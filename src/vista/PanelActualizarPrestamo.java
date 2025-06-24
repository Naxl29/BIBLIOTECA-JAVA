package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Prestamo;
import dao.PrestamoDAO;

@SuppressWarnings("serial")
public class PanelActualizarPrestamo extends JDialog implements ActionListener {

	public final static String ACEPTAR = "Aceptar";
	public final static String CANCELAR = "Cancelar";

	private JTextField txtId;
	private JTextField txtIdPersona;
	private JTextField txtIdLibro;
	private JTextField txtIdEstado;

	private JButton btnAceptar;
	private JButton btnCancelar;

	private PrestamoDAO prestamoDAO;

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
		txtIdPersona = new JTextField();
		txtIdPersona.setEditable(false);
		panelFormulario.add(txtIdPersona);
		
		panelFormulario.add(new JLabel("Libro:"));
		txtIdLibro = new JTextField();
		txtIdLibro.setEditable(false);
		panelFormulario.add(txtIdLibro);
		
		panelFormulario.add(new JLabel("Estado:"));
		txtIdEstado = new JTextField();
		panelFormulario.add(txtIdEstado);
		
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
		txtId.setText(String.valueOf(prestamo.getId()));
		txtIdPersona.setText(String.valueOf(prestamo.getIdPersona()));
		txtIdLibro.setText(String.valueOf(prestamo.getIdLibro()));
		txtIdEstado.setText(String.valueOf(prestamo.getIdEstado()));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		if (comando.equals(ACEPTAR)) {
			try {
				int id = Integer.parseInt(txtId.getText());
			    int idEstado = Integer.parseInt(txtIdEstado.getText());
			    
			    Prestamo prestamo = new Prestamo(id, idEstado);
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
