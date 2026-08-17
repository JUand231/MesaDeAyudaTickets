package servicio;

import modelo.Comentario;
import modelo.Prioridad;
import modelo.Ticket;
import modelo.Usuario;
import repositorio.TicketRepository;
import servicio.asignacion.EstrategiaAsignacion;
import servicio.notificacion.Notificador;
import servicio.sla.CalculadoraSLA;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TicketService {

    private final TicketRepository ticketRepository;
    private final CalculadoraSLA calculadoraSLA;
    private final EstrategiaAsignacion estrategiaAsignacion;
    private final Notificador notificador;

    public TicketService(TicketRepository ticketRepository,
            CalculadoraSLA calculadoraSLA,
            EstrategiaAsignacion estrategiaAsignacion,
            Notificador notificador) {
        this.ticketRepository = ticketRepository;
        this.calculadoraSLA = calculadoraSLA;
        this.estrategiaAsignacion = estrategiaAsignacion;
        this.notificador = notificador;
    }

    public Ticket crearTicket(String titulo, String descripcion, int idCategoria,
            int idPrioridad, int idSolicitante) {

        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El titulo del ticket es obligatorio");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripcion del ticket es obligatoria");
        }

        Ticket ticket = new Ticket();
        ticket.setTitulo(titulo);
        ticket.setDescripcion(descripcion);
        ticket.setIdCategoria(idCategoria);
        ticket.setIdPrioridad(idPrioridad);
        ticket.setIdSolicitante(idSolicitante);

        return ticketRepository.guardar(ticket);
    }

    public List<Ticket> listarTodos() {
        return ticketRepository.listarTodos();
    }

    public List<Ticket> listarPorSolicitante(int idSolicitante) {
        return ticketRepository.listarPorSolicitante(idSolicitante);
    }

    public List<Ticket> listarPorAgente(int idAgente) {
        return ticketRepository.listarPorAgente(idAgente);
    }

    public Ticket buscarPorId(int idTicket) {
        Optional<Ticket> encontrado = ticketRepository.buscarPorId(idTicket);
        if (encontrado.isEmpty()) {
            throw new IllegalArgumentException("No existe un ticket con id " + idTicket);
        }
        return encontrado.get();
    }

    public LocalDateTime calcularFechaLimiteSLA(int idTicket, Prioridad prioridad) {
        Ticket ticket = buscarPorId(idTicket);
        return calculadoraSLA.calcularFechaLimite(ticket, prioridad);
    }

    public boolean estaVencido(int idTicket, Prioridad prioridad) {
        Ticket ticket = buscarPorId(idTicket);
        return calculadoraSLA.estaVencido(ticket, prioridad);
    }

    public Ticket asignarAgenteAutomatico(int idTicket, List<Usuario> agentesDisponibles,
            Usuario solicitante) {
        Ticket ticket = buscarPorId(idTicket);
        int idAgente = estrategiaAsignacion.asignarAgente(ticket, agentesDisponibles, listarTodos());
        return asignarAgente(idTicket, idAgente, solicitante);
    }

    public Ticket asignarAgente(int idTicket, int idAgente, Usuario solicitante) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.setIdAgente(idAgente);
        ticket.asignar(); // delega al EstadoTicket: lanza TransicionInvalidaException si no es valido
        Ticket actualizado = ticketRepository.actualizar(ticket);
        notificador.notificar(solicitante, actualizado, "Tu ticket fue asignado a un agente.");
        return actualizado;
    }

    public Ticket iniciarAtencion(int idTicket, Usuario solicitante) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.iniciar();
        Ticket actualizado = ticketRepository.actualizar(ticket);
        notificador.notificar(solicitante, actualizado, "Tu ticket esta en proceso de atencion.");
        return actualizado;
    }

    public Ticket resolver(int idTicket, Usuario solicitante) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.resolver();
        Ticket actualizado = ticketRepository.actualizar(ticket);
        notificador.notificar(solicitante, actualizado, "Tu ticket fue resuelto. Puedes confirmarlo o reabrirlo.");
        return actualizado;
    }

    public Ticket cerrar(int idTicket, Usuario solicitante) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.cerrar();
        Ticket actualizado = ticketRepository.actualizar(ticket);
        notificador.notificar(solicitante, actualizado, "Tu ticket fue cerrado. Gracias por confirmar.");
        return actualizado;
    }

    public Ticket reabrir(int idTicket, Usuario solicitante) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.reabrir();
        Ticket actualizado = ticketRepository.actualizar(ticket);
        notificador.notificar(solicitante, actualizado, "Tu ticket fue reabierto y vuelve a estar en proceso.");
        return actualizado;
    }

    public Ticket cancelar(int idTicket, Usuario solicitante) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.cancelar();
        Ticket actualizado = ticketRepository.actualizar(ticket);
        notificador.notificar(solicitante, actualizado, "Tu ticket fue cancelado por un administrador.");
        return actualizado;
    }

    public Ticket reasignarAgente(int idTicket, int nuevoIdAgente, Usuario solicitante) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.setIdAgente(nuevoIdAgente);
        Ticket actualizado = ticketRepository.actualizar(ticket);
        notificador.notificar(solicitante, actualizado, "Tu ticket fue reasignado a otro agente.");
        return actualizado;
    }

    public Ticket agregarComentario(int idTicket, int idUsuario, String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El comentario no puede estar vacio");
        }
        Ticket ticket = buscarPorId(idTicket);
        ticket.agregarComentario(new Comentario(0, idUsuario, idTicket, texto.trim()));
        return ticketRepository.actualizar(ticket);
    }
}
