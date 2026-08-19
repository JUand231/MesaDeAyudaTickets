package WebServlet;

import dto.TicketDTO;
import mapper.TicketMapper;
import modelo.Categoria;
import modelo.Comentario;
import modelo.Prioridad;
import modelo.Ticket;
import modelo.Usuario;
import repositorio.CategoriaRepository;
import repositorio.ComentarioRepository;
import repositorio.PrioridadRepository;
import repositorio.UsuarioRepository;
import servicio.TicketService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/detalleTicket")
public class DetalleTicketAgenteServlet extends HttpServlet {

    private static final DateTimeFormatter FORMATO_SLA
            = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // ==========================================================
    // GET
    // MOSTRAR DETALLE DEL TICKET
    // ==========================================================
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ==========================================================
        // VALIDAR SESIÓN
        // ==========================================================
        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("idUsuario") == null
                || session.getAttribute("idRol") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login");

            return;
        }

        int idUsuario
                = (Integer) session.getAttribute("idUsuario");

        int idRol
                = (Integer) session.getAttribute("idRol");

        // ==========================================================
        // VALIDAR ROL
        //
        // 1 = SOLICITANTE
        // 2 = AGENTE
        // 3 = ADMINISTRADOR
        // ==========================================================
        if (idRol != 1 && idRol != 2 && idRol != 3) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permiso para acceder al detalle de este ticket.");

            return;
        }

        // ==========================================================
        // OBTENER ID DEL TICKET
        // ==========================================================
        String idParametro
                = request.getParameter("id");

        if (idParametro == null
                || idParametro.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/tickets");

            return;
        }

        int idTicket;

        try {

            idTicket = Integer.parseInt(idParametro);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/tickets");

            return;
        }

        // ==========================================================
        // OBTENER SERVICIOS Y REPOSITORIOS
        // ==========================================================
        TicketService ticketService
                = (TicketService) getServletContext()
                        .getAttribute(
                                AppContextListener.TICKET_SERVICE);

        CategoriaRepository categoriaRepository
                = (CategoriaRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.CATEGORIA_REPOSITORY);

        PrioridadRepository prioridadRepository
                = (PrioridadRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.PRIORIDAD_REPOSITORY);

        UsuarioRepository usuarioRepository
                = (UsuarioRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.USUARIO_REPOSITORY);

        ComentarioRepository comentarioRepository
                = (ComentarioRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.COMENTARIO_REPOSITORY);

        if (ticketService == null
                || categoriaRepository == null
                || prioridadRepository == null
                || usuarioRepository == null
                || comentarioRepository == null) {

            throw new ServletException(
                    "Los repositorios o servicios no están configurados.");
        }

        // ==========================================================
        // BUSCAR TICKET
        // ==========================================================
        Ticket ticket;

        try {

            ticket = ticketService.buscarPorId(idTicket);

        } catch (IllegalArgumentException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/tickets");

            return;
        }

        // ==========================================================
        // SEGURIDAD
        //
        // AGENTE:
        // Solo puede ver tickets asignados a él.
        //
        // SOLICITANTE:
        // Solo puede ver tickets creados por él.
        //
        // ADMINISTRADOR:
        // Puede ver cualquier ticket, sin restricción.
        // ==========================================================
        if (idRol == 2) {

            if (ticket.getIdAgente() == null
                    || ticket.getIdAgente() != idUsuario) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "No puedes acceder a este ticket.");

                return;
            }

        } else if (idRol == 1) {

            if (ticket.getIdSolicitante() != idUsuario) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "No puedes acceder a este ticket.");

                return;
            }
        }

        // ==========================================================
        // CATEGORÍA
        // ==========================================================
        String nombreCategoria
                = categoriaRepository
                        .buscarPorId(
                                ticket.getIdCategoria())
                        .map(
                                Categoria::getNombreCategoria)
                        .orElse(
                                "Categoria #"
                                + ticket.getIdCategoria());

        // ==========================================================
        // PRIORIDAD
        // ==========================================================
        Prioridad prioridad
                = prioridadRepository
                        .buscarPorId(
                                ticket.getIdPrioridad())
                        .orElse(null);

        String nombrePrioridad
                = prioridad != null
                        ? prioridad.getTipo()
                        : "Prioridad #"
                        + ticket.getIdPrioridad();

        // ==========================================================
        // SOLICITANTE
        // ==========================================================
        String nombreSolicitante
                = usuarioRepository
                        .buscarPorId(
                                ticket.getIdSolicitante())
                        .map(
                                Usuario::getNombre)
                        .orElse(
                                "Usuario #"
                                + ticket.getIdSolicitante());

        // ==========================================================
        // AGENTE
        // ==========================================================
        String nombreAgente = "Sin asignar";

        if (ticket.getIdAgente() != null) {

            nombreAgente
                    = usuarioRepository
                            .buscarPorId(
                                    ticket.getIdAgente())
                            .map(
                                    Usuario::getNombre)
                            .orElse(
                                    "Usuario #"
                                    + ticket.getIdAgente());
        }

        // ==========================================================
        // CONVERTIR A DTO
        // ==========================================================
        TicketDTO ticketDTO
                = TicketMapper.aDTO(
                        ticket,
                        nombreCategoria,
                        nombrePrioridad,
                        nombreSolicitante,
                        nombreAgente);

        request.setAttribute(
                "ticket",
                ticketDTO);

        // ==========================================================
        // SLA
        // ==========================================================
        if (prioridad != null) {

            LocalDateTime fechaLimiteSLA
                    = ticketService.calcularFechaLimiteSLA(
                            ticket.getIdTicket(),
                            prioridad);

            boolean slaVencido
                    = ticketService.estaVencido(
                            ticket.getIdTicket(),
                            prioridad);

            request.setAttribute(
                    "horasSLA",
                    prioridad.getHorasSLA());

            request.setAttribute(
                    "fechaLimiteSLA",
                    fechaLimiteSLA.format(
                            FORMATO_SLA));

            request.setAttribute(
                    "slaVencido",
                    slaVencido);
        }

        // ==========================================================
        // COMENTARIOS
        // ==========================================================
        List<Comentario> comentarios
                = comentarioRepository
                        .listarPorTicket(
                                idTicket);

        request.setAttribute(
                "comentarios",
                comentarios);

        // ==========================================================
        // MOSTRAR VISTA SEGÚN EL ROL
        //
        // ROL 1 → SOLICITANTE
        // ROL 2 → AGENTE
        // ROL 3 → ADMINISTRADOR
        // ==========================================================
        if (idRol == 1) {

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/Solicitante/detalleTicketSolicitante.jsp")
                    .forward(
                            request,
                            response);

        } else if (idRol == 3) {

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/Administrador/detalleTicketAdmin.jsp")
                    .forward(
                            request,
                            response);

        } else {

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/Agente/detalleTicketAgente.jsp")
                    .forward(
                            request,
                            response);
        }
    }

    // ==========================================================
    // POST
    // COMENTARIOS Y CAMBIOS DE ESTADO
    // ==========================================================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // ==========================================================
        // CODIFICACIÓN
        //
        // Sin esto, tildes/ñ/etc. del textarea llegan mal
        // decodificadas y quedan corruptas en la base de datos.
        // ==========================================================
        request.setCharacterEncoding("UTF-8");

        // ==========================================================
        // VALIDAR SESIÓN
        // ==========================================================
        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("idUsuario") == null
                || session.getAttribute("idRol") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login");

            return;
        }

        int idUsuario
                = (Integer) session.getAttribute("idUsuario");

        int idRol
                = (Integer) session.getAttribute("idRol");

        // ==========================================================
        // VALIDAR ROL
        //
        // 1 = SOLICITANTE
        // 2 = AGENTE
        // 3 = ADMINISTRADOR (solo puede comentar, no cambiar estado)
        // ==========================================================
        if (idRol != 1 && idRol != 2 && idRol != 3) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permiso para modificar este ticket.");

            return;
        }

        // ==========================================================
        // DATOS DEL FORMULARIO
        // ==========================================================
        String idParametro
                = request.getParameter("id");

        String accion
                = request.getParameter("accion");

        if (idParametro == null
                || idParametro.trim().isEmpty()
                || accion == null
                || accion.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/tickets");

            return;
        }

        int idTicket;

        try {

            idTicket = Integer.parseInt(idParametro);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/tickets");

            return;
        }

        // ==========================================================
        // SERVICIOS Y REPOSITORIOS
        // ==========================================================
        TicketService ticketService
                = (TicketService) getServletContext()
                        .getAttribute(
                                AppContextListener.TICKET_SERVICE);

        if (ticketService == null) {

            throw new ServletException(
                    "TicketService no está configurado.");
        }

        // ==========================================================
        // BUSCAR TICKET
        // ==========================================================
        Ticket ticket;

        try {

            ticket = ticketService.buscarPorId(idTicket);

        } catch (IllegalArgumentException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/tickets");

            return;
        }

        // ==========================================================
        // SEGURIDAD PARA MODIFICACIONES
        //
        // AGENTE:
        // Solo puede modificar sus tickets asignados.
        //
        // SOLICITANTE:
        // Solo puede comentar sus propios tickets.
        // ==========================================================
        if (idRol == 2) {

            if (ticket.getIdAgente() == null
                    || ticket.getIdAgente() != idUsuario) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "No puedes modificar este ticket.");

                return;
            }

        } else if (idRol == 1) {

            if (ticket.getIdSolicitante() != idUsuario) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "No puedes modificar este ticket.");

                return;
            }
        }

        // ==========================================================
        // COMENTAR
        //
        // SOLICITANTE, AGENTE Y ADMINISTRADOR
        // PUEDEN AGREGAR COMENTARIOS.
        //
        // Se delega en TicketService.agregarComentario() en vez de
        // guardar directo en el repositorio, porque ahí es donde
        // vive la lógica que notifica al agente/solicitante.
        // ==========================================================
        if ("comentar".equals(accion)) {

            String texto
                    = request.getParameter("texto");

            if (texto == null
                    || texto.trim().isEmpty()) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/detalleTicket?id="
                        + idTicket);

                return;
            }

            try {

                ticketService.agregarComentario(
                        idTicket,
                        idUsuario,
                        idRol,
                        texto.trim());

            } catch (IllegalArgumentException e) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        e.getMessage());

                return;
            }

            response.sendRedirect(
                    request.getContextPath()
                    + "/detalleTicket?id="
                    + idTicket);

            return;
        }

        // ==========================================================
        // SOLO EL AGENTE PUEDE CAMBIAR ESTADO
        //
        // Esta comprobación está ANTES del switch.
        // ==========================================================
        if (idRol != 2) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "El solicitante no puede cambiar el estado del ticket.");

            return;
        }

        // ==========================================================
        // CAMBIAR ESTADO
        // ==========================================================
        try {

            switch (accion) {

                // ==================================================
                // INICIAR ATENCIÓN
                // ==================================================
                case "iniciar":

                    ticketService.iniciarAtencion(
                            idTicket,
                            obtenerSolicitante(ticket));

                    break;

                // ==================================================
                // RESOLVER
                // ==================================================
                case "resolver":

                    ticketService.resolver(
                            idTicket,
                            obtenerSolicitante(ticket));

                    break;

                // ==================================================
                // ACCIÓN NO VÁLIDA
                // ==================================================
                default:

                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Acción no válida.");

                    return;
            }

        } catch (IllegalArgumentException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage());

            return;
        }

        // ==========================================================
        // VOLVER AL DETALLE
        // ==========================================================
        response.sendRedirect(
                request.getContextPath()
                + "/detalleTicket?id="
                + idTicket);
    }

    // ==========================================================
    // OBTENER SOLICITANTE
    // ==========================================================
    private Usuario obtenerSolicitante(
            Ticket ticket)
            throws ServletException {

        UsuarioRepository usuarioRepository
                = (UsuarioRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.USUARIO_REPOSITORY);

        if (usuarioRepository == null) {

            throw new ServletException(
                    "UsuarioRepository no está configurado.");
        }

        return usuarioRepository
                .buscarPorId(
                        ticket.getIdSolicitante())
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "No se encontró el solicitante del ticket"));
    }
}
