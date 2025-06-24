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
public class PanelActualizarLibro extends JDialog implements ActionListener {

	public final static String ACEPTAR = "Aceptar";
	public final static String CANCELAR = "Cancelar";
	
	private JTextField txtId;
	private JTextField txtTitulo;
	private JTextField txtAutor;
	private JTextField txtEditorial;
	private JComboBox<String> comboGenero;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private LibroDAO libroDAO;
	private GeneroDAO generoDAO;

	public PanelActualizarLibro(JFrame parent, LibroDAO dao) {
		super(parent, "Actualizar Libro", true);
		this.libroDAO = dao;
		
		setSize(400, 300);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());
		
		JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
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
		
		panelFormulario.add(new JLabel("Género (ID):"));
		comboGenero = new JComboBox<>();
        cargarGeneros();  // Cargar géneros disponibles en el JComboBox
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
	
	// Método para cargar los generos disponibles desde la base de datos
    private void cargarGeneros() {
        if (libroDAO != null) {
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
				int id = Integer.parseInt(txtId.getText().trim());
				String titulo = txtTitulo.getText().trim();
				String autor = txtAutor.getText().trim();
				String editorial = txtEditorial.getText().trim();
				String genero = (String) comboGenero.getSelectedItem();
				
				int generoId = generoDAO.obtenerIdGenero(genero);
				
				Libro libro = new Libro(id, titulo, autor, editorial, generoId);
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