package dao;

import modelo.Persona;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAOImpl implements PersonaDAO {
	private Connection con;
	
	public PersonaDAOImpl() {
		this.con = DBConnection.conectar();
	}
	
	@Override
	public void crearPersona(Persona persona) {
		String sql = "INSERT INTO personas (primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, documento) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, persona.getPrimerNombre());
			stmt.setString(2, persona.getSegundoNombre());
			stmt.setString(3, persona.getPrimerApellido());
			stmt.setString(4, persona.getSegundoApellido());
			stmt.setString(5, persona.getDocumento());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Persona> obtenerTodasLasPersonas() {
		List<Persona> personas = new ArrayList<>();
		String sql = "SELECT * FROM personas";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				personas.add(new Persona(
					rs.getInt("id"),
					rs.getString("primer_nombre"),
					rs.getString("segundo_nombre"),
					rs.getString("primer_apellido"),
					rs.getString("segundo_apellido"),
					rs.getString("documento")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return personas;
	}
	
	@Override
	public void actualizarPersona(Persona persona) {
		String sql = "UPDATE personas SET primer_nombre = ?, segundo_nombre = ?, primer_apellido = ?, segundo_apellido = ?, documento = ? WHERE id = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, persona.getPrimerNombre());
			stmt.setString(2, persona.getSegundoNombre());
			stmt.setString(3, persona.getPrimerApellido());
			stmt.setString(4, persona.getSegundoApellido());
			stmt.setString(5, persona.getDocumento());
			stmt.setInt(6, persona.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void eliminarPersona(int id) {
		String sql = "DELETE FROM personas WHERE id = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
