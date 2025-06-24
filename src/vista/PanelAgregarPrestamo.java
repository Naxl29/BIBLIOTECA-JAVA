package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.*;
import dao.*;

@SuppressWarnings("serial")
public class PanelAgregarPrestamo extends JDialog implements ActionListener {
	
	public final static String ACEPTAR = "Aceptar";
	public final static String CANCELAR = "Cancelar";
	
	private JComboBox<Persona> cbPersonas;
	private JComboBox<Libro> cbLibros;
	private JComboBox<Estado> cbEstados;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private PrestamoDAO prestamoDAO;
	
	private PersonaDAO personaDAO = new PersonaDAOImpl();
	private LibroDAO libroDAO = new LibroDAOImpl();
	
	public PanelAgregarPrestamo(JFrame parent, PrestamoDAO dao) {
		super(parent, "Agregar Préstamo", true);
		this.prestamoDAO = dao;
		
		setSize(400, 300);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());
		
		JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
		panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Préstamo"));
		
		panelFormulario.add(new JLabel("ID Persona:"));
		cbPersonas = new JComboBox<>();
		for (Persona persona : personaDAO.verTodasLasPersonas()) {
			cbPersonas.addItem(persona);
		}
		panelFormulario.add(cbPersonas);
		
		panelFormulario.add(new JLabel("ID Libro:"));
		cbLibros = new JComboBox<>();
		for (Libro libro : libroDAO.verTodosLosLibros()) {
			cbLibros.addItem(libro);
		}
		panelFormulario.add(cbLibros);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setActionCommand(ACEPTAR);
		btnAceptar.addActionListener(this);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(CANCELAR);
		btnCancelar.addActionListener(this);
		
		JPanel panelBotones = new JPanel();
		panelFormulario.add(btnAceptar);
		panelFormulario.add(btnCancelar);
		
		add(panelFormulario, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		if (comando.equals(ACEPTAR)) {
			Persona personaSeleccionada = (Persona) cbPersonas.getSelectedItem();
			Libro libroSeleccionado = (Libro) cbLibros.getSelectedItem();
			
			if (personaSeleccionada == null || libroSeleccionado == null) {
				JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
				return;
			}
			
			int estadoId = 1; 
			
			Prestamo prestamo = new Prestamo(0, personaSeleccionada.getId(), 
				libroSeleccionado.getId(), estadoId);
			prestamoDAO.crearPrestamo(prestamo);
			
			JOptionPane.showMessageDialog(this, "Préstamo agregado exitosamente.");
			dispose();
		} else if (comando.equals(CANCELAR)) {
			dispose();
		}
	}
}
