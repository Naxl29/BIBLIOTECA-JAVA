package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.*;
import dao.*;

@SuppressWarnings("serial")
public class PanelActualizarPrestamo extends JDialog implements ActionListener {

	public final static String ACEPTAR = "Aceptar";
	public final static String CANCELAR = "Cancelar";

	private JTextField txtId;
	private JComboBox<Persona> cbPersonas;
	private JComboBox<Libro> cbLibros;
	private JComboBox<Estado> cbEstados;

	private JButton btnAceptar;
	private JButton btnCancelar;

	private PrestamoDAO prestamoDAO;
	private PersonaDAO personaDAO = new PersonaDAOImpl();
	private LibroDAO libroDAO = new LibroDAOImpl();
	private EstadoDAO estadoDAO = new EstadoDAOImpl();
	
	private Prestamo prestamo;

	public PanelActualizarPrestamo(JFrame parent, PrestamoDAO dao) {
		super(parent, "Actualizar Préstamo", true);
		this.prestamoDAO = dao;

		setSize(400, 300);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());

		JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
		panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Préstamo"));

		panelFormulario.add(new JLabel("ID del Préstamo:"));
		txtId = new JTextField();
		txtId.setEditable(false); 
		panelFormulario.add(txtId);

		panelFormulario.add(new JLabel("Persona:"));
		cbPersonas = new JComboBox<>();
		cbPersonas.setEnabled(false); 
		for (Persona persona : personaDAO.verTodasLasPersonas()) {
			cbPersonas.addItem(persona);
		}
		panelFormulario.add(cbPersonas);
		
		panelFormulario.add(new JLabel("Libro:"));
		cbLibros = new JComboBox<>();
		cbLibros.setEnabled(false);
		for (Libro libro : libroDAO.verTodosLosLibros()) {
			cbLibros.addItem(libro);
		}
		panelFormulario.add(cbLibros);
		
		panelFormulario.add(new JLabel("Estado:"));
		cbEstados = new JComboBox<>();
		for (Estado estado : estadoDAO.verEstados()) {
			cbEstados.addItem(estado);
		}
		panelFormulario.add(cbEstados);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setActionCommand(PanelActualizarPrestamo.ACEPTAR);
		btnAceptar.addActionListener(this);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(PanelActualizarPrestamo.CANCELAR);
		btnCancelar.addActionListener(this);
	
		JPanel panelBotones = new JPanel();
		panelBotones.add(btnAceptar);
		panelBotones.add(btnCancelar);

		add(panelFormulario, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);
	}
	
	public void cargarDatosPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
		txtId.setText(String.valueOf(prestamo.getId()));
		
		for (int i = 0; i < cbPersonas.getItemCount(); i++) {
			if (cbPersonas.getItemAt(i).getId() == prestamo.getIdPersona()) {
				cbPersonas.setSelectedIndex(i);
				break;
			}
		}
		
		for (int i = 0; i < cbLibros.getItemCount(); i++) {
			if (cbLibros.getItemAt(i).getId() == prestamo.getIdLibro()) {
				cbLibros.setSelectedIndex(i);
				break;
			}
		}
		
		for (int i = 0; i < cbEstados.getItemCount(); i++) {
			if (cbEstados.getItemAt(i).getId() == prestamo.getIdEstado()) {
				cbEstados.setSelectedIndex(i);
				break;
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		if (comando.equals(ACEPTAR)) {
			try {
				int id = Integer.parseInt(txtId.getText());
			    Persona personaSeleccionada = (Persona) cbPersonas.getSelectedItem();
			    Libro libroSeleccionado = (Libro) cbLibros.getSelectedItem();
			    Estado estadoSeleccionado = (Estado) cbEstados.getSelectedItem();
			    
			    Prestamo prestamo = new Prestamo(id, personaSeleccionada.getId(), libroSeleccionado.getId(), estadoSeleccionado.getId());
			    prestamoDAO.actualizarPrestamo(prestamo);
			    
			    JOptionPane.showMessageDialog(this, "Préstamo actualizado correctamente.");
			    dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al actualizar el préstamo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (comando.equals(CANCELAR)) {
			dispose();
		}
	}

}
