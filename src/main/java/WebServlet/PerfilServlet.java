package WebServlet;

import modelo.Usuario;
import repositorio.UsuarioRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/perfil")
public class PerfilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int idUsuario = (Integer) session.getAttribute("idUsuario");
        UsuarioRepository usuarioRepository
                = (UsuarioRepository) getServletContext().getAttribute(AppContextListener.USUARIO_REPOSITORY);

        Optional<Usuario> usuarioOpt
                = usuarioRepository.buscarPorId(idUsuario);

        if (usuarioOpt.isEmpty()) {
            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        Usuario usuario = usuarioOpt.get();

        request.setAttribute("usuario", usuario);

        request.setAttribute(
                "nombreRol",
                nombreRol(usuario.getIdRol())
        );

        request.getRequestDispatcher("/WEB-INF/jsp/perfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int idUsuario = (Integer) session.getAttribute("idUsuario");
        UsuarioRepository usuarioRepository
                = (UsuarioRepository) getServletContext().getAttribute(AppContextListener.USUARIO_REPOSITORY);

        String correo = request.getParameter("correo");
        String nuevaContrasena = request.getParameter("nuevaContrasena");
        String confirmarContrasena = request.getParameter("confirmarContrasena");

        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(idUsuario);

            if (usuarioOpt.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            Usuario usuario = usuarioOpt.get();

            // Validaciones básicas
            if (correo == null || correo.trim().isEmpty()) {
                request.setAttribute("error", "El correo no puede estar vacío.");
                request.setAttribute("usuario", usuario);
                request.setAttribute("nombreRol", nombreRol(usuario.getIdRol()));
                request.getRequestDispatcher("/WEB-INF/jsp/perfil.jsp").forward(request, response);
                return;
            }

            if (nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
                if (!nuevaContrasena.equals(confirmarContrasena)) {
                    request.setAttribute("error", "Las contraseñas no coinciden.");
                    request.setAttribute("usuario", usuario);
                    request.setAttribute("nombreRol", nombreRol(usuario.getIdRol()));
                    request.getRequestDispatcher("/WEB-INF/jsp/perfil.jsp").forward(request, response);
                    return;
                }
                usuario.setContrasena(nuevaContrasena);
            }

            usuario.setCorreo(correo.trim());
            usuarioRepository.actualizar(usuario);

            request.setAttribute("exito", "Perfil actualizado correctamente.");
            request.setAttribute("usuario", usuario);
            request.setAttribute("nombreRol", nombreRol(usuario.getIdRol()));

        } catch (SQLException e) {
            throw new ServletException("Error actualizando el perfil del usuario", e);
        }

        request.getRequestDispatcher("/WEB-INF/jsp/perfil.jsp").forward(request, response);
    }

    private String nombreRol(int idRol) {
        switch (idRol) {
            case 1:
                return "Solicitante";
            case 2:
                return "Agente";
            default:
                return "Administrador";
        }
    }
}
