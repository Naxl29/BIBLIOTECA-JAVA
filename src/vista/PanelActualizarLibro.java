package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import dao.GeneroDAO;
import dao.GeneroDAOImpl;
import dao.LibroDAO;
import modelo.Libro;

@SuppressWarnings("serial")
public class PanelActualizarLibro extends JDialog implements ActionListener {

	public final static String ACEPTAR = "Aceptar";
	public final static String CANCELAR = "Cancelar";
	
	private JTextField txtId;
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

	public PanelActualizarLibro(JFrame parent, LibroDAO dao) {
		super(parent, "Actualizar Libro", true);
		this.libroDAO = dao;
		this.generoDAO = new GeneroDAOImpl();
		
		setSize(400, 300);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());
		
		JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
		panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Libro"));
		
		panelFormulario.add(new JLabel("ID:"));
		txtId = new JTextField();
		txtId.setEditable(false);
		panelFormulario.add(txtId);
		
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
        
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setActionCommand(ACEPTAR);
		btnAceptar.addActionListener(this);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(CANCELAR);
		btnCancelar.addActionListener(this);
		
		JPanel panelBotones = new JPanel();
		panelBotones.add(btnAceptar);
		panelBotones.add(btnCancelar);
		
		add(panelFormulario, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);
	}
	
	public void cargarDatosLibro(Libro libro) {
		txtId.setText(String.valueOf(libro.getId()));
		txtId.setEditable(false);
		txtTitulo.setText(libro.getTitulo());
		txtAutor.setText(libro.getAutor());
		txtEditorial.setText(libro.getEditorial());
		comboGenero.setSelectedItem(generoDAO.obtenerNombreGenero(libro.getIdGenero()));
		txtImagen.setText(libro.getImagen());
	}
	
    private void cargarGeneros() {
    			for (String genero : generoDAO.obtenerGeneros()) {
			comboGenero.addItem(genero);
		}
    }
    
    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            txtImagen.setText(archivoSeleccionado.getAbsolutePath());
        }
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		if (comando.equals(ACEPTAR)) {
			try {
				int id = Integer.parseInt(txtId.getText().trim());
				String titulo = txtTitulo.getText().trim();
				String autor = txtAutor.getText().trim();
				String editorial = txtEditorial.getText().trim();
				String generoNombre = (String) comboGenero.getSelectedItem();
				String imagen = txtImagen.getText().trim();
				int stock = Integer.parseInt(txtStock.getText().trim());
				
				int generoId = generoDAO.obtenerIdGenero(generoNombre);
				
				Libro libro = new Libro(id, titulo, autor, editorial, generoId, imagen);
				libroDAO.actualizarLibro(libro);
				
				JOptionPane.showMessageDialog(this, "Libro actualizado exitosamente.");
				dispose();
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al actualizar libro:" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		} else if (comando.equals(CANCELAR)) {
			dispose();
		}
	}
}