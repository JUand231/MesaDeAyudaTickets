package WebServlet;

import modelo.Categoria;
import servicio.CategoriaServicio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/categorias")
public class CategoriasAdminServlet extends HttpServlet {

    private final CategoriaServicio categoriaServicio = new CategoriaServicio();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {
            if ("eliminar".equals(accion)) {
                String idParam = request.getParameter("id");
                if (idParam != null) {
                    categoriaServicio.eliminarCategoria(Integer.parseInt(idParam));
                }
                response.sendRedirect(request.getContextPath() + "/categorias");
                return;
            }

            List<Categoria> lista = categoriaServicio.listarCategorias();

            request.setAttribute("listaCategorias", lista);
            request.setAttribute("totalCategorias", lista.size());

            request.getRequestDispatcher("/WEB-INF/jsp/Administrador/categoriasAdmin.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al listar categorías", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {
            if ("crear".equals(accion)) {
                String nombre = request.getParameter("nombreCategoria");
                categoriaServicio.crearCategoria(nombre);

            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("idCategoria"));
                String nombre = request.getParameter("nombreCategoria");
                categoriaServicio.editarCategoria(id, nombre);
            }

            response.sendRedirect(request.getContextPath() + "/categorias");

        } catch (SQLException e) {
            throw new ServletException("Error al guardar categoría", e);
        }
    }
}
