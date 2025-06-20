package dao;

import modelo.Prestamo;
import java.util.List;

public interface PrestamoDAO {
	void crearPrestamo(Prestamo prestamo);
	List<Prestamo> verTodosLosPrestamos();
	void actualizarPrestamo(Prestamo prestamo);
	void eliminarPrestamo(int id);
}
