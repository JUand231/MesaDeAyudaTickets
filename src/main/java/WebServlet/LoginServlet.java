package WebServlet;

import modelo.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import repositorio.UsuarioRepository;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UsuarioRepository usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = (UsuarioRepository) getServletContext().getAttribute(AppContextListener.USUARIO_REPOSITORY);
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        try {

            Usuario usuario
                    = usuarioDAO.validarLogin(correo, contrasena);

            if (usuario != null) {

                HttpSession session = request.getSession();

                session.setAttribute("usuario", usuario);
                session.setAttribute("idUsuario", usuario.getIdUsuario());
                session.setAttribute("nombreUsuario", usuario.getNombre());
                session.setAttribute("idRol", usuario.getIdRol());

                switch (usuario.getIdRol()) {

                    case 1:
                        response.sendRedirect(
                                request.getContextPath() + "/mis-tickets"
                        );
                        break;

                    case 2:
                        response.sendRedirect(
                                request.getContextPath() + "/tickets"
                        );
                        break;

                    case 3:
                        response.sendRedirect(
                                request.getContextPath() + "/dashboard"
                        );
                        break;

                    default:
                        session.invalidate();

                        response.sendRedirect(
                                request.getContextPath() + "/?error=1"
                        );
                        break;
                }

            } else {

                response.sendRedirect(
                        request.getContextPath() + "/?error=1"
                );
            }

        } catch (Exception e) {

            throw new ServletException(
                    "Error al validar el inicio de sesión", e
            );
        }
    }
}
