package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Persona;
import dao.PersonaDAO;

@SuppressWarnings("serial")
public class PanelAgregarPersona extends JDialog implements ActionListener {

	public final static String ACEPTAR = "Aceptar";
	
	public final static String CANCELAR = "Cancelar";
	
	private JTextField txtPrimerNombre;
	private JTextField txtSegundoNombre;
	private JTextField txtPrimerApellido;
	private JTextField txtSegundoApellido;
	private JTextField txtDocumento;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private PersonaDAO personaDAO;
	
	public PanelAgregarPersona(JFrame parent, PersonaDAO dao) {
		super(parent, "Agregar Persona", true);
		this.personaDAO = dao;
		
		setSize(400, 300);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());
		
		JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
		panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Persona"));
		
		panelFormulario.add(new JLabel("Primer Nombre:"));
		txtPrimerNombre = new JTextField();
		panelFormulario.add(txtPrimerNombre);
		
		panelFormulario.add(new JLabel("Segundo Nombre:"));
		txtSegundoNombre = new JTextField();
		panelFormulario.add(txtSegundoNombre);
		
		panelFormulario.add(new JLabel ("Primer Apellido:"));
		txtPrimerApellido = new JTextField();
		panelFormulario.add(txtPrimerApellido);
		
		panelFormulario.add(new JLabel("Segundo Apellido:"));
		txtSegundoApellido = new JTextField();
		panelFormulario.add(txtSegundoApellido);
		
		panelFormulario.add(new JLabel("Número de Documento:"));
		txtDocumento = new JTextField();
		panelFormulario.add(txtDocumento);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setActionCommand(ACEPTAR);
		btnAceptar.addActionListener(this);
		panelFormulario.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(CANCELAR);
		btnCancelar.addActionListener(this);
		panelFormulario.add(btnCancelar);
		
		add(panelFormulario, BorderLayout.CENTER);
		setResizable(false);
		
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		if (comando.equals(ACEPTAR)) {
			String primerNombre = txtPrimerNombre.getText().trim();
			String segundoNombre = txtSegundoNombre.getText().trim();
			String primerApellido = txtPrimerApellido.getText().trim();
			String segundoApellido = txtSegundoApellido.getText().trim();
			String documento = txtDocumento.getText().trim();
			
			if (primerNombre.isEmpty() || primerApellido.isEmpty() || documento.isEmpty()) {
				JOptionPane.showMessageDialog(this, "El primer nombre, primer apellido y número de documento son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Persona nuevaPersona = new Persona(0, primerNombre, segundoNombre, primerApellido, segundoApellido, documento);
			personaDAO.crearPersona(nuevaPersona);
			
			JOptionPane.showMessageDialog(this, "Persona agregada exitosamente.");
			dispose();
			
		} else if (comando.equals(CANCELAR)) {
			dispose();
		}
	}
}

