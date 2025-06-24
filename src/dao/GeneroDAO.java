package dao;

import java.util.List;

public interface GeneroDAO {
	// Método para obtener todos los géneros
	List<String> obtenerGeneros();
	// Método para obtener al ID de un género pos su nombre
	int obtenerIdGenero(String genero);
}