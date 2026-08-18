package repositorio.jdbc;

import modelo.Usuario;
import repositorio.UsuarioRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepositoryJdbc implements UsuarioRepository {

    @Override
    public Usuario validarLogin(String correo, String contrasena)
            throws SQLException {

        String sql = "SELECT Id, Nombre, Correo, Contrasena, IdRol "
                + "FROM Usuario "
                + "WHERE Correo = ? AND Contrasena = ?";

        try (Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }

        return null;
    }

    @Override
    public Optional<Usuario> buscarPorId(int idUsuario)
            throws SQLException {

        String sql = "SELECT Id, Nombre, Correo, Contrasena, IdRol "
                + "FROM Usuario "
                + "WHERE Id = ?";

        try (Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }

                return Optional.empty();
            }
        }
    }

    // ==========================================================
    // LISTAR AGENTES
    // ==========================================================
    @Override
    public List<Usuario> listarAgentes() throws SQLException {

        List<Usuario> agentes = new ArrayList<>();

        String sql = "SELECT Id, Nombre, Correo, Contrasena, IdRol "
                + "FROM Usuario "
                + "WHERE IdRol = 2 "
                + "ORDER BY Nombre ASC";

        try (Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                agentes.add(mapear(rs));
            }
        }

        return agentes;
    }

    // ==========================================================
    // LISTAR TODOS LOS USUARIOS
    // ==========================================================
    @Override
    public List<Usuario> listar() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT Id, Nombre, Correo, Contrasena, IdRol "
                + "FROM Usuario";

        try (Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapear(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Error al listar usuarios", e
            );
        }

        return usuarios;
    }

    // ==========================================================
    // MAPEAR USUARIO
    // ==========================================================
    private Usuario mapear(ResultSet rs)
            throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setIdUsuario(rs.getInt("Id"));
        usuario.setNombre(rs.getString("Nombre"));
        usuario.setCorreo(rs.getString("Correo"));
        usuario.setContrasena(rs.getString("Contrasena"));
        usuario.setIdRol(rs.getInt("IdRol"));

        return usuario;
    }

    // ==========================================================
    // ACTUALIZAR USUARIO
    // ==========================================================
    @Override
    public void actualizar(Usuario usuario)
            throws SQLException {

        String sql = "UPDATE Usuario "
                + "SET Correo = ?, Contrasena = ? "
                + "WHERE Id = ?";

        try (Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, usuario.getCorreo());
            ps.setString(2, usuario.getContrasena());
            ps.setInt(3, usuario.getIdUsuario());

            ps.executeUpdate();
        }
    }
}
