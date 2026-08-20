package servicio;

import modelo.Comentario;
import modelo.Prioridad;
import modelo.Ticket;
import modelo.Usuario;
import repositorio.ComentarioRepository;
import repositorio.TicketRepository;
import repositorio.PrioridadRepository;
import repositorio.UsuarioRepository;
import servicio.asignacion.EstrategiaAsignacion;
import servicio.notificacion.Notificador;
import servicio.sla.CalculadoraSLA;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TicketService {

    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;
    private final CalculadoraSLA calculadoraSLA;
    private final EstrategiaAsignacion estrategiaAsignacion;
    private final PrioridadRepository prioridadRepository;
    private final Notificador notificador;
    private final ComentarioRepository comentarioRepository;

    public TicketService(
            TicketRepository ticketRepository,
            UsuarioRepository usuarioRepository,
            PrioridadRepository prioridadRepository,
            ComentarioRepository comentarioRepository,
            CalculadoraSLA calculadoraSLA,
            EstrategiaAsignacion estrategiaAsignacion,
            Notificador notificador) {

        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
        this.prioridadRepository = prioridadRepository;
        this.comentarioRepository = comentarioRepository;
        this.calculadoraSLA = calculadoraSLA;
        this.estrategiaAsignacion = estrategiaAsignacion;
        this.notificador = notificador;
    }

    public Ticket crearTicket(
            String titulo,
            String descripcion,
            int idCategoria,
            int idSolicitante) {

        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException(
                    "El titulo del ticket es obligatorio"
            );
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException(
                    "La descripcion del ticket es obligatoria"
            );
        }

        Ticket ticket = new Ticket();

        ticket.setTitulo(titulo.trim());
        ticket.setDescripcion(descripcion.trim());
        ticket.setIdCategoria(idCategoria);
        ticket.setIdSolicitante(idSolicitante);

        Prioridad prioridad = determinarPrioridad(
                titulo,
                descripcion,
                idCategoria
        );

        ticket.setIdPrioridad(
                prioridad.getIdPrioridad()
        );
        Ticket creado
                = ticketRepository.guardar(ticket);

// ==========================================================
// NOTIFICAR A LOS ADMINISTRADORES
// ==========================================================
        try {

            List<Usuario> administradores
                    = usuarioRepository.listarAdministradores();

            for (Usuario administrador : administradores) {

                notificador.notificar(
                        administrador,
                        creado,
                        "Se creó un nuevo ticket #"
                        + creado.getIdTicket()
                        + ": "
                        + creado.getTitulo()
                );
            }

        } catch (Exception e) {

            // El ticket ya fue creado.
            e.printStackTrace();
        }

        return creado;
    }

    public Prioridad determinarPrioridad(
            String titulo,
            String descripcion,
            int idCategoria) {

        String texto = (titulo + " " + descripcion).toLowerCase();

        // ==========================================================
        // PRIORIDAD CRÍTICA
        // ==========================================================
        if (texto.contains("error")
                || texto.contains("fallo")
                || texto.contains("sistema caído")
                || texto.contains("sistema caido")
                || texto.contains("urgente")) {

            return prioridadRepository.buscarPorId(4)
                    .orElseThrow(()
                            -> new IllegalArgumentException(
                            "No existe la prioridad CRITICA"
                    ));
        }

        // ==========================================================
        // PRIORIDAD ALTA
        // ==========================================================
        if (texto.contains("caído")
                || texto.contains("caido")
                || texto.contains("bloqueado")
                || texto.contains("no funciona")
                || texto.contains("no puedo acceder")
                || texto.contains("no inicia")) {

            return prioridadRepository.buscarPorId(3)
                    .orElseThrow(()
                            -> new IllegalArgumentException(
                            "No existe la prioridad ALTA"
                    ));
        }

        // ==========================================================
        // PRIORIDAD MEDIA
        // ==========================================================
        if (texto.contains("lento")
                || texto.contains("no hay internet")
                || texto.contains("sin internet")
                || texto.contains("red caida")
                || texto.contains("problema")
                || texto.contains("configuración")
                || texto.contains("configuracion")) {

            return prioridadRepository.buscarPorId(2)
                    .orElseThrow(()
                            -> new IllegalArgumentException(
                            "No existe la prioridad MEDIA"
                    ));
        }

        // ==========================================================
        // PRIORIDAD BAJA
        // ==========================================================
        return prioridadRepository.buscarPorId(1)
                .orElseThrow(()
                        -> new IllegalArgumentException(
                        "No existe la prioridad BAJA"
                ));

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

        Optional<Ticket> encontrado
                = ticketRepository.buscarPorId(idTicket);

        if (encontrado.isEmpty()) {
            throw new IllegalArgumentException(
                    "No existe un ticket con id " + idTicket
            );
        }

        return encontrado.get();
    }

    public LocalDateTime calcularFechaLimiteSLA(
            int idTicket,
            Prioridad prioridad) {

        Ticket ticket = buscarPorId(idTicket);

        return calculadoraSLA.calcularFechaLimite(
                ticket,
                prioridad
        );
    }

    public boolean estaVencido(
            int idTicket,
            Prioridad prioridad) {

        Ticket ticket = buscarPorId(idTicket);

        return calculadoraSLA.estaVencido(
                ticket,
                prioridad
        );
    }

    public Ticket asignarAgenteAutomatico(
            int idTicket,
            List<Usuario> agentesDisponibles) {

        Ticket ticket = buscarPorId(idTicket);

        int idAgente = estrategiaAsignacion.asignarAgente(
                ticket,
                agentesDisponibles,
                listarTodos()
        );

        return asignarAgente(idTicket, idAgente);
    }

    public Ticket asignarAgente(int idTicket, int idAgente) {

        Ticket ticket = buscarPorId(idTicket);

        ticket.setIdAgente(idAgente);

        ticket.asignar();

        Ticket actualizado = ticketRepository.actualizar(ticket);

        Usuario agente = obtenerUsuario(idAgente);

        notificador.notificar(
                agente,
                actualizado,
                "Se te asignó el ticket #"
                + actualizado.getIdTicket()
                + ": "
                + actualizado.getTitulo()
        );

        return actualizado;
    }

    public Ticket iniciarAtencion(
            int idTicket,
            Usuario solicitante) {

        Ticket ticket = buscarPorId(idTicket);

        ticket.iniciar();

        Ticket actualizado
                = ticketRepository.actualizar(ticket);

        if (solicitante != null) {

            notificador.notificar(
                    solicitante,
                    actualizado,
                    "Tu ticket está en proceso de atención."
            );
        }

        return actualizado;
    }

    public Ticket resolver(
            int idTicket,
            Usuario solicitante) {

        Ticket ticket = buscarPorId(idTicket);

        ticket.resolver();

        Ticket actualizado
                = ticketRepository.actualizar(ticket);

        if (solicitante != null) {

            notificador.notificar(
                    solicitante,
                    actualizado,
                    "Tu ticket fue resuelto. Puedes confirmarlo o reabrirlo."
            );
        }

        return actualizado;
    }

    public Ticket cerrar(
            int idTicket,
            Usuario solicitante) {

        Ticket ticket = buscarPorId(idTicket);

        ticket.cerrar();

        Ticket actualizado
                = ticketRepository.actualizar(ticket);

        if (solicitante != null) {

            notificador.notificar(
                    solicitante,
                    actualizado,
                    "Tu ticket fue cerrado. Gracias por confirmar."
            );
        }

        return actualizado;
    }

    public Ticket reabrir(
            int idTicket,
            Usuario solicitante) {

        Ticket ticket = buscarPorId(idTicket);

        ticket.reabrir();

        Ticket actualizado
                = ticketRepository.actualizar(ticket);

        if (actualizado.getIdAgente() != 0) {

            Usuario agente
                    = obtenerUsuario(
                            actualizado.getIdAgente()
                    );

            notificador.notificar(
                    agente,
                    actualizado,
                    "El ticket #"
                    + actualizado.getIdTicket()
                    + " fue reabierto y requiere atención."
            );
        }

        return actualizado;
    }

    public Ticket cancelar(
            int idTicket,
            Usuario solicitante) {

        Ticket ticket = buscarPorId(idTicket);

        ticket.cancelar();

        Ticket actualizado
                = ticketRepository.actualizar(ticket);

        if (solicitante != null) {

            notificador.notificar(
                    solicitante,
                    actualizado,
                    "Tu ticket fue cancelado por un administrador."
            );
        }

        return actualizado;
    }

    public Ticket reasignarAgente(
            int idTicket,
            int nuevoIdAgente,
            Usuario solicitante) {

        Ticket ticket = buscarPorId(idTicket);

        ticket.setIdAgente(nuevoIdAgente);

        Ticket actualizado
                = ticketRepository.actualizar(ticket);

        Usuario nuevoAgente
                = obtenerUsuario(nuevoIdAgente);

        notificador.notificar(
                nuevoAgente,
                actualizado,
                "Se te asignó el ticket #"
                + actualizado.getIdTicket()
                + ": "
                + actualizado.getTitulo()
        );

        return actualizado;
    }

    public Comentario agregarComentario(
            int idTicket,
            int idUsuario,
            int idRol,
            String texto) {

        if (texto == null || texto.isBlank()) {

            throw new IllegalArgumentException(
                    "El comentario no puede estar vacio"
            );
        }

        Ticket ticket = buscarPorId(idTicket);

        // ==========================================================
        // VALIDAR PERMISOS
        // ==========================================================
        if (idRol == 1) {

            // SOLICITANTE
            if (ticket.getIdSolicitante() != idUsuario) {

                throw new IllegalArgumentException(
                        "No puedes comentar este ticket."
                );
            }

        } else if (idRol == 2) {

            // AGENTE
            if (ticket.getIdAgente() == null
                    || ticket.getIdAgente() != idUsuario) {

                throw new IllegalArgumentException(
                        "No puedes comentar este ticket."
                );
            }

        } else if (idRol != 3) {

            throw new IllegalArgumentException(
                    "No tienes permiso para comentar."
            );
        }

        // ==========================================================
        // CREAR COMENTARIO
        // ==========================================================
        Comentario comentario
                = new Comentario(
                        idTicket,
                        idUsuario,
                        texto.trim()
                );

        comentarioRepository.guardar(
                comentario
        );

        // ==========================================================
        // NOTIFICAR
        // ==========================================================
        if (idRol == 1) {

            // El solicitante comentó.
            // Avisar al agente asignado.
            if (ticket.getIdAgente() != null) {

                Usuario agente
                        = obtenerUsuario(
                                ticket.getIdAgente()
                        );

                notificador.notificar(
                        agente,
                        ticket,
                        "El solicitante agregó un comentario al ticket #"
                        + ticket.getIdTicket()
                        + "."
                );
            }

        } else if (idRol == 2) {

            // El agente comentó.
            // Avisar al solicitante.
            Usuario solicitante
                    = obtenerUsuario(
                            ticket.getIdSolicitante()
                    );

            notificador.notificar(
                    solicitante,
                    ticket,
                    "El agente agregó un comentario al ticket #"
                    + ticket.getIdTicket()
                    + "."
            );

        } else if (idRol == 3) {

            // ADMINISTRADOR
            // Avisar a solicitante y agente.
            Usuario solicitante
                    = obtenerUsuario(
                            ticket.getIdSolicitante()
                    );

            notificador.notificar(
                    solicitante,
                    ticket,
                    "El administrador agregó un comentario al ticket #"
                    + ticket.getIdTicket()
                    + "."
            );

            if (ticket.getIdAgente() != null) {

                Usuario agente
                        = obtenerUsuario(
                                ticket.getIdAgente()
                        );

                notificador.notificar(
                        agente,
                        ticket,
                        "El administrador agregó un comentario al ticket #"
                        + ticket.getIdTicket()
                        + "."
                );
            }
        }

        return comentario;
    }

    private Usuario obtenerUsuario(int idUsuario) {

        Optional<Usuario> usuario
                = usuarioRepository.buscarPorId(idUsuario);

        if (usuario.isEmpty()) {
            throw new IllegalArgumentException(
                    "No existe el usuario con id " + idUsuario
            );
        }

        return usuario.get();
    }
}
