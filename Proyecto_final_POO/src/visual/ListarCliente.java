package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Cliente;
import logico.Conexion;
import logico.DiscoDuro;
import logico.MemoriaRam;
import logico.MicroProcesador;
import logico.MotherBoard;
import logico.Producto;
import logico.Tienda;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class ListarCliente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static DefaultTableModel modelCliente;
	private static Object[] rows;
	private JLabel Modificar;
	private JTable tableCliente;
	private Cliente selectedCliente = null;
	private JTextField txtCedulaCliente;
	private String cedulaCliente = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarCliente dialog = new ListarCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarCliente() {
		setUndecorated(true);
		setBounds(100, 100, 1117, 703);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBackground(Color.white);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		txtCedulaCliente = new JTextField();
		txtCedulaCliente.setHorizontalAlignment(SwingConstants.CENTER);
		txtCedulaCliente.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtCedulaCliente.setColumns(10);
		txtCedulaCliente.setBackground(Color.WHITE);
		txtCedulaCliente.setBounds(352, 28, 258, 40);
		contentPanel.add(txtCedulaCliente);
		
		JLabel label_1 = new JLabel("Buscar");
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!(txtCedulaCliente.getText().isEmpty())) {
					cedulaCliente = txtCedulaCliente.getText();
					loadTableCliente(cedulaCliente);
				}else {
					txtCedulaCliente.setText("");
					loadTableCliente(null);
				}
			}
		});
		label_1.setIcon(new ImageIcon(ListarCliente.class.getResource("/Imagenes/Lupa.png")));
		label_1.setOpaque(true);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		label_1.setBackground(new Color(0, 155, 124));
		label_1.setBounds(620, 28, 150, 40);
		contentPanel.add(label_1);
		
		JScrollPane scrollPaneCliente = new JScrollPane();
		scrollPaneCliente.setBounds(352, 81, 730, 565);
		contentPanel.add(scrollPaneCliente);
		
		String header[] = {"Cedula", "Nombre", "Telefono", "Direccion"};
		modelCliente = new DefaultTableModel();
		modelCliente.setColumnIdentifiers(header);
				
		tableCliente = new JTable();
		tableCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = -1;
				index = tableCliente.getSelectedRow();
				if(index != -1) {
					Modificar.setEnabled(true);
					String cedula = (String)(modelCliente.getValueAt(index, 0));
					selectedCliente = Tienda.getInstance().buscarClienteByCedula(cedula);
				}
			}
		});
		tableCliente.setModel(modelCliente);
		tableCliente.getTableHeader().setBackground(new Color(0, 155, 124));
		tableCliente.getTableHeader().setForeground(Color.WHITE);
		scrollPaneCliente.setViewportView(tableCliente);
		scrollPaneCliente.setViewportView(tableCliente);
		scrollPaneCliente.getViewport().setBackground(Color.white);
		
		JPanel panel = new JPanel();
		panel.setBounds(22, 808, 10, 10);
		contentPanel.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 13, 288, 677);
		panel_1.setBackground(new Color(36, 37, 38));
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(48, 26, 209, 83);
		panel_1.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon(ListarCliente.class.getResource("/Imagenes/ClientesLabelBlanco.png")));
		
		Modificar = new JLabel(" Modificar");
		Modificar.setVisible(false);
		Modificar.setHorizontalAlignment(SwingConstants.CENTER);
		Modificar.setBounds(0, 170, 288, 44);
		panel_1.add(Modificar);
		Modificar.setEnabled(false);
		Modificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				Modificar.setBackground(new Color(0, 155, 124));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Modificar.setBackground(new Color(36, 37, 38));
			}
		});
		Modificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Modificar.setIcon(new ImageIcon(ListarCliente.class.getResource("/Imagenes/ModificarIcon.png")));
		Modificar.setForeground(Color.WHITE);
		Modificar.setFont(new Font("Tahoma", Font.PLAIN, 34));
		Modificar.setBackground(new Color(36, 37, 38));
		
		JLabel lblCancelar = new JLabel(" Cancelar");
		lblCancelar.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancelar.setBounds(0, 626, 288, 51);
		panel_1.add(lblCancelar);
		lblCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		lblCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblCancelar.setIcon(new ImageIcon(ListarCliente.class.getResource("/Imagenes/CancelarIcon.png")));
		lblCancelar.setOpaque(true);
		lblCancelar.setForeground(Color.WHITE);
		lblCancelar.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblCancelar.setBackground(new Color(0, 155, 124));
		
		JLabel lblAdministracin = new JLabel("Administraci\u00F3n");
		lblAdministracin.setForeground(Color.WHITE);
		lblAdministracin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAdministracin.setBounds(75, 81, 144, 64);
		panel_1.add(lblAdministracin);
		
		final JLabel label = new JLabel("X");
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				label.setForeground(Color.red);
			}
		});
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.DARK_GRAY);
		label.setFont(new Font("Tahoma", Font.PLAIN, 40));
		label.setBounds(1060, -2, 57, 55);
		contentPanel.add(label);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBackground(Color.WHITE);
		lblNewLabel_2.setIcon(new ImageIcon(ListarCliente.class.getResource("/Imagenes/PanelListar.png")));
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBounds(312, 8, 793, 682);
		contentPanel.add(lblNewLabel_2);
		loadTableCliente(null);
	}
	
	public static void loadTableCliente(String cedula) {
		
		String headerCliente[] = {"Cedula", "Nombre", "Direccion", "Telefono", "Cantidad de Compras"};
		modelCliente.setColumnIdentifiers(headerCliente);
		modelCliente.setRowCount(0);
		rows = new Object[modelCliente.getColumnCount()];
		
		Statement selectStmt = null;
		try {
			selectStmt = Conexion.getInstance().createStatement();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectarse a la Base de Datos", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		if(cedula == null) {
			try {
				ResultSet rs = selectStmt.executeQuery("SELECT * FROM datos_persona_cliente;");
				
				while (rs.next()) {
					rows[0] = rs.getString("cedulaPersona");
					rows[1] = rs.getString("nombre");
					rows[2] = rs.getString("direccion") + ", " + rs.getString("nombreMunicipio");;
					rows[3] = rs.getString("telefono");
					rows[4] = rs.getInt("cantCompras");
	
					modelCliente.addRow(rows);
				}
				rs.close();
	
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al buscar clientes en la BD", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			try {
				String SQL = "SELECT * FROM datos_persona_cliente WHERE cedulaPersona LIKE '"+cedula+"%';";
				PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					rows[0] = rs.getString("cedulaPersona");
					rows[1] = rs.getString("nombre");
					rows[2] = rs.getString("direccion") + ", " + rs.getString("nombreMunicipio");;
					rows[3] = rs.getString("telefono");
					rows[4] = rs.getInt("cantCompras");

					modelCliente.addRow(rows);
				}
				rs.close();
	
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al buscar clientes en la BD", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

