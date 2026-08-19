package repositorio;

import modelo.Usuario;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    void actualizar(Usuario usuario) throws SQLException;

    Usuario validarLogin(String correo, String contrasena) throws SQLException;

    Optional<Usuario> buscarPorId(int idUsuario);

    List<Usuario> listarAgentes() throws SQLException;

    List<Usuario> listarAdministradores() throws SQLException;

    List<Usuario> listar();
}
