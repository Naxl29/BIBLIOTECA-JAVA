package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import utils.DBConnection;

public class GeneroDAOImpl implements GeneroDAO {
	private Connection con;
	
	public GeneroDAOImpl() {
		this.con = DBConnection.conectar();
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
			stmt.setString(1, genero);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				generoId = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return generoId;
	}
}