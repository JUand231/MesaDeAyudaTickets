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
import java.sql.SQLException;
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

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("idUsuario") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        int idUsuario = (Integer) session.getAttribute("idUsuario");
        int idRol = (Integer) session.getAttribute("idRol");

        // ==================================================
        // SOLO AGENTE
        // ==================================================
        if (idRol != 2) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        // ==================================================
        // ID DEL TICKET
        // ==================================================
        String idParametro = request.getParameter("id");

        if (idParametro == null
                || idParametro.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        int idTicket;

        try {

            idTicket = Integer.parseInt(idParametro);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        // ==================================================
        // SERVICIOS Y REPOSITORIOS
        // ==================================================
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
                    "Los repositorios o servicios no están configurados");

        }

        // ==================================================
        // BUSCAR TICKET
        // ==================================================
        Ticket ticket;

        try {

            ticket = ticketService.buscarPorId(idTicket);

        } catch (IllegalArgumentException e) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        // ==================================================
        // SEGURIDAD
        // EL AGENTE SOLO VE SUS TICKETS
        // ==================================================
        if (ticket.getIdAgente() == null
                || ticket.getIdAgente() != idUsuario) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        // ==================================================
        // CATEGORÍA
        // ==================================================
        String nombreCategoria
                = categoriaRepository
                        .buscarPorId(ticket.getIdCategoria())
                        .map(Categoria::getNombreCategoria)
                        .orElse(
                                "Categoria #"
                                + ticket.getIdCategoria());

        // ==================================================
        // PRIORIDAD
        // ==================================================
        Prioridad prioridad
                = prioridadRepository
                        .buscarPorId(ticket.getIdPrioridad())
                        .orElse(null);

        String nombrePrioridad
                = prioridad != null
                        ? prioridad.getTipo()
                        : "Prioridad #"
                        + ticket.getIdPrioridad();

        // ==================================================
        // SOLICITANTE
        // ==================================================
        String nombreSolicitante
                = usuarioRepository
                        .buscarPorId(ticket.getIdSolicitante())
                        .map(Usuario::getNombre)
                        .orElse(
                                "Usuario #"
                                + ticket.getIdSolicitante());

        // ==================================================
        // AGENTE
        // ==================================================
        String nombreAgente = "Sin asignar";

        if (ticket.getIdAgente() != null) {

            nombreAgente
                    = usuarioRepository
                            .buscarPorId(ticket.getIdAgente())
                            .map(Usuario::getNombre)
                            .orElse(
                                    "Usuario #"
                                    + ticket.getIdAgente());
        }

        // ==================================================
        // DTO
        // ==================================================
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

        // ==================================================
        // SLA
        // ==================================================
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
                    fechaLimiteSLA.format(FORMATO_SLA));

            request.setAttribute(
                    "slaVencido",
                    slaVencido);
        }

        // ==================================================
        // COMENTARIOS
        // ==================================================
        List<Comentario> comentarios
                = comentarioRepository
                        .listarPorTicket(idTicket);

        request.setAttribute(
                "comentarios",
                comentarios);

        // ==================================================
        // MOSTRAR JSP
        // ==================================================
        request.getRequestDispatcher(
                "/WEB-INF/jsp/Agente/detalleTicketAgente.jsp")
                .forward(request, response);
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

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("idUsuario") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        int idUsuario = (Integer) session.getAttribute("idUsuario");
        int idRol = (Integer) session.getAttribute("idRol");

        // ==================================================
        // SOLO AGENTE
        // ==================================================
        if (idRol != 2) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        // ==================================================
        // DATOS DEL FORMULARIO
        // ==================================================
        String idParametro = request.getParameter("id");
        String accion = request.getParameter("accion");

        if (idParametro == null
                || idParametro.trim().isEmpty()
                || accion == null
                || accion.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        int idTicket;

        try {

            idTicket = Integer.parseInt(idParametro);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        // ==================================================
        // SERVICIOS
        // ==================================================
        TicketService ticketService
                = (TicketService) getServletContext()
                        .getAttribute(
                                AppContextListener.TICKET_SERVICE);

        ComentarioRepository comentarioRepository
                = (ComentarioRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.COMENTARIO_REPOSITORY);

        if (ticketService == null
                || comentarioRepository == null) {

            throw new ServletException(
                    "Servicios o repositorios no configurados");
        }

        // ==================================================
        // BUSCAR TICKET
        // ==================================================
        Ticket ticket;

        try {

            ticket = ticketService.buscarPorId(idTicket);

        } catch (IllegalArgumentException e) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        // ==================================================
        // SEGURIDAD
        // SOLO EL AGENTE ASIGNADO
        // ==================================================
        if (ticket.getIdAgente() == null
                || ticket.getIdAgente() != idUsuario) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No puedes modificar este ticket.");

            return;
        }

        // ==================================================
        // COMENTAR
        // ==================================================
        if ("comentar".equals(accion)) {

            String texto = request.getParameter("texto");

            if (texto == null
                    || texto.trim().isEmpty()) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/detalleTicket?id="
                        + idTicket);

                return;
            }

            Comentario comentario
                    = new Comentario(
                            idTicket,
                            idUsuario,
                            texto.trim());

            comentarioRepository.guardar(comentario);

            response.sendRedirect(
                    request.getContextPath()
                    + "/detalleTicket?id="
                    + idTicket);

            return;
        }

        // ==================================================
        // CAMBIAR ESTADO
        // ==================================================
        try {

            switch (accion) {

                case "iniciar":

                    ticketService.iniciarAtencion(
                            idTicket,
                            obtenerSolicitante(ticket));

                    break;

                case "resolver":

                    ticketService.resolver(
                            idTicket,
                            obtenerSolicitante(ticket));

                    break;

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

        // ==================================================
        // VOLVER AL DETALLE
        // ==================================================
        response.sendRedirect(
                request.getContextPath()
                + "/detalleTicket?id="
                + idTicket);
    }

    // ==========================================================
// OBTENER SOLICITANTE
// ==========================================================
    private Usuario obtenerSolicitante(Ticket ticket)
            throws ServletException {

        UsuarioRepository usuarioRepository
                = (UsuarioRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.USUARIO_REPOSITORY);

        return usuarioRepository
                .buscarPorId(ticket.getIdSolicitante())
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "No se encontró el solicitante del ticket"
                        )
                );
    }
}
