import dao.LibroDAO;
import dao.LibroDAOImpl;
import modelo.Libro;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LibroDAO dao = new LibroDAOImpl();

        // Crear libro
        Libro libroNuevo = new Libro(0, "NUM", "NUM", "NUM", 1);
        dao.crearLibro(libroNuevo);

        // Leer todos los libros
        System.out.println("Libros registrados:");
        List<Libro> libros = dao.verTodosLosLibros();
        for (Libro libro : libros) {
            System.out.println(libro);
        }

        // Actualizar libro
        Libro libroActualizar = new Libro(1, "NA", "NA", "NA", 1);
        dao.actualizarLibro(libroActualizar);

        // Eliminar libro por ID
        dao.eliminarLibro(1); 
    }
}
