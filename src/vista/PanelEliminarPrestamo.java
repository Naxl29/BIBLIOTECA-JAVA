package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.PrestamoDAO;

@SuppressWarnings("serial")
public class PanelEliminarPrestamo extends JDialog implements ActionListener {
	public final static String ACEPTAR = "Aceptar";
	public final static String CANCELAR = "Cancelar";

	private JTextField txtIdEliminar;

	private JButton btnAceptar;
	private JButton btnCancelar;

	private PrestamoDAO prestamoDAO;

	public PanelEliminarPrestamo(JFrame parent, PrestamoDAO dao) {
		super(parent, "Eliminar Préstamo", true);
		this.prestamoDAO = dao;

		setSize(400, 300);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());

		JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
		panelFormulario.setBorder(BorderFactory.createTitledBorder("Eliminar Préstamo"));

		panelFormulario.add(new JLabel("ID del Préstamo:"));
		txtIdEliminar = new JTextField();
		panelFormulario.add(txtIdEliminar);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setActionCommand(PanelEliminarPrestamo.ACEPTAR);
		btnAceptar.addActionListener(this);
		panelFormulario.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(PanelEliminarPrestamo.CANCELAR);
		btnCancelar.addActionListener(this);
		panelFormulario.add(btnCancelar);

		add(panelFormulario, BorderLayout.CENTER);
		setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(PanelEliminarPrestamo.ACEPTAR)) {
			try {
				int id = Integer.parseInt(txtIdEliminar.getText());
				prestamoDAO.eliminarPrestamo(id);
				JOptionPane.showMessageDialog(this, "Préstamo eliminado exitosamente.");
				dispose();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getActionCommand().equals(PanelEliminarPrestamo.CANCELAR)) {
			dispose();
		}
	}

}
