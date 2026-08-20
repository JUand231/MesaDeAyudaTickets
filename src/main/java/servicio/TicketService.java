package servicio;

import modelo.Categoria;
import modelo.Comentario;
import modelo.Prioridad;
import modelo.Ticket;
import modelo.Usuario;
import repositorio.CategoriaRepository;
import repositorio.ComentarioRepository;
import repositorio.PrioridadRepository;
import repositorio.TicketRepository;
import repositorio.UsuarioRepository;
import servicio.asignacion.EstrategiaAsignacion;
import servicio.notificacion.Notificador;
import servicio.prioridad.CalculadoraPrioridad;
import servicio.roles.AccionesAdministrador;
import servicio.roles.AccionesAgente;
import servicio.roles.AccionesSolicitante;
import servicio.sla.CalculadoraSLA;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementación única de las tres interfaces de rol (ISP-01).
 */
public class TicketService implements AccionesSolicitante, AccionesAgente, AccionesAdministrador {

    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final PrioridadRepository prioridadRepository;
    private final CalculadoraSLA calculadoraSLA;
    private final CalculadoraPrioridad calculadoraPrioridad;
    private final EstrategiaAsignacion estrategiaAsignacion;
    private final Notificador notificador;
    private final ComentarioRepository comentarioRepository;

    public TicketService(
            TicketRepository ticketRepository,
            UsuarioRepository usuarioRepository,
            CategoriaRepository categoriaRepository,
            PrioridadRepository prioridadRepository,
            ComentarioRepository comentarioRepository,
            CalculadoraSLA calculadoraSLA,
            CalculadoraPrioridad calculadoraPrioridad,
            EstrategiaAsignacion estrategiaAsignacion,
            Notificador notificador) {

        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.prioridadRepository = prioridadRepository;
        this.comentarioRepository = comentarioRepository;
        this.calculadoraSLA = calculadoraSLA;
        this.calculadoraPrioridad = calculadoraPrioridad;
        this.estrategiaAsignacion = estrategiaAsignacion;
        this.notificador = notificador;
    }

    @Override
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

        Categoria categoria = categoriaRepository
                .buscarPorId(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException(
                "No existe la categoria con id " + idCategoria));

        // ==========================================================
        // RF-03: PRIORIDAD AUTOMÁTICA
        // ==========================================================
        int idPrioridad = calcularIdPrioridad(
                titulo,
                descripcion,
                categoria.getNombreCategoria());

        Ticket ticket = new Ticket();
        ticket.setTitulo(titulo.trim());
        ticket.setDescripcion(descripcion.trim());
        ticket.setIdCategoria(idCategoria);
        ticket.setIdSolicitante(idSolicitante);
        ticket.setIdPrioridad(idPrioridad);

        Ticket creado = ticketRepository.guardar(ticket);

