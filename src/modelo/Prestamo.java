package modelo;

public class Prestamo {
	private int id;
	private int id_persona;
	private int id_libro;
	private int id_estado;
	
	public Prestamo(int id, int id_persona, int id_libro, int id_estado) {
		this.id = id;
		this.id_persona = id_persona;
		this.id_libro = id_libro;
		this.id_estado = id_estado;
	}	
	
	public Prestamo(int id, int id_estado) {
		this.id = id;
		this.id_estado = id_estado;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdPersona() {
		return id_persona;
	}
	
	public void setIdPersona(int id_persona) {
		this.id_persona = id_persona;
	}	
	
	public int getIdLibro() {
		return id_libro;
	}
	
	public void setIdLibro(int id_libro) {
		this.id_libro = id_libro;
	}
	
	public int getIdEstado() {
		return id_estado;
	}
	
	public void setIdEstado(int id_estado) {
		this.id_estado = id_estado;
	}
	
	@Override
	public String toString() {
		return "Prestamo [id=" + id + ", id_persona=" + id_persona + ", id_libro=" + id_libro + "]";
	}
}
