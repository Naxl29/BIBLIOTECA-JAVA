import dao.PersonaDAO;
import dao.PersonaDAOImpl;
import modelo.Persona;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PersonaDAO dao = new PersonaDAOImpl();

        Persona PersonaNueva = new Persona(0, "HAROL", "SANTIAGO", "RODRIGUEZ", "CASALLAS", "111111");
        dao.crearPersona(PersonaNueva);

         
    }
}
