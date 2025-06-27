package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.Genero;
import dao.GeneroDAO;

@SuppressWarnings("serial")
public class PanelAgregarGenero extends JDialog implements ActionListener {

	public final static String ACEPTAR = "Aceptar";
	public final static String CANCELAR = "Cancelar";

	private JTextField txtGenero;
	
	private JButton btnAceptar;
	private JButton btnCancelar;

	private GeneroDAO generoDAO;

	public PanelAgregarGenero(JFrame parent, GeneroDAO dao) {
		super(parent, "Agregar Género", true);
		this.generoDAO = dao;

		setSize(400, 300);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());

		JPanel panelFormulario = new JPanel(new GridLayout(2, 2, 5, 5));
		panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Género"));

		panelFormulario.add(new JLabel("Nombre del Género:"));
		txtGenero = new JTextField();
		panelFormulario.add(txtGenero);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setActionCommand(PanelAgregarGenero.ACEPTAR);
		btnAceptar.addActionListener(this);
		panelFormulario.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand(PanelAgregarGenero.CANCELAR);
		btnCancelar.addActionListener(this);
		panelFormulario.add(btnCancelar);

		add(panelFormulario, BorderLayout.CENTER);
		setResizable(false);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		if(comando.equals(ACEPTAR)) {
			String genero = txtGenero.getText().trim();
			if(genero.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Género agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			Genero nuevoGenero = new Genero(0, genero);
			generoDAO.crearGenero(nuevoGenero);
			
			JOptionPane.showMessageDialog(this, "Género agregado exitosamente.");
			dispose();
			
		} else if(comando.equals(CANCELAR)) {
			dispose();
		}
	}
}
