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

public class ListarProducto extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tableProductos;
	private static DefaultTableModel modelProductos;
	private static Object[] rows;
	private JComboBox<String> cbxTipoProducto;
	private JLabel Modificar;
	private JTable tableProductos_1;
	private Producto selectedProducto = null;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarProducto dialog = new ListarProducto();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarProducto() {
		setUndecorated(true);
		setBounds(100, 100, 1117, 703);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBackground(Color.white);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(352, 81, 730, 565);
		contentPanel.add(scrollPane);
		
		String headerProducto[] = {"Numero de serie", "Marca", "Modelo", "Precio", "Cantidad", "Disp. Min", "Categoria"};
		modelProductos = new DefaultTableModel();
		modelProductos.setColumnIdentifiers(headerProducto);
		
		/*Producto p1 = new MotherBoard("402", 10, 25000, "RTX", 1, 20, "QSY", "QSY", "QSY");
		Producto p2 = new MemoriaRam("403", 100, 10000, "TridentZ", 1, 500, 32, "DDR4");
		Producto p3 = new MicroProcesador("404", 55, 5500, "MSI", 10, 60, "QSY", "buena", 100);
		Producto p4 = new DiscoDuro("405", 20, 4500, "Esto", 5, 90, "Funciona", 500, "Maravilla");
		Tienda.getInstance().addProducto(p1);
		Tienda.getInstance().addProducto(p2);
		Tienda.getInstance().addProducto(p3);
		Tienda.getInstance().addProducto(p4);*/
				
		tableProductos_1 = new JTable();
		tableProductos_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = -1;
				index = tableProductos_1.getSelectedRow();
				if(index != -1) {
					String numSerie = (String)(modelProductos.getValueAt(index, 0));					
					selectedProducto = Tienda.getInstance().buscarProductoByNumSerie(numSerie);
					if (selectedProducto != null){
						Modificar.setEnabled(true);
					}
				}
			}
		});
		tableProductos_1.setModel(modelProductos);
		tableProductos_1.getTableHeader().setBackground(new Color(0, 155, 124));
		tableProductos_1.getTableHeader().setForeground(Color.WHITE);
		scrollPane.setViewportView(tableProductos_1);
		scrollPane.getViewport().setBackground(Color.white);
		
		cbxTipoProducto = new JComboBox<String>();
		cbxTipoProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = -1;
				index = cbxTipoProducto.getSelectedIndex();
				if (index != -1) {
					loadTableProductos(index);
				}
			}
		});
		cbxTipoProducto.setModel(new DefaultComboBoxModel(new String[] {"Todos", "Discos Duros", "Memorias Ram", "MicroProcesadores", "Tarjetas Madres"}));
		cbxTipoProducto.setSelectedIndex(0);
		cbxTipoProducto.setOpaque(false);
		cbxTipoProducto.setIgnoreRepaint(true);
		cbxTipoProducto.setFont(new Font("Tahoma", Font.PLAIN, 17));
		cbxTipoProducto.setBorder(null);
		cbxTipoProducto.setBackground(Color.WHITE);
		cbxTipoProducto.setBounds(352, 34, 340, 33);
		contentPanel.add(cbxTipoProducto);
		
		cbxTipoProducto.setSelectedIndex(0);
		
		JPanel panel = new JPanel();
		panel.setBounds(22, 808, 10, 10);
		contentPanel.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 13, 288, 677);
		panel_1.setBackground(new Color(36, 37, 38));
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(18, 24, 258, 83);
		panel_1.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon(ListarProducto.class.getResource("/Imagenes/ProductoLabelBlanco.png")));
		
		lblNewLabel_1 = new JLabel(" Nuevo");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 160, 288, 44);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				RegProducto regProducto  = new RegProducto(null);
				regProducto.setModal(true);
				regProducto.setVisible(true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1.setBackground(new Color(0, 155, 124));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_1.setBackground(new Color(36, 37, 38));
			}
		});
		lblNewLabel_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(36, 37, 38));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setIcon(new ImageIcon(ListarProducto.class.getResource("/Imagenes/A\u00F1adirIcon.png")));
		
		Modificar = new JLabel(" Modificar");
		Modificar.setHorizontalAlignment(SwingConstants.CENTER);
		Modificar.setBounds(0, 224, 288, 44);
		panel_1.add(Modificar);
		Modificar.setEnabled(false);
		Modificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (Modificar.isEnabled()) {
					RegProducto regProducto  = new RegProducto(selectedProducto);
					regProducto.setModal(true);
					regProducto.setVisible(true);
				}
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
		Modificar.setIcon(new ImageIcon(ListarProducto.class.getResource("/Imagenes/ModificarIcon.png")));
		Modificar.setOpaque(true);
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
		lblCancelar.setIcon(new ImageIcon(ListarProducto.class.getResource("/Imagenes/CancelarIcon.png")));
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
		lblNewLabel_2.setIcon(new ImageIcon(ListarProducto.class.getResource("/Imagenes/PanelListar.png")));
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBounds(312, 8, 793, 682);
		contentPanel.add(lblNewLabel_2);
		loadTableProductos(0);
	}
	
	public static void loadTableProductos(int selection) {

		modelProductos.setRowCount(0);
		rows = new Object[modelProductos.getColumnCount()];
		
		/*for (int i = modelProductos.getRowCount() - 1; i >= 0; i--) {
			modelProductos.removeRow(i);
		}*/
		Statement selectStmt = null;
		try {
			selectStmt = Conexion.getInstance().createStatement();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectarse a la Base de Datos", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		switch (selection) {
		case 0:
			String headerProducto[] = {"# de serie", "Marca", "Modelo", "Precio", "Cantidad","Disp. min", "Categoria"};
			modelProductos.setColumnIdentifiers(headerProducto);
			
			try {
				ResultSet rs = selectStmt.executeQuery("SELECT numSerie, marca, modelo, precio, cantStock, dispMin, tipoProducto FROM producto ORDER BY numSerie ASC;");
				
				while (rs.next()) {
					rows[0] = rs.getString("numSerie");
					rows[1] = rs.getString("marca");
					rows[2] = rs.getString("modelo");
					rows[3] = rs.getString("precio");
					rows[4] = rs.getString("cantStock");
					rows[5] = rs.getString("dispMin");
					rows[6] = rs.getString("tipoProducto");
					modelProductos.addRow(rows);
				}
				rs.close();

			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al buscar productos en la BD", "Error", JOptionPane.ERROR_MESSAGE);
			}
			break;

		case 1:
			String headersHD[] = {"# de serie", "Marca", "Modelo", "Precio", "Cantidad","Socket","Capacidad"};
			modelProductos.setColumnIdentifiers(headersHD);

			try {
				ResultSet rs = selectStmt.executeQuery("SELECT numSerie, marca, modelo, precio, cantStock, socket, capacidad, unitCapacidad"
															+ " FROM producto WHERE tipoProducto = 'Disco Duro';");
				
				while (rs.next()) {
					rows[0] = rs.getString("numSerie");
					rows[1] = rs.getString("marca");
					rows[2] = rs.getString("modelo");
					rows[3] = rs.getString("precio");
					rows[4] = rs.getString("cantStock");
					rows[5] = rs.getString("socket");
					rows[6] = rs.getString("capacidad") + " " + rs.getString("unitCapacidad");
					modelProductos.addRow(rows);
				}
				rs.close();

			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al buscar discos duros en la BD", "Error", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case 2:
			String headersRam[] = {"# de serie", "Marca", "modelo", "Precio", "Cantidad","Capacidad","Tipo memoria"};
			modelProductos.setColumnIdentifiers(headersRam);

			try {
				ResultSet rs = selectStmt.executeQuery("SELECT numSerie, marca, modelo, precio, cantStock, capacidad, tipoRam, unitCapacidad"
															+ " FROM producto WHERE tipoProducto = 'Memoria Ram';");
				while (rs.next()) {
					rows[0] = rs.getString("numSerie");
					rows[1] = rs.getString("marca");
					rows[2] = rs.getString("modelo");
					rows[3] = rs.getString("precio");
					rows[4] = rs.getString("cantStock");
					rows[5] = rs.getString("capacidad") + " " + rs.getString("unitCapacidad");;
					rows[6] = rs.getString("tipoRam");
					modelProductos.addRow(rows);
				}
				rs.close();

			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al buscar memorias ram en la BD", "Error", JOptionPane.ERROR_MESSAGE);
			}
			break;
		
		case 3:
			String headersMicro[] = {"# de serie", "Marca", "Modelo", "Precio", "Cantidad","Modelo","procesamiento"};
			modelProductos.setColumnIdentifiers(headersMicro);
			
			try {
				ResultSet rs = selectStmt.executeQuery("SELECT numSerie, marca, modelo, precio, cantStock, modelo, velocidadProcesamiento, unitCapacidad"
															+ " FROM producto WHERE tipoProducto = 'Micro Procesador';");
				
				while (rs.next()) {
					rows[0] = rs.getString("numSerie");
					rows[1] = rs.getString("marca");
					rows[2] = rs.getString("modelo");
					rows[3] = rs.getString("precio");
					rows[4] = rs.getString("cantStock");
					rows[5] = rs.getString("modelo");
					rows[6] = rs.getString("velocidadProcesamiento")+ " " + rs.getString("unitCapacidad");;
					modelProductos.addRow(rows);
				}
				rs.close();

			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al buscar Microprocesador en la BD", "Error", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case 4:
			String headersMoBoard[] = {"# de serie", "Marca", "modelo", "Precio", "Cantidad","Modelo","Tipo Ram"};
			modelProductos.setColumnIdentifiers(headersMoBoard);

			try {
				ResultSet rs = selectStmt.executeQuery("SELECT numSerie, marca, modelo, precio, cantStock, modelo, tipoRam"
															+ " FROM producto WHERE tipoProducto = 'Tarjeta Madre';");
				
				while (rs.next()) {
					rows[0] = rs.getString("numSerie");
					rows[1] = rs.getString("marca");
					rows[2] = rs.getString("modelo");
					rows[3] = rs.getString("precio");
					rows[4] = rs.getString("cantStock");
					rows[5] = rs.getString("modelo");
					rows[6] = rs.getString("TipoRam");
					// rows[7] = rs.getString("TipoConexionHD");
					modelProductos.addRow(rows);
				}
				rs.close();

			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al buscar tarjeta madre en la BD", "Error", JOptionPane.ERROR_MESSAGE);
			}
			break;
		}
	}
}
