package dao;

import java.util.List;

public interface GeneroDAO {
	List<String> obtenerGeneros();  // Obtener todos los géneros
	int obtenerIdGenero(String genero);  // Obtener el ID de un género por su nombre
}