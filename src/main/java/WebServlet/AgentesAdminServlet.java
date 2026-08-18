package WebServlet;

import modelo.Usuario;
import repositorio.UsuarioRepository;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/agentes")
public class AgentesAdminServlet extends HttpServlet {

    private UsuarioRepository usuarioRepository;

    @Override
    public void init() throws ServletException {

        usuarioRepository = (UsuarioRepository) getServletContext()
                .getAttribute(AppContextListener.USUARIO_REPOSITORY);

        if (usuarioRepository == null) {
            throw new ServletException(
                    "No se encontró UsuarioRepository en el contexto de la aplicación."
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            List<Usuario> usuarios = usuarioRepository.listar();

            List<Usuario> agentes = usuarios.stream()
                    .filter(usuario -> usuario.getIdRol() == 2)
                    .collect(java.util.stream.Collectors.toList());

            request.setAttribute("agentes", agentes);

            request.setAttribute("totalAgentes", agentes.size());

            request.setAttribute("agentesActivos", agentes.size());
            request.setAttribute("agentesInactivos", 0);

            request.getRequestDispatcher(
                    "/WEB-INF/jsp/Administrador/agentesAdmin.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h2>Error al cargar los agentes</h2>");
            response.getWriter().println("<p>" + e.getMessage() + "</p>");
        }
    }
}
