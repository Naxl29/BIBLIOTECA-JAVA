package dao;

import modelo.Prestamo;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAOImpl implements PrestamoDAO{
	private Connection con;

	public PrestamoDAOImpl() {
		this.con = DBConnection.conectar();
	}
	
	@Override
	public void crearPrestamo(Prestamo prestamo) {
		String sql = "INSERT INTO prestamos (id_persona, id_libro, id_estado) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, prestamo.getIdPersona());
			stmt.setInt(2, prestamo.getIdLibro());
			stmt.setInt(3, prestamo.getIdEstado());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Prestamo> verTodosLosPrestamos() {
		List<Prestamo> prestamos = new ArrayList<>();
		String sql = "SELECT * FROM prestamos";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				prestamos.add(new Prestamo(
					rs.getInt("id"),
					rs.getInt("id_persona"),
					rs.getInt("id_libro"),
					rs.getInt("id_estado")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return prestamos;
	}
	
	@Override
	public Prestamo verPrestamoPorId(int id) {
		String sql = "SELECT * FROM prestamos WHERE id = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Prestamo(
						rs.getInt("id"),
						rs.getInt("id_persona"),
						rs.getInt("id_libro"),
						rs.getInt("id_estado")
					);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public void actualizarPrestamo(Prestamo prestamo) {
		String sql = "UPDATE prestamos SET id_estado = ? WHERE id = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, prestamo.getIdEstado());
			stmt.setInt(2, prestamo.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void eliminarPrestamo(int id) {
		String sql = "DELETE FROM prestamos WHERE id = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
