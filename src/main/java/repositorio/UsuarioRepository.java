package repositorio;

import modelo.Usuario;

import java.sql.SQLException;

public interface UsuarioRepository {

    Usuario validarLogin(String correo, String contrasena) throws SQLException;

    java.util.Optional<Usuario> buscarPorId(int idUsuario) throws SQLException;
}
