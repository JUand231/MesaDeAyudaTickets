package repositorio;

import modelo.Prioridad;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PrioridadRepository {

    Optional<Prioridad> buscarPorId(int idPrioridad) throws SQLException;

    List<Prioridad> listarTodas() throws SQLException;
}
