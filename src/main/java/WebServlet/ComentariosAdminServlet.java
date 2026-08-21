package WebServlet;

import modelo.Comentario;
import repositorio.ComentarioRepository;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/comentariosAdmin")
public class ComentariosAdminServlet extends HttpServlet {

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
                    request.getContextPath() + "/login"
            );

            return;
        }

        int idRol = (Integer) session.getAttribute("idRol");

        // Solo administrador
        if (idRol != 3) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permiso para acceder a esta sección."
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
                    "ComentarioRepository no está configurado"
            );
        }

        String buscar = request.getParameter("buscar");
        String rol = request.getParameter("rol");

        System.out.println("=================================");
        System.out.println("PARAMETRO buscar = [" + buscar + "]");
        System.out.println("PARAMETRO rol = [" + rol + "]");
        System.out.println("=================================");

        if (buscar == null) {
            buscar = "";
        }

        if (rol == null) {
            rol = "";
        }

        List<Comentario> comentarios
                = comentarioRepository.buscarAdmin(buscar, rol);

        System.out.println(
                "BUSQUEDA: [" + buscar + "]"
                + " | ROL: [" + rol + "]"
                + " | RESULTADOS: " + comentarios.size()
        );

        request.setAttribute(
                "comentarios",
                comentarios
        );

        request.getRequestDispatcher(
                "/WEB-INF/jsp/Administrador/comentariosAdmin.jsp"
        ).forward(request, response);
    }
}
