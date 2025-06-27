package dao;

import modelo.Genero;
import java.util.List;

public interface GeneroDAO {
	void crearGenero(Genero genero);
	List<String> obtenerGeneros();  
	int obtenerIdGenero(String genero);
	String obtenerNombreGenero(int id); 
}