package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DBConnection;

import modelo.Genero;

public class GeneroDAOImpl implements GeneroDAO {
	private Connection con;
	
	public GeneroDAOImpl() {
		this.con = DBConnection.conectar();
	}
	
	@Override
	public void crearGenero(Genero genero) {
		String sql = "INSERT INTO generos (genero) VALUES (?)";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, genero.getGenero());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<String> obtenerGeneros() {
	List<String> generos = new ArrayList<>();
	String sql = "SELECT genero FROM generos";
	try (PreparedStatement stmt = con.prepareStatement(sql)) {
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			generos.add(rs.getString("genero"));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return generos;
	
}
	@Override
    public int obtenerIdGenero(String genero) {
        int generoId = -1;
        String sql = "SELECT id FROM generos WHERE genero = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, genero);  // Aquí se pasa el nombre del género
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                generoId = rs.getInt("id");  // Se obtiene el ID del género
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generoId;  // Retorna el ID del género
    }
	
	@Override
	public String obtenerNombreGenero(int idGenero) {
		String nombre = null;
		String sql = "SELECT genero FROM generos WHERE id = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, idGenero);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				nombre = rs.getString("genero");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nombre;
	}
	
}