package dao;

import modelo.Libro;
import java.util.List;

public interface LibroDAO {
    void crearLibro(Libro libro);
    List<Libro> verTodosLosLibros();
    void actualizarLibro(Libro libro);
    void eliminarLibro(int id);
}
