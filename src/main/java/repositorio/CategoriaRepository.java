package repositorio;

import modelo.Categoria;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepository {

    Optional<Categoria> buscarPorId(int idCategoria);

    List<Categoria> listarTodas() throws SQLException;
}
