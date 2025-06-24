package modelo;

public class Persona {
	private int id;
	private String primer_nombre;
	private String segundo_nombre;
	private String primer_apellido;
	private String segundo_apellido;
	private String documento;
	
	public Persona(int id, String primer_nombre, String segundo_nombre,String primer_apellido, String segundo_apellido, String documento ) {
		this.id = id;
		this.primer_nombre = primer_nombre;
		this.segundo_nombre = segundo_nombre;
		this.primer_apellido = primer_apellido;
		this.segundo_apellido = segundo_apellido;
		this.documento = documento;
	}
		
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPrimerNombre() {
		return primer_nombre;
	}
	
	public void setPrimerNombre(String primer_nombre) {
		this.primer_nombre = primer_nombre;
	}
	
	public String getSegundoNombre() {
		return segundo_nombre;
	}
	
	public void setSegundoNombre(String segundo_nombre) {
		this.segundo_nombre = segundo_nombre;
	}
	
	public String getPrimerApellido() {
		return primer_apellido;
	}
	
	public void setPrimerApellido(String primer_apellido) {
		this.primer_apellido = primer_apellido;
	}
	
	public String getSegundoApellido() {
		return segundo_apellido;
	}
	
	public void setSegundoApellido(String segundo_apellido) {
		this.segundo_apellido = segundo_apellido;
	}
	
	public String getDocumento() {
		return documento;
	}
	
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	@Override
	public String toString() {
		return primer_nombre + " " + primer_apellido;
	}
	
}
