package repositorio.jdbc;

import modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {

    public Usuario validarLogin(String correo, String contrasena)
            throws SQLException {

        String sql = "SELECT Id, Nombre, Correo, Contrasena, IdRol "
                   + "FROM Usuario "
                   + "WHERE Correo = ? AND Contrasena = ?";

        try (Connection cn = ConexionDB.obtener();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, contrasena);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Usuario usuario = new Usuario();

                    usuario.setIdUsuario(rs.getInt("Id"));
                    usuario.setNombre(rs.getString("Nombre"));
                    usuario.setCorreo(rs.getString("Correo"));
                    usuario.setContrasena(rs.getString("Contrasena"));
                    usuario.setIdRol(rs.getInt("IdRol"));

                    return usuario;
                }
            }
        }

        return null;
    }
}