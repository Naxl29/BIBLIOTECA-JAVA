package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Prestamo;
import dao.PrestamoDAO;

@SuppressWarnings("serial")
public class PanelAgregarPrestamo extends JDialog implements ActionListener {
	
	public final static String ACEPTAR = "Aceptar";
	public final static String CANCELAR = "Cancelar";
	
	private JTextField txtIdPersona;
	private JTextField txtIdLibro;
	private JTextField txtIdEstado;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private PrestamoDAO prestamoDAO;
	
	public PanelAgregarPrestamo(JFrame parent, PrestamoDAO dao) {
		super(parent, "Agregar Préstamo", true);
		this.prestamoDAO = dao;
		
		setSize(400, 300);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());
		
		JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
		panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Préstamo"));
		
		panelFormulario.add(new JLabel("ID Persona:"));
		txtIdPersona = new JTextField();
		panelFormulario.add(txtIdPersona);
		
		panelFormulario.add(new JLabel("ID Libro:"));
		txtIdLibro = new JTextField();
		panelFormulario.add(txtIdLibro);
		
		panelFormulario.add(new JLabel("ID Estado:"));
		txtIdEstado = new JTextField();
		panelFormulario.add(txtIdEstado);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setActionCommand(PanelAgregarPrestamo.ACEPTAR);
		btnAceptar.addActionListener(this);
		panelFormulario.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(PanelAgregarPrestamo.CANCELAR);
		btnCancelar.addActionListener(this);
		panelFormulario.add(btnCancelar);
		
		add(panelFormulario, BorderLayout.CENTER);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		if (comando.equals(ACEPTAR)) {
			int idPersona = Integer.parseInt(txtIdPersona.getText());
			int idLibro = Integer.parseInt(txtIdLibro.getText());
			int idEstado = Integer.parseInt(txtIdEstado.getText());
			
			if (idPersona <= 0 || idLibro <= 0 || idEstado <= 0) {
				JOptionPane.showMessageDialog(this, "Los IDs deben ser mayores a cero.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Prestamo prestamo = new Prestamo(0, idPersona, idLibro, idEstado);
			prestamoDAO.crearPrestamo(prestamo);
			
			JOptionPane.showMessageDialog(this, "Préstamo agregado exitosamente.");
			dispose();
		} else if (comando.equals(CANCELAR)) {
			dispose();
		}
	}
}
