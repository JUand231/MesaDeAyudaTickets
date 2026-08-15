package repositorio;

import modelo.Usuario;

import java.sql.SQLException;

public interface UsuarioRepository {
    
    void actualizar(Usuario usuario) throws SQLException;

    Usuario validarLogin(String correo, String contrasena) throws SQLException;

    java.util.Optional<Usuario> buscarPorId(int idUsuario) throws SQLException;
}
