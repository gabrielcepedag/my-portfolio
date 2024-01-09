package visual;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Conexion;
import logico.DiscoDuro;
import logico.MemoriaRam;
import logico.MicroProcesador;
import logico.MotherBoard;
import logico.OrdenCompra;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.channels.NonReadableChannelException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.sql.rowset.JoinRowSet;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class ListarHistorialPrecio extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static DefaultTableModel modelPedido;
	private static Object[] rows;
	private JTable tablePedido;
	int indexCbx = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarHistorialPrecio dialog = new ListarHistorialPrecio();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarHistorialPrecio() {
		setUndecorated(true);
		setBounds(100, 100, 858, 703);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBackground(Color.white);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ListarHistorialPrecio.class.getResource("/Imagenes/listarHistorialTextoLbl.png")));
		lblNewLabel.setBounds(65, 40, 713, 63);
		contentPanel.add(lblNewLabel);
		
		JScrollPane scrollPanePedido = new JScrollPane();
		scrollPanePedido.setBounds(71, 113, 707, 534);
		contentPanel.add(scrollPanePedido);
		
		String header[] = {"numHistorial", "Fecha", "Precio Antiguo", "Precio Nuevo", "numSerie","Producto", "Marca"};
		modelPedido = new DefaultTableModel();
		modelPedido.setColumnIdentifiers(header);
				
		tablePedido = new JTable();
		tablePedido.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = -1;
				index = tablePedido.getSelectedRow();
				if(index != -1) {
				}
			}

		});
		tablePedido.setModel(modelPedido);
		tablePedido.getTableHeader().setBackground(new Color(0, 155, 124));
		tablePedido.getTableHeader().setForeground(Color.WHITE);
		scrollPanePedido.setViewportView(tablePedido);
		scrollPanePedido.setViewportView(tablePedido);
		scrollPanePedido.getViewport().setBackground(Color.white);
		
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
		label.setBounds(778, 10, 57, 55);
		contentPanel.add(label);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBackground(Color.WHITE);
		lblNewLabel_2.setIcon(new ImageIcon(ListarCliente.class.getResource("/Imagenes/PanelListar.png")));
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBounds(31, 10, 793, 693);
		contentPanel.add(lblNewLabel_2);
		
		// Tienda.getInstance().crearOrdenesCompra();
		loadTableHistorial();
	}
	
	public static void loadTableHistorial() {
		
		modelPedido.setRowCount(0);
		rows = new Object[modelPedido.getColumnCount()];
		ResultSet rs = null;

		try {
			String SQL = "SELECT * FROM historial_cambio_precio order by fechaCambio;";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {

				Statement consulta = Conexion.getInstance().createStatement();
				int historialId = rs.getInt("numHistorial");
				ResultSet datosProducto = consulta.executeQuery("select producto.tipoProducto, producto.marca from producto, historial_cambio_precio where historial_cambio_precio.numSerie = producto.numSerie and historial_cambio_precio.numHistorial = " + historialId 
				+ "");
				datosProducto.next();
				rows[0] = rs.getString("numHistorial");
				rows[1] = rs.getString("fechaCambio");
				rows[2] = rs.getString("precioAntiguo");
				rows[3] = rs.getString("precioNuevo");	
				rows[4] = rs.getString("numSerie");
				rows[5] = datosProducto.getString("tipoProducto");
				rows[6] = datosProducto.getString("marca");
				modelPedido.addRow(rows);
			}
			
			rs.close();
			pstmt.close();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
}
