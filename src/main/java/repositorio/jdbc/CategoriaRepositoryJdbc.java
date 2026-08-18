package repositorio.jdbc;

import modelo.Categoria;
import repositorio.CategoriaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaRepositoryJdbc implements CategoriaRepository {

    @Override
    public Optional<Categoria> buscarPorId(int idCategoria) {

        String sql = "SELECT Id, NombreCategoria "
                + "FROM Categoria "
                + "WHERE Id = ?";

        try (
                Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }

                return Optional.empty();
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Error buscando categoria con id "
                    + idCategoria,
                    e
            );
        }
    }

    @Override
    public List<Categoria> listarTodas() throws SQLException {
        String sql = "SELECT Id, NombreCategoria FROM Categoria";
        List<Categoria> categorias = new ArrayList<>();
        try (Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categorias.add(mapear(rs));
            }
            return categorias;
        }
    }

    private Categoria mapear(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(rs.getInt("Id"));
        categoria.setNombreCategoria(rs.getString("NombreCategoria"));
        return categoria;
    }
}
