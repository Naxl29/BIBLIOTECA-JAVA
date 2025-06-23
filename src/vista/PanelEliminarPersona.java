package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Persona;
import dao.PersonaDAO;

@SuppressWarnings("serial")
public class PanelEliminarPersona extends JDialog implements ActionListener {
	public final static String ACEPTAR = "Aceptar";
	public final static String CANCELAR = "Cancelar";
	
	private JTextField txtIdEliminar;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private PersonaDAO personaDAO;
	
	public PanelEliminarPersona(JFrame parent, PersonaDAO dao) {
		super(parent, "Eliminar Persona", true);
		this.personaDAO = dao;
		
		setSize(400, 300);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());
		
		JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
		panelFormulario.setBorder(BorderFactory.createTitledBorder("Eliminar Persona"));
		
		panelFormulario.add(new JLabel("ID de la Persona:"));
		txtIdEliminar = new JTextField();
		panelFormulario.add(txtIdEliminar);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setActionCommand(PanelEliminarPersona.ACEPTAR);
		btnAceptar.addActionListener(this);
		panelFormulario.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(PanelEliminarPersona.CANCELAR);
		btnCancelar.addActionListener(this);
		panelFormulario.add(btnCancelar);
		
		add(panelFormulario, BorderLayout.CENTER);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(PanelEliminarPersona.ACEPTAR)) {
			try {
				int id = Integer.parseInt(txtIdEliminar.getText());
				personaDAO.eliminarPersona(id);
				JOptionPane.showMessageDialog(this, "Persona eliminada exitosamente.");
				dispose();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getActionCommand().equals(PanelEliminarPersona.CANCELAR)) {
			dispose();
		}
	}
}

