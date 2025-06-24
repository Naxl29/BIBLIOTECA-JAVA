package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
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
		cargarGeneros(); // esta llena el JComboBox con los géneros de la base de datos
		panelFormulario.add(comboGenero);
		
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
	
	//Cargar los géneros disponibles desde la base de datos
	private void cargarGeneros() {
		if (generoDAO != null) {
			for (String genero : generoDAO.obtenerGeneros()) {
				comboGenero.addItem(genero);
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
				
				//obtener el id del género seleccionado
				int generoId = generoDAO.obtenerIdGenero(genero);
				
				//Crear el objeto libro
				Libro libro = new Libro(0, titulo, autor, editorial, generoId);
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
