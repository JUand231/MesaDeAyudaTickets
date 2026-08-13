package servicio;

import modelo.Comentario;
import modelo.Ticket;
import repositorio.TicketRepository;
import java.util.List;
import java.util.Optional;

/**
 * Logica de negocio de los tickets. Depende de TicketRepository (interfaz)
 */
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket crearTicket(String titulo, String descripcion, int idCategoria, int idPrioridad, int idSolicitante) {

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

    public Ticket asignarAgente(int idTicket, int idAgente) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.setIdAgente(idAgente);
        ticket.asignar();
        return ticketRepository.actualizar(ticket);
    }

    public Ticket iniciarAtencion(int idTicket) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.iniciar();
        return ticketRepository.actualizar(ticket);
    }

    public Ticket resolver(int idTicket) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.resolver();
        return ticketRepository.actualizar(ticket);
    }

    public Ticket cerrar(int idTicket) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.cerrar();
        return ticketRepository.actualizar(ticket);
    }

    public Ticket reabrir(int idTicket) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.reabrir();
        return ticketRepository.actualizar(ticket);
    }

    public Ticket cancelar(int idTicket) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.cancelar();
        return ticketRepository.actualizar(ticket);
    }

    public Ticket reasignarAgente(int idTicket, int nuevoIdAgente) {
        Ticket ticket = buscarPorId(idTicket);
        ticket.setIdAgente(nuevoIdAgente);
        return ticketRepository.actualizar(ticket);
    }

    public Ticket agregarComentario(int idTicket, int idUsuario, String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El comentario no puede estar vacio");
        }
        Ticket ticket = buscarPorId(idTicket);
        ticket.agregarComentario(new Comentario(idUsuario, texto));
        return ticketRepository.actualizar(ticket);
    }
}
