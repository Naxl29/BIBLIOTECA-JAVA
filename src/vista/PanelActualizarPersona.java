package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Persona;
import dao.PersonaDAO;

@SuppressWarnings("serial")
public class PanelActualizarPersona extends JDialog implements ActionListener {

	public final static String ACEPTAR = "Aceptar";
	public final static String CANCELAR = "Cancelar";

	private JTextField txtId;
	private JTextField txtPrimerNombre;
	private JTextField txtSegundoNombre;
	private JTextField txtPrimerApellido;
	private JTextField txtSegundoApellido;
	private JTextField txtDocumento;

	private JButton btnAceptar;
	private JButton btnCancelar;

	private PersonaDAO personaDAO;

	public PanelActualizarPersona(JFrame parent, PersonaDAO dao) {
		super(parent, "Actualizar Persona", true);
		this.personaDAO = dao;

		setSize(400, 300);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());

		JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
		panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Persona"));

		panelFormulario.add(new JLabel("ID:"));
		txtId = new JTextField();
		panelFormulario.add(txtId);

		panelFormulario.add(new JLabel("Primer Nombre:"));
		txtPrimerNombre = new JTextField();
		panelFormulario.add(txtPrimerNombre);

		panelFormulario.add(new JLabel("Segundo Nombre:"));
		txtSegundoNombre = new JTextField();
		panelFormulario.add(txtSegundoNombre);

		panelFormulario.add(new JLabel("Primer Apellido:"));
		txtPrimerApellido = new JTextField();
		panelFormulario.add(txtPrimerApellido);

		panelFormulario.add(new JLabel("Segundo Apellido:"));
		txtSegundoApellido = new JTextField();
		panelFormulario.add(txtSegundoApellido);

		panelFormulario.add(new JLabel("Número de Documento:"));
		txtDocumento = new JTextField();
		panelFormulario.add(txtDocumento);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setActionCommand(PanelActualizarPersona.ACEPTAR);
		btnAceptar.addActionListener(this);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(PanelActualizarPersona.CANCELAR);
		btnCancelar.addActionListener(this);

		JPanel panelBotones = new JPanel();
		panelBotones.add(btnAceptar);
		panelBotones.add(btnCancelar);

		add(panelFormulario, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);
	}
	
	public void cargarDatosPersona(Persona persona) {
	    txtId.setText(String.valueOf(persona.getId()));
	    txtId.setEditable(false);
	    txtPrimerNombre.setText(persona.getPrimerNombre());
	    txtSegundoNombre.setText(persona.getSegundoNombre());
	    txtPrimerApellido.setText(persona.getPrimerApellido());
	    txtSegundoApellido.setText(persona.getSegundoApellido());
	    txtDocumento.setText(persona.getDocumento());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		if (comando.equals(ACEPTAR)) {
			try {
				int id = Integer.parseInt(txtId.getText().trim());
				String primerNombre = txtPrimerNombre.getText().trim();
				String segundoNombre = txtSegundoNombre.getText().trim();
				String primerApellido = txtPrimerApellido.getText().trim();
				String segundoApellido = txtSegundoApellido.getText().trim();
				String documento = txtDocumento.getText().trim();
				
				Persona persona = new Persona(id, primerNombre, segundoNombre, primerApellido, segundoApellido, documento);
				personaDAO.actualizarPersona(persona);
				
				JOptionPane.showMessageDialog(this, "Persona actualizada correctamente.");
				dispose();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error al actualizar la persona: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (comando.equals(CANCELAR)) {
			dispose();
		}
	}
}
