package servicio;

import modelo.Categoria;
import repositorio.CategoriaRepository;
import repositorio.jdbc.CategoriaRepositoryJdbc;

import java.sql.SQLException;
import java.util.List;

public class CategoriaServicio {

    private final CategoriaRepository repositorio = new CategoriaRepositoryJdbc();

    public List<Categoria> listarCategorias() throws SQLException {
        return repositorio.listarTodas();
    }

    public void crearCategoria(String nombre) throws SQLException {
        Categoria cat = new Categoria();
        cat.setNombreCategoria(nombre);
        repositorio.crear(cat);
    }

    public void editarCategoria(int id, String nombre) throws SQLException {
        Categoria cat = new Categoria();
        cat.setIdCategoria(id);
        cat.setNombreCategoria(nombre);
        repositorio.actualizar(cat);
    }

    public void eliminarCategoria(int id) throws SQLException {
        repositorio.eliminar(id);
    }
}
