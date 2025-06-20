package dao;

import modelo.Libro;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {
    private Connection con;

    public LibroDAOImpl() {
        this.con = DBConnection.conectar();
    }

    @Override
    public void crearLibro(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, editorial, id_genero) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getEditorial());
            stmt.setInt(4, libro.getIdGenero());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Libro> verTodosLosLibros() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                libros.add(new Libro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("editorial"),
                    rs.getInt("id_genero")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libros;
    }

    @Override
    public void actualizarLibro(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, editorial = ?, id_genero = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getEditorial());
            stmt.setInt(4, libro.getIdGenero());
            stmt.setInt(5, libro.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarLibro(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
