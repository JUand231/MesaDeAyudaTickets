package repositorio.jdbc;

import modelo.Ticket;
import modelo.estado.EstadoTicketFactory;
import repositorio.TicketRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

/**
 * Implementacion de TicketRepository contra SQL Server, usando JDBC puro.
 *
 * TicketService no necesita conocer si utiliza esta implementacion o
 * TicketRepositoryEnMemoria.
 */
public class TicketRepositoryJdbc implements TicketRepository {

    @Override
    public Ticket guardar(Ticket ticket) {
        if (ticket.getIdTicket() == 0) {
            return insertar(ticket);
        }

        return actualizar(ticket);
    }

    private Ticket insertar(Ticket ticket) {

        String sql = "INSERT INTO Ticket "
                + "(Titulo, Descripcion, IdCategoria, IdPrioridad, "
                + "IdSolicitante, IdAgente, Estado, FechaCreacion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.obtener(); PreparedStatement stmt = con.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS)) {

            mapearParametros(stmt, ticket);

            stmt.executeUpdate();

            try (ResultSet generado = stmt.getGeneratedKeys()) {

                if (generado.next()) {
                    ticket.setIdTicket(generado.getInt(1));
                }
            }

            return ticket;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error guardando el ticket", e);
        }
    }

    @Override
    public Ticket actualizar(Ticket ticket) {

        String sql = "UPDATE Ticket SET "
                + "Titulo = ?, "
                + "Descripcion = ?, "
                + "IdCategoria = ?, "
                + "IdPrioridad = ?, "
                + "IdSolicitante = ?, "
                + "IdAgente = ?, "
                + "Estado = ?, "
                + "FechaCreacion = ? "
                + "WHERE Id = ?";

        try (Connection con = ConexionDB.obtener(); PreparedStatement stmt = con.prepareStatement(sql)) {

            mapearParametros(stmt, ticket);

            stmt.setInt(9, ticket.getIdTicket());

            stmt.executeUpdate();

            return ticket;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error actualizando el ticket "
                    + ticket.getIdTicket(), e);
        }
    }

    private void mapearParametros(
            PreparedStatement stmt,
            Ticket ticket) throws SQLException {

        stmt.setString(1, ticket.getTitulo());

        stmt.setString(2, ticket.getDescripcion());

        stmt.setInt(3, ticket.getIdCategoria());

        stmt.setInt(4, ticket.getIdPrioridad());

        stmt.setInt(5, ticket.getIdSolicitante());

        if (ticket.getIdAgente() != null) {
            stmt.setInt(6, ticket.getIdAgente());
        } else {
            stmt.setNull(6, Types.INTEGER);
        }

        stmt.setString(7, ticket.getEstadoNombre());

        stmt.setTimestamp(
                8,
                Timestamp.valueOf(ticket.getFechaCreacion()));
    }

    @Override
    public Optional<Ticket> buscarPorId(int idTicket) {

        String sql = "SELECT * FROM Ticket WHERE Id = ?";

        try (Connection con = ConexionDB.obtener(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idTicket);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }

                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error buscando el ticket " + idTicket, e);
        }
    }

    @Override
    public List<Ticket> listarTodos() {

        return listarConFiltro(
                "SELECT * FROM Ticket",
                null,
                0);
    }

    @Override
    public List<Ticket> listarPorSolicitante(
            int idSolicitante) {

        return listarConFiltro(
                "SELECT * FROM Ticket WHERE IdSolicitante = ?",
                "solicitante",
                idSolicitante);
    }

    @Override
    public List<Ticket> listarPorAgente(
            int idAgente) {

        return listarConFiltro(
                "SELECT * FROM Ticket WHERE IdAgente = ?",
                "agente",
                idAgente);
    }

    private List<Ticket> listarConFiltro(
            String sql,
            String filtro,
            int valor) {

        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = ConexionDB.obtener(); PreparedStatement stmt = con.prepareStatement(sql)) {

            if (filtro != null) {
                stmt.setInt(1, valor);
            }

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    tickets.add(mapear(rs));
                }
            }

            return tickets;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error listando tickets", e);
        }
    }

    @Override
    public void eliminar(int idTicket) {

        String sql = "DELETE FROM Ticket WHERE Id = ?";

        try (Connection con = ConexionDB.obtener(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idTicket);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error eliminando el ticket " + idTicket, e);
        }
    }

    /**
     * Convierte una fila de la tabla Ticket en un objeto de dominio.
     */
    private Ticket mapear(ResultSet rs) throws SQLException {

        Ticket ticket = new Ticket();

        ticket.setIdTicket(
                rs.getInt("Id"));

        ticket.setTitulo(
                rs.getString("Titulo"));

        ticket.setDescripcion(
                rs.getString("Descripcion"));

        ticket.setIdCategoria(
                rs.getInt("IdCategoria"));

        ticket.setIdPrioridad(
                rs.getInt("IdPrioridad"));

        ticket.setIdSolicitante(
                rs.getInt("IdSolicitante"));

        int idAgente = rs.getInt("IdAgente");

        ticket.setIdAgente(
                rs.wasNull() ? null : idAgente);

        ticket.setEstado(
                EstadoTicketFactory.desde(
                        rs.getString("Estado")));

        Timestamp fechaCreacion
                = rs.getTimestamp("FechaCreacion");

        if (fechaCreacion != null) {
            ticket.setFechaCreacion(
                    fechaCreacion.toLocalDateTime());
        }

        return ticket;
    }
}
