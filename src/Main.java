import dao.LibroDAO;
import dao.LibroDAOImpl;
import modelo.Libro;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LibroDAO dao = new LibroDAOImpl();

        // Crear libro
        Libro libroNuevo = new Libro(0, "Cien Años de Soledad", "Gabriel García Márquez", "Sudamericana");
        dao.crearLibro(libroNuevo);

        // Leer todos los libros
        System.out.println("Libros registrados:");
        List<Libro> libros = dao.verTodosLosLibros();
        for (Libro libro : libros) {
            System.out.println(libro);
        }

        // Actualizar libro (ejemplo con ID 1)
        Libro libroActualizar = new Libro(1, "Cien Años de Soledad (Editado)", "Gabo", "Sudamericana");
        dao.actualizarLibro(libroActualizar);

        // Eliminar libro por ID
        dao.eliminarLibro(1); // Elimina libro con id 1 (ajusta según tu tabla)
    }
}
