package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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
	private LibroDAO libroDAO;
	private PersonaDAO personaDAO;
	
    private JTable tablaLibros;
    private DefaultTableModel modeloTablaLibros;
    
    private JTable tablaPersonas;
    private DefaultTableModel modeloTablaPersonas;

    private JTextField txtTitulo, txtAutor, txtEditorial, txtGenero, txtIdEliminar;
    
    public InterfazBiblioteca() {
    	libroDAO= new LibroDAOImpl();
    	personaDAO = new PersonaDAOImpl();
  
    	setTitle("Bibioteca: LA MONDA");
    	setSize(900, 600);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        // -- PANEL LIBROS --
        JPanel panelTablaLibros = new JPanel(new BorderLayout());
    
	    modeloTablaLibros = new DefaultTableModel(new String[] {"ID", "Título", "Autor", "Editorial", "Género"}, 0);
	    tablaLibros = new JTable(modeloTablaLibros);
	    cargarLibros();
	    panelTablaLibros.add(new JScrollPane(tablaLibros), BorderLayout.CENTER);
	    
	    
	    //Panel donde se van agregar los libros
	    JPanel panelAgregarLibros = new JPanel(new GridLayout(7,2));
	    txtTitulo = new JTextField();
	    txtAutor = new JTextField();
	    txtEditorial = new JTextField();
	    txtGenero = new JTextField();
	    
	    panelAgregarLibros.add(new JLabel("Título:"));
	    panelAgregarLibros.add(txtTitulo);
	    panelAgregarLibros.add(new JLabel("Autor:"));
	    panelAgregarLibros.add(txtAutor);
	    panelAgregarLibros.add(new JLabel("Editorial:"));
	    panelAgregarLibros.add(txtEditorial);
	    panelAgregarLibros.add(new JLabel("Género:"));
	    panelAgregarLibros.add(txtGenero);
	    
	    JButton btnAgregarLibro = new JButton("Agregar Libro");
	    btnAgregarLibro.addActionListener(e -> agregarLibro());
	    panelAgregarLibros.add(btnAgregarLibro);
	    
	    //El panel para eliminar
	    txtIdEliminar = new JTextField();
	    JButton btnEliminar = new JButton("Eliminar Libro");
	    btnEliminar.addActionListener(e -> eliminarLibro());
	    
	    panelAgregarLibros.add(new JLabel("ID a eliminar:"));
	    panelAgregarLibros.add(txtIdEliminar);
	    panelAgregarLibros.add(btnEliminar);
	    
	    panelTablaLibros.add(panelAgregarLibros, BorderLayout.SOUTH);
	    tabbedPane.addTab("Libros", panelTablaLibros);
	    
	    // -- PANEL PERSONAS --
	    JPanel panelTablaPersonas = new JPanel(new BorderLayout());
	    modeloTablaPersonas = new DefaultTableModel(new String[] {"ID", "Primer Nombre", "Segundo Nombre", "Primer Apellido", "Segundo Apellido", "Documento"}, 0);
	    tablaPersonas = new JTable(modeloTablaPersonas);
	    
	    // Panel para mostrar los detalles de la persona al hacer clic
	    tablaPersonas.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(java.awt.event.MouseEvent evt) {
	    		int filaSeleccionada = tablaPersonas.getSelectedRow();
	    		if (filaSeleccionada != -1) {
	    			int idPersona = (int) modeloTablaPersonas.getValueAt(filaSeleccionada, 0);
	    			Persona persona = personaDAO.verPersonaPorId(idPersona);
	    			if (persona != null) {
	    				JOptionPane.showMessageDialog(null, 
	    						"ID: " + persona.getId() + "\n" +
	    						"Nombre: " + persona.getPrimerNombre() + " " + persona.getSegundoNombre() + "\n" +
	    						"Apellido: " + persona.getPrimerApellido() + " " + persona.getSegundoApellido() + "\n" +
	    						"Documento: " + persona.getDocumento(),
	    						"Detalles de la Persona", JOptionPane.INFORMATION_MESSAGE);
	    			} else {
	    				JOptionPane.showMessageDialog(panelTablaPersonas, "Persona no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
	    			}
	    		}
	    	}
	    });
	    
	    cargarPersonas();
	    panelTablaPersonas.add(new JScrollPane(tablaPersonas), BorderLayout.CENTER);
	    
	    // Panel para agregar personas
	    JButton btnAgregarPersona = new JButton("Agregar Persona");
	    btnAgregarPersona.addActionListener(e -> {
	    	PanelAgregarPersona dialogo = new PanelAgregarPersona(this, personaDAO);
	    	dialogo.setVisible(true);
	    	cargarPersonas();
	    });
	    
	    JPanel panelAgregarPersona = new JPanel();
	    panelAgregarPersona.add(btnAgregarPersona);
	    panelTablaPersonas.add(panelAgregarPersona, BorderLayout.SOUTH);
	    
	    tabbedPane.addTab("Personas", panelTablaPersonas);
	    add(tabbedPane);
    }
	    
	    private void cargarLibros() {
	    	modeloTablaLibros.setRowCount(0); 	
	    	List<Libro> libros = libroDAO.verTodosLosLibros();
	    	for (Libro libro : libros) {
	    		modeloTablaLibros.addRow(new Object[] {
	    				libro.getId(),
	    				libro.getTitulo(),
	    				libro.getAutor(),
	    				libro.getEditorial(),
	    				libro.getIdGenero()
	    		});
	    	}
	    }
	    
	    private void cargarPersonas() {
	    	modeloTablaPersonas.setRowCount(0); 
	    	List<Persona> personas = personaDAO.verTodasLasPersonas();
	    	for (Persona persona : personas) {
	    		modeloTablaPersonas.addRow(new Object[] {
	    				persona.getId(),
	    				persona.getPrimerNombre(),
	    				persona.getSegundoNombre(),
	    				persona.getPrimerApellido(),
	    				persona.getSegundoApellido(),
	    				persona.getDocumento()
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
	            libroDAO.crearLibro(libro);
	            cargarLibros();
	            limpiarCampos();
	        } catch (Exception e) {
	        	JOptionPane.showMessageDialog(this, "Error al agregar libro: " + e.getMessage());
	    	}
	    }
	    
	    private void eliminarLibro() {
	    	try {
	    		int id = Integer.parseInt(txtIdEliminar.getText());
	    		libroDAO.eliminarLibro(id);
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

