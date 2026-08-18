package repositorio;

import modelo.Categoria;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepository {

    Optional<Categoria> buscarPorId(int idCategoria);

    List<Categoria> listarTodas() throws SQLException;

    void crear(Categoria categoria) throws SQLException;

    void actualizar(Categoria categoria) throws SQLException;

    void eliminar(int idCategoria) throws SQLException;
}
