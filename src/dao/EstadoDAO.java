package dao;

import modelo.Estado;
import java.util.List;

public interface EstadoDAO {
	List<Estado> verEstados();
	Estado verEstadoPorId(int id);
}
