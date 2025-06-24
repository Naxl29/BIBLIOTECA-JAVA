package dao;

import java.util.List;

public interface GeneroDAO {
	List<String> obtenerGeneros();  
	int obtenerIdGenero(String genero);
	String obtenerNombreGenero(int id); 
}