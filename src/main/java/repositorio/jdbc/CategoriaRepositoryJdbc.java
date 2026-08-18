package repositorio.jdbc;

import modelo.Categoria;
import repositorio.CategoriaRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    @Override
    public void crear(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO Categoria (NombreCategoria) VALUES (?)";
        try (Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, categoria.getNombreCategoria());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    categoria.setIdCategoria(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void actualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE Categoria SET NombreCategoria = ? WHERE Id = ?";
        try (Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombreCategoria());
            ps.setInt(2, categoria.getIdCategoria());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(int idCategoria) throws SQLException {
        String sql = "DELETE FROM Categoria WHERE Id = ?";
        try (Connection cn = ConexionDB.obtener(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);
            ps.executeUpdate();
        }
    }

    private Categoria mapear(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(rs.getInt("Id"));
        categoria.setNombreCategoria(rs.getString("NombreCategoria"));
        return categoria;
    }
}