        // ==========================================================
        // RF-04 / OCP-02: ASIGNACIÓN AUTOMÁTICA DE AGENTE
        // ==========================================================
        try {
            List<Usuario> agentesDisponibles = usuarioRepository.listarAgentes();

            if (!agentesDisponibles.isEmpty()) {
                creado = asignarAgenteAutomatico(
                        creado.getIdTicket(),
                        agentesDisponibles);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ==========================================================
        // NOTIFICAR A LOS ADMINISTRADORES
        // ==========================================================
        try {
            List<Usuario> administradores = usuarioRepository.listarAdministradores();

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
            e.printStackTrace();
        }

        return creado;
    }

    /**
     * Usa la Strategy de prioridad (calculadoraPrioridad) para decidir el tipo
     * ("BAJA"/"MEDIA"/"ALTA"/"CRITICA") y lo traduce al id correspondiente de
     * la tabla Prioridad.
     */
    private int calcularIdPrioridad(
            String titulo,
            String descripcion,
            String nombreCategoria) {

        String tipoCalculado = calculadoraPrioridad.calcular(
                titulo,
                descripcion,
                nombreCategoria);

        List<Prioridad> prioridades;

        try {
            prioridades = prioridadRepository.listarTodas();
        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudieron cargar las prioridades disponibles",
                    e);
        }

        return prioridades.stream()
                .filter(p -> p.getTipo().equalsIgnoreCase(tipoCalculado))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                "La prioridad calculada \""
                + tipoCalculado
                + "\" no existe en la tabla Prioridad"))
                .getIdPrioridad();
    }

    @Override
    public List<Ticket> listarTodos() {
        return ticketRepository.listarTodos();
    }

    @Override
    public List<Ticket> listarPorSolicitante(int idSolicitante) {
        return ticketRepository.listarPorSolicitante(idSolicitante);
    }

    @Override
    public List<Ticket> listarPorAgente(int idAgente) {
        return ticketRepository.listarPorAgente(idAgente);
    }

    @Override
    public Ticket buscarPorId(int idTicket) {
        Optional<Ticket> encontrado = ticketRepository.buscarPorId(idTicket);

        if (encontrado.isEmpty()) {
            throw new IllegalArgumentException(
                    "No existe un ticket con id " + idTicket
            );
        }

        return encontrado.get();
    }

    @Override
    public LocalDateTime calcularFechaLimiteSLA(
            int idTicket,
            Prioridad prioridad) {

        Ticket ticket = buscarPorId(idTicket);

        return calculadoraSLA.calcularFechaLimite(
                ticket,
                prioridad
        );
    }

    @Override
    public boolean estaVencido(
            int idTicket,
            Prioridad prioridad) {

        Ticket ticket = buscarPorId(idTicket);

        return calculadoraSLA.estaVencido(
                ticket,
                prioridad
        );
    }

    @Override
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

    @Override
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

        Usuario solicitante = obtenerUsuario(actualizado.getIdSolicitante());

        notificador.notificar(
                solicitante,
                actualizado,
                "Tu ticket #"
                + actualizado.getIdTicket()
                + " fue asignado al agente "
                + agente.getNombre()
        );

        return actualizado;
    }

    @Override
    public Ticket iniciarAtencion(
            int idTicket,
            Usuario solicitante) {

        Ticket ticket = buscarPorId(idTicket);

        ticket.iniciar();

        Ticket actualizado = ticketRepository.actualizar(ticket);

        if (solicitante != null) {
            notificador.notificar(
                    solicitante,
                    actualizado,
                    "Tu ticket está en proceso de atención."
            );
        }

        return actualizado;
    }

    @Override
    public Ticket resolver(
            int idTicket,
            Usuario solicitante) {

        Ticket ticket = buscarPorId(idTicket);

        ticket.resolver();

        Ticket actualizado = ticketRepository.actualizar(ticket);

        if (solicitante != null) {
            notificador.notificar(
                    solicitante,
                    actualizado,
                    "Tu ticket fue resuelto. Puedes confirmarlo o reabrirlo."
            );
        }

        return actualizado;
    }

    @Override
    public Ticket cerrar(
            int idTicket,
            Usuario solicitante) {

        Ticket ticket = buscarPorId(idTicket);

        ticket.cerrar();

        Ticket actualizado = ticketRepository.actualizar(ticket);

        if (solicitante != null) {
            notificador.notificar(
                    solicitante,
                    actualizado,
                    "Tu ticket fue cerrado. Gracias por confirmar."
            );
        }

        return actualizado;
    }

    @Override
    public Ticket reabrir(
            int idTicket,
            Usuario solicitante) {

        Ticket ticket = buscarPorId(idTicket);

        ticket.reabrir();

        Ticket actualizado = ticketRepository.actualizar(ticket);

        if (actualizado.getIdAgente() != 0) {
            Usuario agente = obtenerUsuario(actualizado.getIdAgente());

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

    @Override
    public Ticket cancelar(
            int idTicket,
            Usuario solicitante) {

        Ticket ticket = buscarPorId(idTicket);

        ticket.cancelar();

        Ticket actualizado = ticketRepository.actualizar(ticket);

        if (solicitante != null) {
            notificador.notificar(
                    solicitante,
                    actualizado,
                    "Tu ticket fue cancelado por un administrador."
            );
        }

        return actualizado;
    }

    @Override
    public Ticket reasignarAgente(
            int idTicket,
            int nuevoIdAgente,
            Usuario solicitante) {

        Ticket ticket = buscarPorId(idTicket);

        ticket.setIdAgente(nuevoIdAgente);

        Ticket actualizado = ticketRepository.actualizar(ticket);

        Usuario nuevoAgente = obtenerUsuario(nuevoIdAgente);

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

    @Override
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
        Comentario comentario = new Comentario(
                idTicket,
                idUsuario,
                texto.trim()
        );

        comentarioRepository.guardar(comentario);

        // ==========================================================
        // NOTIFICAR
        // ==========================================================
        if (idRol == 1) {

            if (ticket.getIdAgente() != null) {
                Usuario agente = obtenerUsuario(ticket.getIdAgente());

                notificador.notificar(
                        agente,
                        ticket,
                        "El solicitante agregó un comentario al ticket #"
                        + ticket.getIdTicket()
                        + "."
                );
            }

        } else if (idRol == 2) {

            Usuario solicitante = obtenerUsuario(ticket.getIdSolicitante());

            notificador.notificar(
                    solicitante,
                    ticket,
                    "El agente agregó un comentario al ticket #"
                    + ticket.getIdTicket()
                    + "."
            );

        } else if (idRol == 3) {

            Usuario solicitante = obtenerUsuario(ticket.getIdSolicitante());

            notificador.notificar(
                    solicitante,
                    ticket,
                    "El administrador agregó un comentario al ticket #"
                    + ticket.getIdTicket()
                    + "."
            );

            if (ticket.getIdAgente() != null) {
                Usuario agente = obtenerUsuario(ticket.getIdAgente());

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

        Optional<Usuario> usuario = usuarioRepository.buscarPorId(idUsuario);

        if (usuario.isEmpty()) {
            throw new IllegalArgumentException(
                    "No existe el usuario con id " + idUsuario
            );
        }

        return usuario.get();
    }
}
