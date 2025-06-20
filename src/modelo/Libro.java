package modelo;

public class Libro {
	private int id;
	private String titulo;
	private String autor;
	private String editorial;
	private int id_genero;
	
	public Libro(int id, String titulo, String autor, String editorial, int id_genero) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.id_genero = id_genero;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public String getEditorial() {
		return editorial;
	}
	
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	
	public int getIdGenero() {
		return id_genero;
	}
	
	public void setIdGenero(int id_genero) {
		this.id_genero = id_genero;
	}
	
	@Override
	public String toString() {
		return "Libro [id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", editorial=" + editorial + ", genero=" + id_genero + "]";
	}
	
}
