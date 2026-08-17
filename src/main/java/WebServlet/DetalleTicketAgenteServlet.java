package WebServlet;

import dto.TicketDTO;
import mapper.TicketMapper;
import modelo.Categoria;
import modelo.Prioridad;
import modelo.Ticket;
import modelo.Usuario;
import repositorio.CategoriaRepository;
import repositorio.PrioridadRepository;
import repositorio.UsuarioRepository;
import repositorio.ComentarioRepository;
import servicio.TicketService;
import modelo.Comentario;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/detalleTicket")
public class DetalleTicketAgenteServlet extends HttpServlet {

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

        int idUsuario
                = (Integer) session.getAttribute("idUsuario");

        int idRol
                = (Integer) session.getAttribute("idRol");

        // Solo puede entrar un agente
        if (idRol != 2) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        String idParametro
                = request.getParameter("id");

        if (idParametro == null
                || idParametro.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        int idTicket;

        try {

            idTicket
                    = Integer.parseInt(idParametro);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

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

        Ticket ticket;

        try {

            ticket
                    = ticketService.buscarPorId(idTicket);

        } catch (IllegalArgumentException e) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        if (ticket.getIdAgente() == null
                || ticket.getIdAgente() != idUsuario) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permiso para ver este ticket.");

            return;
        }

        try {

            String nombreCategoria
                    = categoriaRepository
                            .buscarPorId(
                                    ticket.getIdCategoria())
                            .map(Categoria::getNombreCategoria)
                            .orElse(
                                    "Categoria #"
                                    + ticket.getIdCategoria());

            String nombrePrioridad
                    = prioridadRepository
                            .buscarPorId(
                                    ticket.getIdPrioridad())
                            .map(Prioridad::getTipo)
                            .orElse(
                                    "Prioridad #"
                                    + ticket.getIdPrioridad());

            String nombreSolicitante
                    = usuarioRepository
                            .buscarPorId(
                                    ticket.getIdSolicitante())
                            .map(Usuario::getNombre)
                            .orElse(
                                    "Usuario #"
                                    + ticket.getIdSolicitante());

            String nombreAgente
                    = usuarioRepository
                            .buscarPorId(
                                    ticket.getIdAgente())
                            .map(Usuario::getNombre)
                            .orElse(
                                    "Usuario #"
                                    + ticket.getIdAgente());

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

            Usuario solicitante
                    = usuarioRepository
                            .buscarPorId(
                                    ticket.getIdSolicitante())
                            .orElse(null);

            request.setAttribute(
                    "solicitante",
                    solicitante);

            if (comentarioRepository != null) {

                request.setAttribute(
                        "comentarios",
                        comentarioRepository
                                .listarPorTicket(idTicket));
            }

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/Agente/detalleTicketAgente.jsp")
                    .forward(request, response);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error consultando los datos del ticket",
                    e);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session
                = request.getSession(false);

        if (session == null
                || session.getAttribute("idUsuario") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        int idUsuario
                = (Integer) session.getAttribute("idUsuario");

        int idRol
                = (Integer) session.getAttribute("idRol");

        // Solo agentes
        if (idRol != 2) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        String idParametro
                = request.getParameter("id");

        String accion
                = request.getParameter("accion");

        if (idParametro == null
                || idParametro.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        int idTicket;

        try {

            idTicket
                    = Integer.parseInt(idParametro);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        TicketService ticketService
                = (TicketService) getServletContext()
                        .getAttribute(
                                AppContextListener.TICKET_SERVICE);

        UsuarioRepository usuarioRepository
                = (UsuarioRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.USUARIO_REPOSITORY);

        Ticket ticket;

        try {

            ticket
                    = ticketService.buscarPorId(idTicket);

        } catch (IllegalArgumentException e) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        if (ticket.getIdAgente() == null
                || ticket.getIdAgente() != idUsuario) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permiso para modificar este ticket.");

            return;
        }

        Usuario solicitante;

        try {

            solicitante
                    = usuarioRepository
                            .buscarPorId(
                                    ticket.getIdSolicitante())
                            .orElse(null);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error buscando al solicitante",
                    e);
        }

        if (solicitante == null) {

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No se encontró el solicitante del ticket.");

            return;
        }

        try {

            if ("iniciar".equals(accion)) {

                ticketService.iniciarAtencion(
                        idTicket,
                        solicitante);

            } else if ("resolver".equals(accion)) {

                ticketService.resolver(
                        idTicket,
                        solicitante);

            } else if ("comentar".equals(accion)) {

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

                ComentarioRepository comentarioRepository
                        = (ComentarioRepository) getServletContext()
                                .getAttribute(
                                        AppContextListener.COMENTARIO_REPOSITORY);

                if (comentarioRepository == null) {

                    throw new ServletException(
                            "ComentarioRepository no está configurado");
                }

                Comentario comentario
                        = new Comentario(
                                idTicket,
                                idUsuario,
                                texto.trim());

                comentarioRepository.guardar(comentario);
            }

            response.sendRedirect(
                    request.getContextPath()
                    + "/detalleTicket?id="
                    + idTicket);

        } catch (IllegalArgumentException e) {
            request.setAttribute(
                    "error",
                    e.getMessage());

            response.sendRedirect(
                    request.getContextPath()
                    + "/detalleTicket?id="
                    + idTicket);
        }
    }
}
