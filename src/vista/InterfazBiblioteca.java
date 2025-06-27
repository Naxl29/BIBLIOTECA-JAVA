package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.util.function.*;
import javax.swing.table.JTableHeader;

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
    	setSize(1000, 700);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(52, 144, 220));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        
        JLabel titleLabel = new JLabel("Sistema de Biblioteca N & J");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);      
        
        JTabbedPane tabbedPane = new JTabbedPane();
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // -- PANEL LIBROS --
        JPanel panelTablaLibros = new JPanel(new BorderLayout());
        panelTablaLibros.setBackground(Color.WHITE);
        panelTablaLibros.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel tituloLibros = new JLabel("Gestión de Libros");
        tituloLibros.setFont(new Font("Arial", Font.BOLD, 16));
        tituloLibros.setForeground(new Color(70, 70, 70));
        tituloLibros.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panelTablaLibros.add(tituloLibros, BorderLayout.NORTH);
        
        modeloTablaLibros = new DefaultTableModel(new String[] {"ID", "Título", "Autor", "Editorial", "Género", "Imagen"}, 0) {
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
        
        tablaLibros.setGridColor(new Color(230, 230, 230));
        tablaLibros.setSelectionBackground(new Color(184, 207, 229));
        tablaLibros.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JTableHeader headerTabla = tablaLibros.getTableHeader();
        headerTabla.setBackground(new Color(240, 240, 240));
        headerTabla.setFont(new Font("Arial", Font.BOLD, 12));
        headerTabla.setForeground(new Color(70, 70, 70));
        
        tablaLibros.getColumnModel().getColumn(0).setPreferredWidth(50);  
        tablaLibros.getColumnModel().getColumn(1).setPreferredWidth(180); 
        tablaLibros.getColumnModel().getColumn(2).setPreferredWidth(130); 
        tablaLibros.getColumnModel().getColumn(3).setPreferredWidth(130); 
        tablaLibros.getColumnModel().getColumn(4).setPreferredWidth(100); 
        tablaLibros.getColumnModel().getColumn(5).setPreferredWidth(60);  
        tablaLibros.getColumnModel().getColumn(6).setPreferredWidth(80);  

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
        
        JScrollPane scrollLibros = new JScrollPane(tablaLibros);
        scrollLibros.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        panelTablaLibros.add(scrollLibros, BorderLayout.CENTER);

        // Panel para agregar un libro
        JButton btnAgregarLibro = new JButton("Agregar Libro");
        btnAgregarLibro.setBackground(new Color(92, 184, 92));
        btnAgregarLibro.setForeground(Color.WHITE);
        btnAgregarLibro.setFocusPainted(false);
        btnAgregarLibro.setBorderPainted(false);
        btnAgregarLibro.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregarLibro.setPreferredSize(new Dimension(100, 35));
        btnAgregarLibro.addActionListener(e -> {
            PanelAgregarLibro dialogo = new PanelAgregarLibro(this, libroDAO);
            dialogo.setVisible(true);  
            cargarLibros(); 
        });

     // Panel para actualizar libro
        JButton btnActualizarLibro = new JButton("Actualizar");
        btnActualizarLibro.setBackground(new Color(91, 192, 222));
        btnActualizarLibro.setForeground(Color.WHITE);
        btnActualizarLibro.setFocusPainted(false);
        btnActualizarLibro.setBorderPainted(false);
        btnActualizarLibro.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizarLibro.setPreferredSize(new Dimension(120, 35));
        btnActualizarLibro.addActionListener(e -> actualizarPorId("Libro", libroDAO::verLibroPorId, libro -> {
        	PanelActualizarLibro dialogo = new PanelActualizarLibro(this, libroDAO);
        	dialogo.cargarDatosLibro(libro);
        	dialogo.setVisible(true);
        	cargarLibros();
        }));
        
        // Panel para eliminar libro
        JButton btnEliminarLibro = new JButton("Eliminar");
        btnEliminarLibro.setBackground(new Color(217, 83, 79));
        btnEliminarLibro.setForeground(Color.WHITE);
        btnEliminarLibro.setFocusPainted(false);
        btnEliminarLibro.setBorderPainted(false);
        btnEliminarLibro.setFont(new Font("Arial", Font.BOLD, 12));
        btnEliminarLibro.setPreferredSize(new Dimension(100, 35));
        btnEliminarLibro.addActionListener(e -> eliminarPorId("Libro", libroDAO::verLibroPorId, id ->{
        	libroDAO.eliminarLibro(id);
        	cargarLibros();
        }));
        
        // Botón para ordenar libros por título
        JButton btnOrdenarLibros = new JButton("Ordenar");
        btnOrdenarLibros.setBackground(new Color(175, 116, 175));
        btnOrdenarLibros.setForeground(Color.WHITE);
        btnOrdenarLibros.setFocusPainted(false);
        btnOrdenarLibros.setBorderPainted(false);
        btnOrdenarLibros.setFont(new Font("Arial", Font.BOLD, 12));
        btnOrdenarLibros.setPreferredSize(new Dimension(100, 35));
        btnOrdenarLibros.addActionListener(e -> {
        	cargarLibrosOrdenados();
        });
	    
	    // Panel para agregar género
        JButton btnAgregarGenero = new JButton("Agregar Género");
        btnAgregarGenero.setBackground(new Color(255, 193, 7)); 
        btnAgregarGenero.setForeground(Color.WHITE);
        btnAgregarGenero.setFocusPainted(false);
        btnAgregarGenero.setBorderPainted(false);
        btnAgregarGenero.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregarGenero.setPreferredSize(new Dimension(130, 35)); 

	    btnAgregarGenero.addActionListener(e -> {
	    	PanelAgregarGenero dialogo = new PanelAgregarGenero(this, generoDAO);
	    	dialogo.setVisible(true);
	    });
	    
	    JPanel panelAgregarLibro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
	    panelAgregarLibro.setBackground(Color.WHITE);
	    panelAgregarLibro.add(btnAgregarLibro);
	    panelAgregarLibro.add(btnActualizarLibro);
	    panelAgregarLibro.add(btnEliminarLibro);
	    panelAgregarLibro.add(btnOrdenarLibros);
	    panelAgregarLibro.add(btnAgregarGenero);
	    panelTablaLibros.add(panelAgregarLibro, BorderLayout.SOUTH);
	    
	    tabbedPane.addTab("Libros", panelTablaLibros);
	    
	    
	 // -- PANEL PERSONAS --
	    JPanel panelTablaPersonas = new JPanel(new BorderLayout());
	    panelTablaPersonas.setBackground(Color.WHITE);
	    panelTablaPersonas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    
        JLabel tituloPersonas = new JLabel("Gestión de Personas");
        tituloPersonas.setFont(new Font("Arial", Font.BOLD, 16));
        tituloPersonas.setForeground(new Color(70, 70, 70));
        tituloPersonas.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panelTablaPersonas.add(tituloPersonas, BorderLayout.NORTH);
        
	    modeloTablaPersonas = new DefaultTableModel(new String[] {"ID", "Primer Nombre", "Segundo Nombre", "Primer Apellido", "Segundo Apellido", "Documento"}, 0);
	    tablaPersonas = new JTable(modeloTablaPersonas);
	    
        tablaPersonas.setRowHeight(25);
        tablaPersonas.setGridColor(new Color(230, 230, 230));
        tablaPersonas.setSelectionBackground(new Color(184, 207, 229));
        tablaPersonas.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JTableHeader headerPersonas = tablaPersonas.getTableHeader();
        headerPersonas.setBackground(new Color(240, 240, 240));
        headerPersonas.setFont(new Font("Arial", Font.BOLD, 12));
        headerPersonas.setForeground(new Color(70, 70, 70));
	    
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
	    JScrollPane scrollPersonas = new JScrollPane(tablaPersonas);
        scrollPersonas.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
	    panelTablaPersonas.add(scrollPersonas, BorderLayout.CENTER);
	    
	    // Panel para agregar personas
	    JButton btnAgregarPersona = new JButton("Agregar");
	    btnAgregarPersona.setBackground(new Color(92, 184, 92));
        btnAgregarPersona.setForeground(Color.WHITE);
        btnAgregarPersona.setFocusPainted(false);
        btnAgregarPersona.setBorderPainted(false);
        btnAgregarPersona.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregarPersona.setPreferredSize(new Dimension(100, 35));
	    btnAgregarPersona.addActionListener(e -> {
	    	PanelAgregarPersona dialogo = new PanelAgregarPersona(this, personaDAO);
	    	dialogo.setVisible(true);
	    	cargarPersonas();
	    });
	    
	    // Panel para actualizar persona
	    JButton btnActualizarPersona = new JButton("Actualizar");
	    btnActualizarPersona.setBackground(new Color(91, 192, 222));
        btnActualizarPersona.setForeground(Color.WHITE);
        btnActualizarPersona.setFocusPainted(false);
        btnActualizarPersona.setBorderPainted(false);
        btnActualizarPersona.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizarPersona.setPreferredSize(new Dimension(120, 35));
        btnActualizarPersona.addActionListener(e -> actualizarPorId("Persona", personaDAO::verPersonaPorId, persona -> {
        	PanelActualizarPersona dialogo = new PanelActualizarPersona(this, personaDAO);
        	dialogo.cargarDatosPersona(persona);
        	dialogo.setVisible(true);
        	cargarPersonas();
        }));
	    
	    //Panel para eliminar persona
	    JButton btnEliminarPersona = new JButton("Eliminar");
	    btnEliminarPersona.setBackground(new Color(217, 83, 79));
        btnEliminarPersona.setForeground(Color.WHITE);
        btnEliminarPersona.setFocusPainted(false);
        btnEliminarPersona.setBorderPainted(false);
        btnEliminarPersona.setFont(new Font("Arial", Font.BOLD, 12));
        btnEliminarPersona.setPreferredSize(new Dimension(100, 35));
        btnEliminarPersona.addActionListener(e -> eliminarPorId("Persona", personaDAO::verPersonaPorId, id ->{
        	personaDAO.eliminarPersona(id);
        	cargarPersonas();
	    }));
	    
	    JPanel panelAgregarPersona = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
	    panelAgregarPersona.setBackground(Color.WHITE);
	    panelAgregarPersona.add(btnAgregarPersona);
	    panelAgregarPersona.add(btnActualizarPersona);
	    panelAgregarPersona.add(btnEliminarPersona);
	    panelTablaPersonas.add(panelAgregarPersona, BorderLayout.SOUTH);
	    
	    tabbedPane.addTab("Personas", panelTablaPersonas);
	    
	 // -- PANEL PRESTAMOS --
	    JPanel panelTablaPrestamos = new JPanel(new BorderLayout());
	    panelTablaPrestamos.setBackground(Color.WHITE);
	    panelTablaPrestamos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    
	    // Título de sección
        JLabel tituloPrestamos = new JLabel("Gestión de Préstamos");
        tituloPrestamos.setFont(new Font("Arial", Font.BOLD, 16));
        tituloPrestamos.setForeground(new Color(70, 70, 70));
        tituloPrestamos.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panelTablaPrestamos.add(tituloPrestamos, BorderLayout.NORTH);
        
	    modeloTablaPrestamos = new DefaultTableModel(new String[] {"ID", "Persona", "Libro", "Estado"}, 0);
	    tablaPrestamos = new JTable(modeloTablaPrestamos);
	    
        tablaPrestamos.setRowHeight(25);
        tablaPrestamos.setGridColor(new Color(230, 230, 230));
        tablaPrestamos.setSelectionBackground(new Color(184, 207, 229));
        tablaPrestamos.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JTableHeader headerPrestamos = tablaPrestamos.getTableHeader();
        headerPrestamos.setBackground(new Color(240, 240, 240));
        headerPrestamos.setFont(new Font("Arial", Font.BOLD, 12));
        headerPrestamos.setForeground(new Color(70, 70, 70));
	    
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
	    JScrollPane scrollPrestamos = new JScrollPane(tablaPrestamos);
        scrollPrestamos.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
	    panelTablaPrestamos.add(scrollPrestamos, BorderLayout.CENTER);
	    
	    JButton btnAgregarPrestamo = new JButton("Agregar");
	    btnAgregarPrestamo.setBackground(new Color(92, 184, 92));
        btnAgregarPrestamo.setForeground(Color.WHITE);
        btnAgregarPrestamo.setFocusPainted(false);
        btnAgregarPrestamo.setBorderPainted(false);
        btnAgregarPrestamo.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregarPrestamo.setPreferredSize(new Dimension(100, 35));
	    btnAgregarPrestamo.addActionListener(e -> {
	    	PanelAgregarPrestamo dialogo = new PanelAgregarPrestamo(this, prestamoDAO);
	    	dialogo.setVisible(true);
	    	cargarPrestamos();
	    });
	    
	    // Panel para actualizar préstamo
	    JButton btnActualizarPrestamo = new JButton("Actualizar");
	    btnActualizarPrestamo.setBackground(new Color(91, 192, 222));
        btnActualizarPrestamo.setForeground(Color.WHITE);
        btnActualizarPrestamo.setFocusPainted(false);
        btnActualizarPrestamo.setBorderPainted(false);
        btnActualizarPrestamo.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizarPrestamo.setPreferredSize(new Dimension(120, 35));
        btnActualizarPrestamo.addActionListener(e -> actualizarPorId("Prestamo", prestamoDAO::verPrestamoPorId, prestamo-> {
        	PanelActualizarPrestamo dialogo = new PanelActualizarPrestamo(this, prestamoDAO);
        	dialogo.cargarDatosPrestamo(prestamo);
        	dialogo.setVisible(true);
        	cargarPrestamos();
        }));
	    
	    //Panel para eliminar préstamo
	    JButton btnEliminarPrestamo = new JButton("Eliminar");
	    btnEliminarPrestamo.setBackground(new Color(217, 83, 79));
        btnEliminarPrestamo.setForeground(Color.WHITE);
        btnEliminarPrestamo.setFocusPainted(false);
        btnEliminarPrestamo.setBorderPainted(false);
        btnEliminarPrestamo.setFont(new Font("Arial", Font.BOLD, 12));
        btnEliminarPrestamo.setPreferredSize(new Dimension(100, 35));
        btnEliminarPrestamo.addActionListener(e -> eliminarPorId("Préstamo", prestamoDAO::verPrestamoPorId, id ->{
        	prestamoDAO.eliminarPrestamo(id);
        	cargarPrestamos();
	    }));

	    
	    JPanel panelBotonesPrestamo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
	    panelBotonesPrestamo.setBackground(Color.WHITE);
	    panelBotonesPrestamo.add(btnAgregarPrestamo);
	    panelBotonesPrestamo.add(btnActualizarPrestamo);
	    panelBotonesPrestamo.add(btnEliminarPrestamo);
	    panelTablaPrestamos.add(panelBotonesPrestamo, BorderLayout.SOUTH);
	    
	    tabbedPane.addTab("Préstamos", panelTablaPrestamos);
	    
	    mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
	    
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

