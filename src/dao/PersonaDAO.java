package dao;

import modelo.Persona;
import java.util.List;

public interface PersonaDAO {
	void crearPersona(Persona persona);
	List<Persona> obtenerTodasLasPersonas();
	void actualizarPersona(Persona persona);
	void eliminarPersona(int id);
}
