package WebServlet;

import modelo.Comentario;
import repositorio.ComentarioRepository;
import servicio.TicketService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/comentarios")
public class ComentariosSolicitanteServlet
        extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session
                = request.getSession(false);

        if (session == null
                || session.getAttribute("idUsuario") == null
                || session.getAttribute("idRol") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login"
            );

            return;
        }

        int idUsuario
                = (Integer) session.getAttribute(
                        "idUsuario"
                );

        int idRol
                = (Integer) session.getAttribute(
                        "idRol"
                );

        if (idRol != 1) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Solo los solicitantes pueden acceder aquí."
            );

            return;
        }

        ComentarioRepository comentarioRepository
                = (ComentarioRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.COMENTARIO_REPOSITORY
                        );

        if (comentarioRepository == null) {

            throw new ServletException(
                    "ComentarioRepository no está configurado."
            );
        }

        List<Comentario> comentarios
                = comentarioRepository.listarPorSolicitante(
                        idUsuario
                );

        request.setAttribute(
                "comentarios",
                comentarios
        );

        request.getRequestDispatcher(
                "/WEB-INF/jsp/Solicitante/comentariosSolicitante.jsp"
        ).forward(
                request,
                response
        );
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding(
                "UTF-8"
        );

        HttpSession session
                = request.getSession(false);

        if (session == null
                || session.getAttribute("idUsuario") == null
                || session.getAttribute("idRol") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login"
            );

            return;
        }

        int idUsuario
                = (Integer) session.getAttribute(
                        "idUsuario"
                );

        int idRol
                = (Integer) session.getAttribute(
                        "idRol"
                );

        if (idRol != 1) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permiso para comentar."
            );

            return;
        }

        String idTicketStr
                = request.getParameter(
                        "idTicket"
                );

        String texto
                = request.getParameter(
                        "texto"
                );

        if (idTicketStr == null
                || idTicketStr.trim().isEmpty()
                || texto == null
                || texto.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/comentarios"
            );

            return;
        }

        int idTicket;

        try {

            idTicket
                    = Integer.parseInt(
                            idTicketStr
                    );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/comentarios"
            );

            return;
        }

        TicketService ticketService
                = (TicketService) getServletContext()
                        .getAttribute(
                                AppContextListener.TICKET_SERVICE
                        );

        if (ticketService == null) {

            throw new ServletException(
                    "TicketService no está configurado."
            );
        }

        try {

            ticketService.agregarComentario(
                    idTicket,
                    idUsuario,
                    idRol,
                    texto
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/comentarios"
            );

        } catch (IllegalArgumentException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage()
            );
        }
    }
}
