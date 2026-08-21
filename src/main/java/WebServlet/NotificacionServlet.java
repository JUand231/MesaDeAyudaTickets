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
import java.util.Optional;

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
                = (Integer) session.getAttribute("idUsuario");

        int idRol
                = (Integer) session.getAttribute("idRol");

        List<Notificacion> notificaciones
                = notificacionRepository.listarPorUsuario(
                        idUsuario
                );

        int noLeidas
                = notificacionRepository.contarNoLeidas(
                        idUsuario
                );

        request.setAttribute(
                "notificaciones",
                notificaciones
        );

        request.setAttribute(
                "noLeidas",
                noLeidas
        );

        String dashboard;

        switch (idRol) {

            case 1:

                dashboard = "/dashboardSolicitante";

                break;

            case 2:

                dashboard = "/dashboardAgente";

                break;

            case 3:

                dashboard = "/dashboardAdmin";

                break;

            default:

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Rol de usuario no válido."
                );

                return;
        }

        request.setAttribute(
                "dashboard",
                dashboard
        );

        request.getRequestDispatcher(
                "/WEB-INF/jsp/notificaciones.jsp"
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
                = (Integer) session.getAttribute("idUsuario");

        String accion
                = request.getParameter("accion");

        try {

            if ("leer".equals(accion)) {

                String idParametro
                        = request.getParameter(
                                "idNotificacion"
                        );

                if (idParametro == null
                        || idParametro.trim().isEmpty()) {

                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "ID de notificación obligatorio."
                    );

                    return;
                }

                int idNotificacion
                        = Integer.parseInt(
                                idParametro
                        );

                Optional<Notificacion> resultado
                        = notificacionRepository.buscarPorId(
                                idNotificacion
                        );

                if (!resultado.isPresent()) {

                    response.sendError(
                            HttpServletResponse.SC_NOT_FOUND,
                            "La notificación no existe."
                    );

                    return;
                }

                Notificacion notificacion
                        = resultado.get();

                if (notificacion.getIdUsuario()
                        != idUsuario) {

                    response.sendError(
                            HttpServletResponse.SC_FORBIDDEN,
                            "No puedes modificar esta notificación."
                    );

                    return;
                }

                notificacionRepository.marcarComoLeida(
                        idNotificacion
                );

            } else if ("leerTodas".equals(accion)) {

                notificacionRepository.marcarTodasComoLeidas(
                        idUsuario
                );

            } else {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Acción de notificación no válida."
                );

                return;
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
