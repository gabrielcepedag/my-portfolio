package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import org.omg.CORBA.TCKind;

import logico.Cliente;
import logico.Conexion;
import logico.Empleado;
import logico.Tienda;
import logico.Vendedor;

import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Cursor;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class RegDistribuidor extends JDialog {

	private JPanel contentPane;
	private JPanel panelRegistro;
	private JTextField txtNombre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegDistribuidor dialog = new RegDistribuidor();
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegDistribuidor() {
		setUndecorated(true);
		setBounds(100, 100, 587, 271);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(36, 37, 38));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		panelRegistro = new JPanel();
		panelRegistro.setLayout(null);
		panelRegistro.setBackground(Color.WHITE);
		panelRegistro.setBounds(11, 12, 553, 239);
		panel.add(panelRegistro);
		
		JLabel lblRegistrar = new JLabel("A\u00F1adir Distribuidor:");
		lblRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblRegistrar.setBounds(33, 13, 322, 55);
		panelRegistro.add(lblRegistrar);
		
		JLabel lblCantidad = new JLabel("Nombre:");
		lblCantidad.setForeground(Color.BLACK);
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCantidad.setBounds(32, 101, 103, 55);
		panelRegistro.add(lblCantidad);
		
		JLabel lblNewLabel = new JLabel("Cancelar");
		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(new Color(0, 155, 124));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(42, 169, 225, 55);
		panelRegistro.add(lblNewLabel);
		
		final JLabel lblX = new JLabel("X");
		lblX.setBounds(496, 0, 57, 55);
		panelRegistro.add(lblX);
		lblX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				lblX.setForeground(Color.red);
			}
		});
		lblX.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setForeground(Color.DARK_GRAY);
		
		JLabel lblRegistrar_1 = new JLabel("A\u00F1adir");
		lblRegistrar_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRegistrar_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtNombre.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "El campo esta vacio", "Error", JOptionPane.WARNING_MESSAGE);
				}
				else {
					if ( ( addDistribuidor(txtNombre.getText()) )){
						JOptionPane.showMessageDialog(null, "Distribuidor registrado correctamente");
					}else {
						JOptionPane.showMessageDialog(null, "Distribuidor no pudo ser registrado correctamente");
					}
					
					clean();
				}
			}
		});
		lblRegistrar_1.setOpaque(true);
		lblRegistrar_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrar_1.setForeground(Color.WHITE);
		lblRegistrar_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRegistrar_1.setBackground(new Color(0, 155, 124));
		lblRegistrar_1.setBounds(301, 169, 225, 55);
		panelRegistro.add(lblRegistrar_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 155, 124));
		panel_1.setBounds(34, 64, 390, 4);
		panelRegistro.add(panel_1);
		
		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtNombre.setBounds(122, 103, 404, 50);
		panelRegistro.add(txtNombre);
		txtNombre.setColumns(10);
				
	}
	protected boolean addDistribuidor(String nombre) {
		boolean continuar = false;

		try {
			String SQL = "INSERT INTO distribuidor "
			+ "(nombre)"
			+ "values( ? ); ";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setString(1, nombre);
			
			int count = pstmt.executeUpdate();
			
			System.out.println("Filas afectadas: "+count);

			pstmt.close();		
		 }
		 catch (Exception e1) {
			 try {
				Conexion.getInstance().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			e1.printStackTrace();
			return continuar;
		 }
		 try {
			Conexion.getInstance().commit();
			continuar = true;
		} catch (SQLException e) {
			continuar = false;
		}
		 return continuar;
	}

	private void clean() {
		txtNombre.setText("");
	}
}