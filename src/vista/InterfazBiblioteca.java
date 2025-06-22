package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.LibroDAO;
import dao.LibroDAOImpl;
import modelo.Libro;

import modelo.Persona;
import dao.PersonaDAO;
import dao.PersonaDAOImpl;

import java.awt.*;
import java.util.List;

@SuppressWarnings("serial")
public class InterfazBiblioteca extends JFrame {
	private LibroDAO dao;
	private PersonaDAO personaDAO;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JTextField txtTitulo, txtAutor, txtEditorial, txtGenero, txtIdEliminar;
    
    public InterfazBiblioteca() {
    	dao = new LibroDAOImpl();
  
    	setTitle("Bibioteca: LA MONDA");
    	setSize(800, 500);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
    
	    //the table en español la tabla
	    modeloTabla = new DefaultTableModel(new String[] {"ID", "Título", "Autor", "Editorial", "Género"}, 0);
	    tabla = new JTable(modeloTabla);
	    cargarLibros();
	    add(new JScrollPane(tabla), BorderLayout.CENTER);
	    
	    
	    //Panel donde se van agregar los libros
	    JPanel panelAgregar = new JPanel(new GridLayout(6,2));
	    txtTitulo = new JTextField();
	    txtAutor = new JTextField();
	    txtEditorial = new JTextField();
	    txtGenero = new JTextField();
	    
	    panelAgregar.add(new JLabel("Título:"));
	    panelAgregar.add(txtTitulo);
	    panelAgregar.add(new JLabel("Autor:"));
	    panelAgregar.add(txtAutor);
	    panelAgregar.add(new JLabel("Editorial:"));
	    panelAgregar.add(txtEditorial);
	    panelAgregar.add(new JLabel("Género:"));
	    panelAgregar.add(txtGenero);
	    
	    JButton btnAgregar = new JButton("Agregar Libro");
	    btnAgregar.addActionListener(e -> agregarLibro());
	    panelAgregar.add(btnAgregar);
	    
	    //El panel para eliminar
	    txtIdEliminar = new JTextField();
	    JButton btnEliminar = new JButton("Eliminar Libro");
	    btnEliminar.addActionListener(e -> EliminarLibro());
	    
	    panelAgregar.add(new JLabel("ID a eliminar:"));
	    panelAgregar.add(txtIdEliminar);
	    panelAgregar.add(btnEliminar);
	
	    add(panelAgregar, BorderLayout.SOUTH);
    }
	    
	    private void cargarLibros() {
	    	modeloTabla.setRowCount(0); // limpia la tabla
	    	List<Libro> libros = dao.verTodosLosLibros();
	    	for (Libro libro : libros) {
	    		modeloTabla.addRow(new Object[] {
	    				libro.getId(),
	    				libro.getTitulo(),
	    				libro.getAutor(),
	    				libro.getEditorial(),
	    				libro.getIdGenero()
	    		});
	    	}
	    }
	    	
	    private void agregarLibro() {
	    	try {
	    		String titulo = txtTitulo.getText();
	            String autor = txtAutor.getText();
	            String editorial = txtEditorial.getText();
	            int genero = Integer.parseInt(txtGenero.getText());
	            
	            Libro libro = new Libro(0, titulo, autor, editorial, genero);
	            dao.crearLibro(libro);
	            cargarLibros();
	            limpiarCampos();
	        } catch (Exception e) {
	        	JOptionPane.showMessageDialog(this, "Error al agregar libro: " + e.getMessage());
	    	}
	    }
	    
	    private void EliminarLibro() {
	    	try {
	    		int id = Integer.parseInt(txtIdEliminar.getText());
	            dao.eliminarLibro(id);
	            cargarLibros();
	            txtIdEliminar.setText("");
	        } catch (Exception e) {
	        	JOptionPane.showMessageDialog(this, "Error al eliminar libro: " + e.getMessage());
	        }
	    }
	    
	    private void limpiarCampos() {
	    	txtTitulo.setText("");
	    	txtAutor.setText("");
	    	txtEditorial.setText("");
	    	txtGenero.setText("");
	    }
	    
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new InterfazBiblioteca().setVisible(true));
	    }
}

