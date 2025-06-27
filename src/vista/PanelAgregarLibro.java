package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import dao.GeneroDAO;
import dao.GeneroDAOImpl;
import dao.LibroDAO;
import modelo.Libro;

@SuppressWarnings("serial")
public class PanelAgregarLibro extends JDialog implements ActionListener {

	public final static String ACEPTAR = "Aceptar";
	public final static String CANCELAR = "Cancelar";
	
	private JTextField txtTitulo;
	private JTextField txtAutor;
	private JTextField txtEditorial;
	private JComboBox<String> comboGenero;
	private JTextField txtImagen;
	private JTextField txtStock;
	private JButton btnSeleccionarImagen;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private LibroDAO libroDAO;
	private GeneroDAO generoDAO;
	
	public PanelAgregarLibro(JFrame parent, LibroDAO dao) {
		super(parent, "Agregar Libro", true);
		this.libroDAO = dao;
		this.generoDAO = new GeneroDAOImpl();
		
		setSize(400, 300);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());
		
		JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
		panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Libro"));
		
		panelFormulario.add(new JLabel("Título:"));
		txtTitulo = new JTextField();
		panelFormulario.add(txtTitulo);
		
		panelFormulario.add(new JLabel("Autor:"));
		txtAutor = new JTextField();
		panelFormulario.add(txtAutor);
		
		panelFormulario.add(new JLabel ("Editorial:"));
		txtEditorial = new JTextField();
		panelFormulario.add(txtEditorial);
		
		panelFormulario.add(new JLabel("Género:"));
		comboGenero = new JComboBox<>();
		cargarGeneros(); 
		panelFormulario.add(comboGenero);
		
		panelFormulario.add(new JLabel("Imagen:"));
		JPanel panelImagen = new JPanel(new BorderLayout());
		txtImagen = new JTextField();
		btnSeleccionarImagen = new JButton("Seleccionar");
		btnSeleccionarImagen.addActionListener(e -> seleccionarImagen());
		panelImagen.add(txtImagen, BorderLayout.CENTER);
		panelImagen.add(btnSeleccionarImagen, BorderLayout.EAST);
		panelFormulario.add(panelImagen);
		
		panelFormulario.add(new JLabel("Stock:"));
		txtStock = new JTextField();
		panelFormulario.add(txtStock);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setActionCommand(PanelAgregarLibro.ACEPTAR);
		btnAceptar.addActionListener(this);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(PanelAgregarLibro.CANCELAR);
		btnCancelar.addActionListener(this);
		
		JPanel panelBotones = new JPanel();
		panelBotones.add(btnAceptar);
		panelBotones.add(btnCancelar);
		
		add(panelFormulario, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);
	}
	
	private void cargarGeneros() {
		for (String genero : generoDAO.obtenerGeneros()) {
			comboGenero.addItem(genero);
		}
	}
	
	public void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoOriginal = fileChooser.getSelectedFile();
            String nombreArchivo = archivoOriginal.getName();
            File carpetaDestino = new File("imagenes");
            if (!carpetaDestino.exists()) {
                carpetaDestino.mkdir();
            }
            File archivoDestino = new File(carpetaDestino, nombreArchivo);
            try {
                Files.copy(
                    archivoOriginal.toPath(),
                    archivoDestino.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                );
                txtImagen.setText("imagenes/" + nombreArchivo); // Guardamos ruta relativa
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al copiar la imagen: " + ex.getMessage());
            }
        }
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		if (comando.equals(ACEPTAR)) {
			try {
				String titulo = txtTitulo.getText().trim();
				String autor = txtAutor.getText().trim();
				String editorial = txtEditorial.getText().trim();
				String genero = (String) comboGenero.getSelectedItem();
				String imagen = txtImagen.getText().trim();
				int stock = Integer.parseInt(txtStock.getText().trim());
				
				int generoId = generoDAO.obtenerIdGenero(genero);
				
				if (titulo.isEmpty() || autor.isEmpty() || editorial.isEmpty() || generoId == -1 || imagen.isEmpty() || stock < 0) {
					JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Libro libro = new Libro(0, titulo, autor, editorial, generoId, imagen, stock);
				libroDAO.crearLibro(libro);
				
				JOptionPane.showMessageDialog(this, "Libro agregado exitosamente.");
				dispose();
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al agregar libro:" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		} else if (comando.equals(CANCELAR)) {
			dispose();
		}
	}
}
