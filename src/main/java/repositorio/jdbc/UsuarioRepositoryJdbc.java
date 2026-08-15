package repositorio.jdbc;

import modelo.Usuario;
import repositorio.UsuarioRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepositoryJdbc implements UsuarioRepository {

    @Override
    public Usuario validarLogin(String correo, String contrasena) throws SQLException {

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
    public java.util.Optional<Usuario> buscarPorId(int idUsuario) throws SQLException {
        String sql = "SELECT Id, Nombre, Correo, Contrasena, IdRol FROM Usuario WHERE Id = ?";

        try (Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return java.util.Optional.of(mapear(rs));
                }
                return java.util.Optional.empty();
            }
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("Id"));
        usuario.setNombre(rs.getString("Nombre"));
        usuario.setCorreo(rs.getString("Correo"));
        usuario.setContrasena(rs.getString("Contrasena"));
        usuario.setIdRol(rs.getInt("IdRol"));
        return usuario;
    }

    @Override
    public void actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE Usuario SET Correo = ?, Contrasena = ? WHERE Id = ?";

        try (Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, usuario.getCorreo());
            ps.setString(2, usuario.getContrasena());
            ps.setInt(3, usuario.getIdUsuario());
            ps.executeUpdate();
        }
    }
}
