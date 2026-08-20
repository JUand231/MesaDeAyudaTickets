package WebServlet;

import modelo.Categoria;
import repositorio.CategoriaRepository;
import servicio.roles.AccionesSolicitante;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ticket/nuevo")
public class NuevoTicketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        cargarListas(request);
        request.getRequestDispatcher("/WEB-INF/jsp/Solicitante/nuevo-ticket.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int idSolicitante = (Integer) session.getAttribute("idUsuario");

        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        String idCategoriaStr = request.getParameter("idCategoria");

        AccionesSolicitante ticketService = (AccionesSolicitante) getServletContext().getAttribute(AppContextListener.TICKET_SERVICE);

        try {
            int idCategoria = Integer.parseInt(idCategoriaStr);

            ticketService.crearTicket(titulo, descripcion, idCategoria, idSolicitante);

            response.sendRedirect(request.getContextPath() + "/dashboardSolicitante");

        } catch (IllegalArgumentException e) {
            // titulo/descripcion vacios, o los ids no eran numeros validos
            request.setAttribute("error", e.getMessage());
            cargarListas(request);
            request.getRequestDispatcher("/WEB-INF/jsp/Solicitante/nuevo-ticket.jsp")
                    .forward(request, response);
        }
    }

    private void cargarListas(HttpServletRequest request) throws ServletException {
        CategoriaRepository categoriaRepository = (CategoriaRepository) getServletContext().getAttribute(AppContextListener.CATEGORIA_REPOSITORY);

        try {
            List<Categoria> categorias = categoriaRepository.listarTodas();
            request.setAttribute("categorias", categorias);
        } catch (SQLException e) {
            throw new ServletException("Error cargando categorias", e);
        }
    }
}
