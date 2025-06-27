package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.util.function.*;

import dao.*;
import modelo.*;

import java.awt.*;
import java.util.List;

@SuppressWarnings("serial")
public class InterfazBiblioteca extends JFrame {
	private LibroDAO libroDAO;
	private PersonaDAO personaDAO;
	private PrestamoDAO prestamoDAO;
	private EstadoDAO estadoDAO;
	private GeneroDAO generoDAO;
	
    private JTable tablaLibros;
    private DefaultTableModel modeloTablaLibros;
    
    private JTable tablaPersonas;
    private DefaultTableModel modeloTablaPersonas;
    
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTablaPrestamos;
    
    public InterfazBiblioteca() {
    	libroDAO= new LibroDAOImpl();
    	personaDAO = new PersonaDAOImpl();
    	prestamoDAO = new PrestamoDAOImpl();
    	estadoDAO = new EstadoDAOImpl();
    	generoDAO = new GeneroDAOImpl();
    	
    	setTitle("Biblioteca N & J");
    	setSize(900, 600);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        // -- PANEL LIBROS --
        JPanel panelTablaLibros = new JPanel(new BorderLayout());
        modeloTablaLibros = new DefaultTableModel(new String[] {"ID", "Título", "Autor", "Editorial", "Género", "Imagen", "stock"}, 0) {
	        @Override
	        public Class<?> getColumnClass(int columnIndex) {
	            return (columnIndex == 5) ? ImageIcon.class : Object.class;
	        }
	
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
        tablaLibros = new JTable(modeloTablaLibros);
        tablaLibros.setRowHeight(80); 
        tablaLibros.getColumnModel().getColumn(5).setPreferredWidth(80);

        // Panel para mostrar los detalles de los libros al hacer clic
        tablaLibros.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int filaSeleccionada = tablaLibros.getSelectedRow();
                if (filaSeleccionada != -1) {
                    int idLibro = (int) modeloTablaLibros.getValueAt(filaSeleccionada, 0);
                    Libro libro = libroDAO.verLibroPorId(idLibro);
                    String nombreGenero = generoDAO.obtenerNombreGenero(libro.getIdGenero());
                    if (libro != null) {
                    	JPanel panel = new JPanel(new BorderLayout());
                        JTextArea info = new JTextArea( 
                                "ID: " + libro.getId() + "\n" +
                                "Título: " + libro.getTitulo() + "\n" +
                                "Autor: " + libro.getAutor() + "\n" +
                                "Editorial: " + libro.getEditorial() + "\n" +
                                "Stock:" + libro.getStock() + "\n" +
                                "Género: " + nombreGenero
                        );
        				info.setEditable(false); 
        				panel.add(info, BorderLayout.CENTER);
        				
        				if (libro.getImagen() != null && !libro.getImagen().isEmpty()) {
        					try {
        						ImageIcon imagen = new ImageIcon(libro.getImagen());
        						Image img = imagen.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
								JLabel labelImagen = new JLabel(new ImageIcon(img));
								panel.add(labelImagen, BorderLayout.EAST);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(panelTablaLibros, "Error al cargar la imagen: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        					}
        				}
                        JOptionPane.showMessageDialog(null, panel, "Detalles del Libro", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        cargarLibros();
        panelTablaLibros.add(new JScrollPane(tablaLibros), BorderLayout.CENTER);

        // Panel para agregar un libro
        JButton btnAgregarLibro = new JButton("Agregar Libro");
        btnAgregarLibro.addActionListener(e -> {
            PanelAgregarLibro dialogo = new PanelAgregarLibro(this, libroDAO);
            dialogo.setVisible(true);  
            cargarLibros(); 
        });

        // Panel para actualizar libro
        JButton btnActualizarLibro = new JButton("Actualizar Libro");
        btnActualizarLibro.addActionListener(e -> actualizarPorId("Libro", libroDAO::verLibroPorId, libro -> {
        	PanelActualizarLibro dialogo = new PanelActualizarLibro(this, libroDAO);
        	dialogo.cargarDatosLibro(libro);
        	dialogo.setVisible(true);
        	cargarLibros();
        }));
        
        
        // Panel para eliminar libro
        JButton btnEliminarLibro = new JButton("Eliminar Libro");
        btnEliminarLibro.addActionListener(e -> eliminarPorId("Libro", libroDAO::verLibroPorId, id ->{
        	libroDAO.eliminarLibro(id);
        	cargarLibros();
        }));
        
        JButton btnOrdenarLibros = new JButton("Ordenar por Título");
        btnOrdenarLibros.addActionListener(e -> {
        	cargarLibrosOrdenados();
        });
	    
        JButton btnAgregarGenero = new JButton("Agregar Género");
	    btnAgregarGenero.addActionListener(e -> {
	    	PanelAgregarGenero dialogo = new PanelAgregarGenero(this, generoDAO);
	    	dialogo.setVisible(true);
	    });
	    
	    JPanel panelAgregarLibro= new JPanel();
	    panelAgregarLibro.add(btnAgregarLibro);
	    panelAgregarLibro.add(btnActualizarLibro);
	    panelAgregarLibro.add(btnEliminarLibro);
	    panelAgregarLibro.add(btnOrdenarLibros);
	    panelAgregarLibro.add(btnAgregarGenero);
	    panelTablaLibros.add(panelAgregarLibro, BorderLayout.SOUTH);
	    
	    tabbedPane.addTab("Libros", panelTablaLibros);
	    add(tabbedPane);
	    
	    
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
	    
	    // Panel para actualizar persona
	    JButton btnActualizarPersona = new JButton("Actualizar Persona");
        btnActualizarPersona.addActionListener(e -> actualizarPorId("Persona", personaDAO::verPersonaPorId, persona -> {
        	PanelActualizarPersona dialogo = new PanelActualizarPersona(this, personaDAO);
        	dialogo.cargarDatosPersona(persona);
        	dialogo.setVisible(true);
        	cargarPersonas();
        }));
	    
	    //Panel para eliminar persona
	    JButton btnEliminarPersona = new JButton("Eliminar Persona");
        btnEliminarPersona.addActionListener(e -> eliminarPorId("Persona", personaDAO::verPersonaPorId, id ->{
        	personaDAO.eliminarPersona(id);
        	cargarPersonas();
	    }));
	    
	    JPanel panelAgregarPersona = new JPanel();
	    panelAgregarPersona.add(btnAgregarPersona);
	    panelAgregarPersona.add(btnActualizarPersona);
	    panelAgregarPersona.add(btnEliminarPersona);
	    panelTablaPersonas.add(panelAgregarPersona, BorderLayout.SOUTH);
	    
	    tabbedPane.addTab("Personas", panelTablaPersonas);
	    add(tabbedPane);
	    
	    // -- PANEL PRESTAMOS --
	    JPanel panelTablaPrestamos = new JPanel(new BorderLayout());
	    modeloTablaPrestamos = new DefaultTableModel(new String[] {"ID", "Persona", "Libro", "Estado"}, 0);
	    tablaPrestamos = new JTable(modeloTablaPrestamos);
	    
	    // Panel para mostrar los detalles del préstamo al hacer clic
	    tablaPrestamos.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(java.awt.event.MouseEvent evt) {
	    		int filaSeleccionada = tablaPrestamos.getSelectedRow();
	    		if (filaSeleccionada != -1) {
	    			int idPrestamo = (int) modeloTablaPrestamos.getValueAt(filaSeleccionada, 0);
	    			Prestamo prestamo = prestamoDAO.verPrestamoPorId(idPrestamo);
	    			if (prestamo != null) {
	    				 Persona persona = personaDAO.verPersonaPorId(prestamo.getIdPersona());
	    	                Libro libro = libroDAO.verLibroPorId(prestamo.getIdLibro());
	    	                Estado estado = estadoDAO.verEstadoPorId(prestamo.getIdEstado());

	    	                String nombrePersona = (persona != null)
	    	                    ? persona.getPrimerNombre() + " " + persona.getPrimerApellido()
	    	                    : "Desconocida";

	    	                String tituloLibro = (libro != null)
	    	                    ? libro.getTitulo()
	    	                    : "Desconocido";
	    	                
	    	                String estadoPrestamo = (estado != null)
	    	                	? estado.getEstado()
	    	                	: "Desconocido";

	    	                JOptionPane.showMessageDialog(null, 
	    	                    "ID: " + prestamo.getId() + "\n" +
	    	                    "Persona: " + nombrePersona + "\n" +
	    	                    "Libro: " + tituloLibro + "\n" +
	    	                    "Estado: " + estadoPrestamo + "\n",
	    	                    "Detalles del Préstamo", JOptionPane.INFORMATION_MESSAGE);
	    			} else {
	    				JOptionPane.showMessageDialog(panelTablaPersonas, "Prétamo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
	    			}
	    		}
	    	}
	    });
	    
	    cargarPrestamos();
	    panelTablaPrestamos.add(new JScrollPane(tablaPrestamos), BorderLayout.CENTER);
	    
	    JButton btnAgregarPrestamo = new JButton("Agregar Préstamo");
	    btnAgregarPrestamo.addActionListener(e -> {
	    	PanelAgregarPrestamo dialogo = new PanelAgregarPrestamo(this, prestamoDAO);
	    	dialogo.setVisible(true);
	    	cargarPrestamos();
	    });
	    
	    // Panel para actualizar préstamo
	    JButton btnActualizarPrestamo = new JButton("Actualizar Préstamo");
        btnActualizarPrestamo.addActionListener(e -> actualizarPorId("Prestamo", prestamoDAO::verPrestamoPorId, prestamo-> {
        	PanelActualizarPrestamo dialogo = new PanelActualizarPrestamo(this, prestamoDAO);
        	dialogo.cargarDatosPrestamo(prestamo);
        	dialogo.setVisible(true);
        	cargarPrestamos();
        }));
	    
	    //Panel para eliminar préstamo
	    JButton btnEliminarPrestamo = new JButton("Eliminar Préstamo");
        btnEliminarPrestamo.addActionListener(e -> eliminarPorId("Préstamo", prestamoDAO::verPrestamoPorId, id ->{
        	prestamoDAO.eliminarPrestamo(id);
        	cargarPrestamos();
	    }));

	    
	    JPanel panelBotonesPrestamo = new JPanel();
	    panelBotonesPrestamo.add(btnAgregarPrestamo);
	    panelBotonesPrestamo.add(btnActualizarPrestamo);
	    panelBotonesPrestamo.add(btnEliminarPrestamo);
	    panelTablaPrestamos.add(panelBotonesPrestamo, BorderLayout.SOUTH);
	    
	    tabbedPane.addTab("Préstamos", panelTablaPrestamos);
	    add(tabbedPane);
	    
	    
	   
	    
    }
	    
	    private void cargarPrestamos() {
	    	modeloTablaPrestamos.setRowCount(0); 
	    	List<Prestamo> prestamos = prestamoDAO.verTodosLosPrestamos();
	    	for (Prestamo prestamo : prestamos) {
	            Persona persona = personaDAO.verPersonaPorId(prestamo.getIdPersona());
	            Libro libro = libroDAO.verLibroPorId(prestamo.getIdLibro());
	            Estado estado = estadoDAO.verEstadoPorId(prestamo.getIdEstado());
	            
	            String nombrePersona = (persona != null) 
	                ? persona.getPrimerNombre() + " " + persona.getPrimerApellido() 
	                : "Desconocido";

	            String tituloLibro = (libro != null) 
	                ? libro.getTitulo() 
	                : "Desconocido";
	            
	            String estadoPrestamo = (estado != null)
	                	? estado.getEstado()
	                	: "Desconocido";

	            modeloTablaPrestamos.addRow(new Object[] {
	                prestamo.getId(),
	                nombrePersona,
	                tituloLibro,
	                estadoPrestamo
	    		});
	    	}	    	
	    }
	    
	    private void cargarLibros() {
	    	modeloTablaLibros.setRowCount(0);
	        List<Libro> libros = libroDAO.verTodosLosLibros();
	        for (Libro libro : libros) {
	            ImageIcon icono = null;
	            String nombreGenero = generoDAO.obtenerNombreGenero(libro.getIdGenero());
	            
	            if (libro.getImagen() != null && !libro.getImagen().isEmpty()) {
	                try {
	                    ImageIcon original = new ImageIcon(libro.getImagen());
	                    Image imagenEscalada = original.getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH);
	                    icono = new ImageIcon(imagenEscalada);
	                } catch (Exception e) {
	                    icono = new ImageIcon(); 
	                }
	            }
	            
	            modeloTablaLibros.addRow(new Object[] {
	                libro.getId(),
	                libro.getTitulo(),
	                libro.getAutor(),
	                libro.getEditorial(),
	                nombreGenero != null ? nombreGenero : "Desconocido",
	                icono,
	                libro.getStock()
	    		});
	    	}
	    }
	    
	    private void cargarLibrosOrdenados() {
	    	modeloTablaLibros.setRowCount(0);
	    	List<Libro> libros = libroDAO.ordenarPorTitulo();
	    	
	    	for (Libro libro : libros) {
	    		ImageIcon icono = null;
	    		String nombreGenero = generoDAO.obtenerNombreGenero(libro.getIdGenero());
	    		
	    		if (libro.getImagen() != null && !libro.getImagen().isEmpty()) {
	    			try {
	    				ImageIcon original = new ImageIcon(libro.getImagen());
	    				Image imagenEscalada = original.getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH);
	    				icono = new ImageIcon(imagenEscalada);
	    			} catch (Exception e) {
	    				icono = new ImageIcon(); 
	    			}
	    		}
	    		
	    		modeloTablaLibros.addRow(new Object[] {
	    				libro.getId(),
	    				libro.getTitulo(),
	    				libro.getAutor(),
	    				libro.getEditorial(),
	    				nombreGenero != null ? nombreGenero : "Desconocido",
	    				icono
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
	    

	    	
	    private void eliminarPorId(String tipo, Function<Integer, Object> buscador, Consumer<Integer> eliminador) {
	    	String input = JOptionPane.showInputDialog(this, "Ingrese el ID del " + tipo + " que desea eliminar:");
	    	if (input != null && !input.trim().isEmpty()) {
	    		try {
	    			int id = Integer.parseInt(input.trim());
	    			Object obj = buscador.apply(id);
	    			if (obj != null) {
	    				eliminador.accept(id);	
	    				JOptionPane.showMessageDialog(this, tipo + " eliminado correctamente");
    				} else {
    					JOptionPane.showMessageDialog(this, tipo + " no encontrado con ID: " + id, "Error", JOptionPane.ERROR_MESSAGE);
    				}
    			} catch (NumberFormatException e) {
    				JOptionPane.showMessageDialog(this, "ID inválido. Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
    			}
	    	}
	    }
	    
	    private <T> void actualizarPorId(String tipo, Function<Integer, T> buscador, Consumer<T> actualizador) {
	    	String input = JOptionPane.showInputDialog(this, "Ingrese el ID del " + tipo + " que desea actualizar:");
	    	if (input != null && !input.trim().isEmpty()) {
	    		try {
	    			int id = Integer.parseInt(input.trim());
	    			T obj = buscador.apply(id);
	    			if (obj != null) {
	    				actualizador.accept(obj);
	    			} else {
	    				JOptionPane.showMessageDialog(this, tipo + " no encontrado con ID: " + id, "Error", JOptionPane.ERROR_MESSAGE);
	    			}
	    		} catch (NumberFormatException e) {
	    			JOptionPane.showMessageDialog(this, "ID inválido. Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
	    		}
	    	}
	    }
	    
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new InterfazBiblioteca().setVisible(true));
	    }
}

