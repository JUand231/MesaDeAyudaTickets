package repositorio.jdbc;

import modelo.Prioridad;
import repositorio.PrioridadRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrioridadRepositoryJdbc implements PrioridadRepository {

    @Override
    public Optional<Prioridad> buscarPorId(int idPrioridad) {

        String sql = "SELECT Id, Tipo, HorasSLA "
                + "FROM Prioridad "
                + "WHERE Id = ?";

        try (
                Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idPrioridad);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }

                return Optional.empty();
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error buscando prioridad con id "
                    + idPrioridad,
                    e
            );
        }
    }

    @Override
    public List<Prioridad> listarTodas() throws SQLException {
        String sql = "SELECT Id, Tipo, HorasSLA FROM Prioridad";
        List<Prioridad> prioridades = new ArrayList<>();
        try (Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                prioridades.add(mapear(rs));
            }
            return prioridades;
        }
    }

    private Prioridad mapear(ResultSet rs) throws SQLException {
        Prioridad prioridad = new Prioridad();
        prioridad.setIdPrioridad(rs.getInt("Id"));
        prioridad.setTipo(rs.getString("Tipo"));
        prioridad.setHorasSLA(rs.getInt("HorasSLA"));
        return prioridad;
    }
}
