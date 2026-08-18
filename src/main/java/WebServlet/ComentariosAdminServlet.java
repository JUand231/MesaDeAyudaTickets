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

        // Administrador
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

        List<Comentario> comentarios
                = comentarioRepository.listarTodos();

        System.out.println("COMENTARIOS ENCONTRADOS: " + comentarios.size());

        for (Comentario c : comentarios) {
            System.out.println(
                    "Comentario: "
                    + c.getIdComentario()
                    + " | Usuario: "
                    + c.getNombreUsuario()
                    + " | Ticket: "
                    + c.getIdTicket()
            );
        }

        request.setAttribute(
                "comentarios",
                comentarios
        );

        request.getRequestDispatcher(
                "/WEB-INF/jsp/Administrador/comentariosAdmin.jsp"
        ).forward(request, response);
    }
}
