package WebServlet;

import modelo.Usuario;
import repositorio.UsuarioRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/agentes")
public class AgentesServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // ==========================================
        // VALIDAR SESIÓN
        // ==========================================
        if (session == null
                || session.getAttribute("idUsuario") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        int idRol = (Integer) session.getAttribute("idRol");

        // ==========================================
        // SOLO ADMINISTRADOR
        // ==========================================
        if (idRol != 3) {

            response.sendRedirect(
                    request.getContextPath() + "/tickets");

            return;
        }

        // ==========================================
        // REPOSITORIO
        // ==========================================
        UsuarioRepository usuarioRepository
                = (UsuarioRepository) getServletContext()
                        .getAttribute(
                                AppContextListener.USUARIO_REPOSITORY);

        try {

            // ==========================================
            // OBTENER TODOS LOS AGENTES
            // ==========================================
            List<Usuario> agentes
                    = usuarioRepository.listarAgentes();

            request.setAttribute(
                    "agentes",
                    agentes);

            // ==========================================
            // ENVIAR A LA VISTA
            // ==========================================
            request.getRequestDispatcher(
                    "/WEB-INF/jsp/Administrador/agentes.jsp")
                    .forward(request, response);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error consultando los agentes",
                    e);
        }
    }
}
