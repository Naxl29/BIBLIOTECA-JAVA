package dao;

import modelo.Estado;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoDAOImpl implements EstadoDAO{
	private Connection con;

	public EstadoDAOImpl() {
		this.con = DBConnection.conectar();
	}
	
	public List<Estado> verEstados() {
		List<Estado> estados = new ArrayList<>();
		String sql = "SELECT * FROM estados";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				estados.add(new Estado(
					rs.getInt("id"),
					rs.getString("estado")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return estados;
	}
	
}
