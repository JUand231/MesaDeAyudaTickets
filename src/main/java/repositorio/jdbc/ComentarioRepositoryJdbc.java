package repositorio.jdbc;

import modelo.Comentario;
import repositorio.ComentarioRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComentarioRepositoryJdbc
        implements ComentarioRepository {

    // ==========================================================
    // GUARDAR
    // ==========================================================
    @Override
    public Comentario guardar(Comentario comentario) {

        String sql = "INSERT INTO Comentarios "
                + "(IdTicket, IdUsuario, Texto, Fecha) "
                + "VALUES (?, ?, ?, ?)";

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt = con.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(
                    1,
                    comentario.getIdTicket()
            );

            stmt.setInt(
                    2,
                    comentario.getIdUsuario()
            );

            // IMPORTANTE:
            // NVARCHAR en SQL Server
            stmt.setNString(
                    3,
                    comentario.getTexto()
            );

            stmt.setTimestamp(
                    4,
                    Timestamp.valueOf(
                            comentario.getFecha()
                    )
            );

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {

                if (rs.next()) {

                    comentario.setIdComentario(
                            rs.getInt(1)
                    );
                }
            }

            return comentario;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error guardando el comentario",
                    e
            );
        }
    }

    // ==========================================================
    // BUSCAR POR ID
    // ==========================================================
    @Override
    public Optional<Comentario> buscarPorId(
            int idComentario) {

        String sql
                = "SELECT * "
                + "FROM Comentarios "
                + "WHERE Id = ?";

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt
                = con.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    idComentario
            );

            try (ResultSet rs
                    = stmt.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(
                            mapear(rs)
                    );
                }

                return Optional.empty();
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error buscando el comentario "
                    + idComentario,
                    e
            );
        }
    }

    // ==========================================================
    // LISTAR POR TICKET
    // ==========================================================
    @Override
    public List<Comentario> listarPorTicket(
            int idTicket) {

        String sql
                = "SELECT "
                + "c.Id, "
                + "c.IdTicket, "
                + "c.IdUsuario, "
                + "c.Texto, "
                + "c.Fecha, "
                + "u.Nombre AS NombreUsuario, "
                + "u.IdRol, "
                + "t.Titulo AS TituloTicket "
                + "FROM Comentarios c "
                + "LEFT JOIN Usuario u "
                + "ON c.IdUsuario = u.Id "
                + "LEFT JOIN Ticket t "
                + "ON c.IdTicket = t.Id "
                + "WHERE c.IdTicket = ? "
                + "ORDER BY c.Fecha ASC";

        List<Comentario> comentarios
                = new ArrayList<>();

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt
                = con.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    idTicket
            );

            try (ResultSet rs
                    = stmt.executeQuery()) {

                while (rs.next()) {

                    Comentario comentario
                            = mapearCompleto(rs);

                    comentarios.add(
                            comentario
                    );
                }
            }

            return comentarios;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error listando comentarios del ticket "
                    + idTicket,
                    e
            );
        }
    }

    // ==========================================================
    // LISTAR POR SOLICITANTE
    // ==========================================================
    @Override
    public List<Comentario> listarPorSolicitante(
            int idUsuario) {

        String sql
                = "SELECT "
                + "c.Id, "
                + "c.IdTicket, "
                + "c.IdUsuario, "
                + "c.Texto, "
                + "c.Fecha, "
                + "u.Nombre AS NombreUsuario, "
                + "u.IdRol, "
                + "t.Titulo AS TituloTicket "
                + "FROM Comentarios c "
                + "INNER JOIN Ticket t "
                + "ON c.IdTicket = t.Id "
                + "LEFT JOIN Usuario u "
                + "ON c.IdUsuario = u.Id "
                + "WHERE t.IdSolicitante = ? "
                + "ORDER BY c.Fecha DESC";

        List<Comentario> comentarios
                = new ArrayList<>();

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt
                = con.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    idUsuario
            );

            try (ResultSet rs
                    = stmt.executeQuery()) {

                while (rs.next()) {

                    comentarios.add(
                            mapearCompleto(rs)
                    );
                }
            }

            return comentarios;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error listando comentarios del solicitante",
                    e
            );
        }
    }

    // ==========================================================
    // VERIFICAR PROPIETARIO
    // ==========================================================
    @Override
    public boolean ticketPerteneceAUsuario(
            int idTicket,
            int idUsuario) {

        String sql
                = "SELECT COUNT(*) "
                + "FROM Ticket "
                + "WHERE Id = ? "
                + "AND IdSolicitante = ?";

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt
                = con.prepareStatement(sql)) {

            stmt.setInt(1, idTicket);
            stmt.setInt(2, idUsuario);

            try (ResultSet rs
                    = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }

                return false;
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error verificando propietario del ticket",
                    e
            );
        }
    }

    // ==========================================================
    // LISTAR TODOS
    // ==========================================================
    @Override
    public List<Comentario> listarTodos() {

        String sql
                = "SELECT "
                + "c.Id, "
                + "c.IdTicket, "
                + "c.IdUsuario, "
                + "c.Texto, "
                + "c.Fecha, "
                + "u.Nombre AS NombreUsuario, "
                + "u.IdRol, "
                + "t.Titulo AS TituloTicket "
                + "FROM Comentarios c "
                + "LEFT JOIN Usuario u "
                + "ON c.IdUsuario = u.Id "
                + "LEFT JOIN Ticket t "
                + "ON c.IdTicket = t.Id "
                + "ORDER BY c.Fecha DESC";

        List<Comentario> comentarios
                = new ArrayList<>();

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt
                = con.prepareStatement(sql); ResultSet rs
                = stmt.executeQuery()) {

            while (rs.next()) {

                comentarios.add(
                        mapearCompleto(rs)
                );
            }

            return comentarios;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error listando todos los comentarios",
                    e
            );
        }
    }

    // ==========================================================
    // BUSCAR ADMIN
    // ==========================================================
    @Override
    public List<Comentario> buscarAdmin(
            String buscar,
            String rol) {

        String sql
                = "SELECT "
                + "c.Id, "
                + "c.IdTicket, "
                + "c.IdUsuario, "
                + "c.Texto, "
                + "c.Fecha, "
                + "u.Nombre AS NombreUsuario, "
                + "u.IdRol, "
                + "t.Titulo AS TituloTicket "
                + "FROM Comentarios c "
                + "LEFT JOIN Usuario u "
                + "ON c.IdUsuario = u.Id "
                + "LEFT JOIN Ticket t "
                + "ON c.IdTicket = t.Id "
                + "WHERE 1 = 1 ";

        if (buscar != null
                && !buscar.trim().isEmpty()) {

            sql
                    += "AND ("
                    + "CAST(c.IdTicket AS VARCHAR) LIKE ? "
                    + "OR c.Texto LIKE ? "
                    + "OR u.Nombre LIKE ? "
                    + "OR t.Titulo LIKE ?"
                    + ") ";
        }

        if (rol != null
                && !rol.trim().isEmpty()) {

            if ("AGENTE".equalsIgnoreCase(rol)) {

                sql += "AND u.IdRol = 2 ";

            } else if ("SOLICITANTE"
                    .equalsIgnoreCase(rol)) {

                sql += "AND u.IdRol = 1 ";

            } else if ("ADMINISTRADOR"
                    .equalsIgnoreCase(rol)) {

                sql += "AND u.IdRol = 3 ";
            }
        }

        sql += "ORDER BY c.Fecha DESC";

        List<Comentario> comentarios
                = new ArrayList<>();

        try (
                Connection con = ConexionDB.obtener(); PreparedStatement stmt
                = con.prepareStatement(sql)) {

            int posicion = 1;

            if (buscar != null
                    && !buscar.trim().isEmpty()) {

                String filtro
                        = "%" + buscar.trim() + "%";

                stmt.setString(
                        posicion++,
                        filtro
                );

                stmt.setString(
                        posicion++,
                        filtro
                );

                stmt.setString(
                        posicion++,
                        filtro
                );

                stmt.setString(
                        posicion++,
                        filtro
                );
            }

            try (ResultSet rs
                    = stmt.executeQuery()) {

                while (rs.next()) {

                    comentarios.add(
                            mapearCompleto(rs)
                    );
                }
            }

            return comentarios;

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error buscando comentarios",
                    e
            );
        }
    }

    // ==========================================================
    // MAPEAR BÁSICO
    // ==========================================================
    private Comentario mapear(
            ResultSet rs)
            throws SQLException {

        Comentario comentario
                = new Comentario();

        comentario.setIdComentario(
                rs.getInt("Id")
        );

        comentario.setIdTicket(
                rs.getInt("IdTicket")
        );

        comentario.setIdUsuario(
                rs.getInt("IdUsuario")
        );

        comentario.setTexto(
                rs.getString("Texto")
        );

        Timestamp fecha
                = rs.getTimestamp("Fecha");

        if (fecha != null) {

            comentario.setFecha(
                    fecha.toLocalDateTime()
            );
        }

        return comentario;
    }

    // ==========================================================
    // MAPEAR COMPLETO
    // ==========================================================
    private Comentario mapearCompleto(
            ResultSet rs)
            throws SQLException {

        Comentario comentario
                = mapear(rs);

        comentario.setNombreUsuario(
                rs.getString("NombreUsuario")
        );

        comentario.setTituloTicket(
                rs.getString("TituloTicket")
        );

        int idRol
                = rs.getInt("IdRol");

        switch (idRol) {

            case 1:
                comentario.setNombreRol(
                        "SOLICITANTE"
                );
                break;

            case 2:
                comentario.setNombreRol(
                        "AGENTE"
                );
                break;

            case 3:
                comentario.setNombreRol(
                        "ADMINISTRADOR"
                );
                break;

            default:
                comentario.setNombreRol(
                        "USUARIO"
                );
                break;
        }

        return comentario;
    }
}
