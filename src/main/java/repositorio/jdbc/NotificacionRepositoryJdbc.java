package repositorio.jdbc;

import modelo.Notificacion;
import repositorio.NotificacionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

public class NotificacionRepositoryJdbc implements NotificacionRepository {

    @Override
    public Notificacion guardar(Notificacion notificacion) {

        String sql = "INSERT INTO Notificaciones "
                + "(IdUsuario, IdTicket, Mensaje, Leida, Fecha) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt = con.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, notificacion.getIdUsuario());
            stmt.setInt(2, notificacion.getIdTicket());
            stmt.setString(3, notificacion.getMensaje());
            stmt.setBoolean(4, notificacion.isLeida());
            stmt.setTimestamp(
                    5,
                    Timestamp.valueOf(notificacion.getFecha())
            );

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    notificacion.setIdNotificacion(rs.getInt(1));
                }
            }

            return notificacion;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error guardando la notificacion",
                    e
            );
        }
    }

    @Override
    public Optional<Notificacion> buscarPorId(int idNotificacion) {

        String sql = "SELECT * FROM Notificaciones WHERE Id = ?";

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idNotificacion);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }

                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error buscando la notificacion "
                    + idNotificacion,
                    e
            );
        }
    }

    @Override
    public List<Notificacion> listarPorUsuario(int idUsuario) {

        String sql = "SELECT * "
                + "FROM Notificaciones "
                + "WHERE IdUsuario = ? "
                + "ORDER BY Fecha DESC";

        List<Notificacion> notificaciones = new ArrayList<>();

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    notificaciones.add(mapear(rs));
                }
            }

            return notificaciones;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error listando notificaciones del usuario",
                    e
            );
        }
    }

    @Override
    public List<Notificacion> listarNoLeidas(int idUsuario) {

        String sql = "SELECT * "
                + "FROM Notificaciones "
                + "WHERE IdUsuario = ? "
                + "AND Leida = 0 "
                + "ORDER BY Fecha DESC";

        List<Notificacion> notificaciones = new ArrayList<>();

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    notificaciones.add(mapear(rs));
                }
            }

            return notificaciones;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error listando notificaciones no leidas",
                    e
            );
        }
    }

    @Override
    public int contarNoLeidas(int idUsuario) {

        String sql = "SELECT COUNT(*) "
                + "FROM Notificaciones "
                + "WHERE IdUsuario = ? "
                + "AND Leida = 0";

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error contando notificaciones no leidas",
                    e
            );
        }
    }

    @Override
    public void marcarComoLeida(int idNotificacion) {

        String sql = "UPDATE Notificaciones "
                + "SET Leida = 1 "
                + "WHERE Id = ?";

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idNotificacion);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error marcando notificacion como leida",
                    e
            );
        }
    }

    @Override
    public void marcarTodasComoLeidas(int idUsuario) {

        String sql = "UPDATE Notificaciones "
                + "SET Leida = 1 "
                + "WHERE IdUsuario = ? "
                + "AND Leida = 0";

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error marcando todas las notificaciones como leidas",
                    e
            );
        }
    }

    private Notificacion mapear(ResultSet rs) throws SQLException {

        Notificacion notificacion = new Notificacion();

        notificacion.setIdNotificacion(
                rs.getInt("Id")
        );

        notificacion.setIdUsuario(
                rs.getInt("IdUsuario")
        );

        notificacion.setIdTicket(
                rs.getInt("IdTicket")
        );

        notificacion.setMensaje(
                rs.getString("Mensaje")
        );

        notificacion.setLeida(
                rs.getBoolean("Leida")
        );

        Timestamp fecha = rs.getTimestamp("Fecha");

        if (fecha != null) {
            notificacion.setFecha(
                    fecha.toLocalDateTime()
            );
        }

        return notificacion;
    }
}
