package dao;

import modelo.Persona;
import java.util.List;

public interface PersonaDAO {
	void crearPersona(Persona persona);
	List<Persona> verTodasLasPersonas();
	void actualizarPersona(Persona persona);
	void eliminarPersona(int id);
}
