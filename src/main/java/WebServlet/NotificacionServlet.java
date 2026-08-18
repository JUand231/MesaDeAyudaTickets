package WebServlet;

import modelo.Notificacion;
import repositorio.NotificacionRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/notificaciones")
public class NotificacionServlet extends HttpServlet {

    private NotificacionRepository notificacionRepository;

    @Override
    public void init() throws ServletException {

        notificacionRepository
                = (NotificacionRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.NOTIFICACION_REPOSITORY
                        );

        if (notificacionRepository == null) {
            throw new ServletException(
                    "No se encontró NotificacionRepository en el contexto."
            );
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("usuario") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );

            return;
        }

        int idUsuario
                = (Integer) session.getAttribute("idUsuario");

        List<Notificacion> notificaciones
                = notificacionRepository.listarPorUsuario(idUsuario);

        int noLeidas
                = notificacionRepository.contarNoLeidas(idUsuario);

        request.setAttribute(
                "notificaciones",
                notificaciones
        );

        request.setAttribute(
                "noLeidas",
                noLeidas
        );

        request.getRequestDispatcher(
                "/WEB-INF/jsp/notificaciones.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("usuario") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );

            return;
        }

        int idUsuario
                = (Integer) session.getAttribute("idUsuario");

        String accion
                = request.getParameter("accion");

        try {

            if ("leer".equals(accion)) {

                int idNotificacion
                        = Integer.parseInt(
                                request.getParameter(
                                        "idNotificacion"
                                )
                        );

                notificacionRepository.marcarComoLeida(
                        idNotificacion
                );

            } else if ("leerTodas".equals(accion)) {

                notificacionRepository.marcarTodasComoLeidas(
                        idUsuario
                );
            }

            response.sendRedirect(
                    request.getContextPath()
                    + "/notificaciones"
            );

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "ID de notificación inválido."
            );
        }
    }
}
