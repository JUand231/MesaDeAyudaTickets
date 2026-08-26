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
import servicio.notificacion.Notificador;
import java.io.IOException;
import java.security.SecureRandom;
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

    private static final String SESSION_OTP_CODIGO = "otpCierreCodigo_";
    private static final String SESSION_OTP_EXPIRA = "otpCierreExpira_";
    private static final int OTP_MINUTOS_VALIDEZ = 10;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

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

        if (idRol != 1 && idRol != 2 && idRol != 3) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permiso para acceder al detalle de este ticket.");

            return;
        }

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

        TicketService ticketService
                = (TicketService) getServletContext().getAttribute(
                        AppContextListener.TICKET_SERVICE);

        CategoriaRepository categoriaRepository
                = (CategoriaRepository) getServletContext().getAttribute(
                        AppContextListener.CATEGORIA_REPOSITORY);

        PrioridadRepository prioridadRepository
                = (PrioridadRepository) getServletContext().getAttribute(
                        AppContextListener.PRIORIDAD_REPOSITORY);

        UsuarioRepository usuarioRepository
                = (UsuarioRepository) getServletContext().getAttribute(
                        AppContextListener.USUARIO_REPOSITORY);

        ComentarioRepository comentarioRepository
                = (ComentarioRepository) getServletContext().getAttribute(
                        AppContextListener.COMENTARIO_REPOSITORY);

        if (ticketService == null
                || categoriaRepository == null
                || prioridadRepository == null
                || usuarioRepository == null
                || comentarioRepository == null) {

            throw new ServletException(
                    "Los repositorios o servicios no están configurados.");
        }

        Ticket ticket;

        try {

            ticket = ticketService.buscarPorId(idTicket);

        } catch (IllegalArgumentException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/tickets");

            return;
        }

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

        String nombreCategoria
                = categoriaRepository
                        .buscarPorId(
                                ticket.getIdCategoria())
                        .map(
                                Categoria::getNombreCategoria)
                        .orElse(
                                "Categoria #"
                                + ticket.getIdCategoria());

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

        String nombreSolicitante
                = usuarioRepository
                        .buscarPorId(
                                ticket.getIdSolicitante())
                        .map(
                                Usuario::getNombre)
                        .orElse(
                                "Usuario #"
                                + ticket.getIdSolicitante());

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

        List<Comentario> comentarios
                = comentarioRepository
                        .listarPorTicket(
                                idTicket);

        request.setAttribute(
                "comentarios",
                comentarios);

        boolean otpPendienteCierre
                = session.getAttribute(SESSION_OTP_CODIGO + idTicket) != null;

        request.setAttribute(
                "otpPendienteCierre",
                otpPendienteCierre);

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

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

        if (idRol != 1 && idRol != 2 && idRol != 3) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permiso para modificar este ticket.");

            return;
        }

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

        TicketService ticketService
                = (TicketService) getServletContext()
                        .getAttribute(
                                AppContextListener.TICKET_SERVICE);

        if (ticketService == null) {

            throw new ServletException(
                    "TicketService no está configurado.");
        }

        Ticket ticket;

        try {

            ticket = ticketService.buscarPorId(idTicket);

        } catch (IllegalArgumentException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/tickets");

            return;
        }

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

        if ("solicitarCierreOtp".equals(accion)) {

            if (idRol != 1) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Solo el solicitante puede pedir el código de cierre.");

                return;
            }

            if (!"RESUELTO".equals(ticket.getEstadoNombre())) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Solo se puede pedir el código cuando el ticket está RESUELTO.");

                return;
            }

            String codigo
                    = String.format(
                            "%06d",
                            new SecureRandom().nextInt(1_000_000));

            session.setAttribute(
                    SESSION_OTP_CODIGO + idTicket,
                    codigo);

            session.setAttribute(
                    SESSION_OTP_EXPIRA + idTicket,
                    LocalDateTime.now().plusMinutes(OTP_MINUTOS_VALIDEZ));

            Notificador notificador
                    = (Notificador) getServletContext()
                            .getAttribute(AppContextListener.NOTIFICADOR);

            if (notificador == null) {

                throw new ServletException(
                        "Notificador no está configurado.");
            }

            notificador.notificar(
                    obtenerSolicitante(ticket),
                    ticket,
                    "Tu código para confirmar el cierre del ticket #"
                    + ticket.getIdTicket()
                    + " es "
                    + codigo
                    + ". Vence en "
                    + OTP_MINUTOS_VALIDEZ
                    + " minutos.");

            response.sendRedirect(
                    request.getContextPath()
                    + "/detalleTicket?id="
                    + idTicket);

            return;
        }

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

        try {

            switch (accion) {

                case "iniciar":

                    if (idRol != 2) {

                        response.sendError(
                                HttpServletResponse.SC_FORBIDDEN,
                                "Solo el agente puede iniciar la atención.");

                        return;
                    }

                    ticketService.iniciarAtencion(
                            idTicket,
                            obtenerSolicitante(ticket));

                    break;

                case "resolver":

                    if (idRol != 2) {

                        response.sendError(
                                HttpServletResponse.SC_FORBIDDEN,
                                "Solo el agente puede resolver el ticket.");

                        return;
                    }

                    ticketService.resolver(
                            idTicket,
                            obtenerSolicitante(ticket));

                    break;

                case "cerrar":

                    if (idRol != 1) {

                        response.sendError(
                                HttpServletResponse.SC_FORBIDDEN,
                                "Solo el solicitante puede cerrar el ticket.");

                        return;
                    }

                    String otpIngresado
                            = request.getParameter("otp");

                    String otpEsperado
                            = (String) session.getAttribute(
                                    SESSION_OTP_CODIGO + idTicket);

                    LocalDateTime otpExpira
                            = (LocalDateTime) session.getAttribute(
                                    SESSION_OTP_EXPIRA + idTicket);

                    if (otpEsperado == null) {

                        response.sendError(
                                HttpServletResponse.SC_BAD_REQUEST,
                                "Primero debes solicitar el código de cierre.");

                        return;
                    }

                    if (otpExpira == null
                            || LocalDateTime.now().isAfter(otpExpira)) {

                        session.removeAttribute(SESSION_OTP_CODIGO + idTicket);
                        session.removeAttribute(SESSION_OTP_EXPIRA + idTicket);

                        response.sendError(
                                HttpServletResponse.SC_BAD_REQUEST,
                                "El código venció. Solicita uno nuevo.");

                        return;
                    }

                    if (otpIngresado == null
                            || !otpEsperado.equals(otpIngresado.trim())) {

                        response.sendError(
                                HttpServletResponse.SC_BAD_REQUEST,
                                "El código ingresado no es correcto.");

                        return;
                    }

                    ticketService.cerrar(
                            idTicket,
                            obtenerSolicitante(ticket));

                    session.removeAttribute(SESSION_OTP_CODIGO + idTicket);
                    session.removeAttribute(SESSION_OTP_EXPIRA + idTicket);

                    break;

                case "reabrir":

                    if (idRol != 1) {

                        response.sendError(
                                HttpServletResponse.SC_FORBIDDEN,
                                "Solo el solicitante puede reabrir el ticket.");

                        return;
                    }

                    ticketService.reabrir(
                            idTicket,
                            obtenerSolicitante(ticket));

                    break;

                default:

                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Acción no válida.");

                    return;
            }

        } catch (RuntimeException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage());

            return;
        }

        response.sendRedirect(
                request.getContextPath()
                + "/detalleTicket?id="
                + idTicket);
    }

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
