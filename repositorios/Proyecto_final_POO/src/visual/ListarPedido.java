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
import java.text.SimpleDateFormat;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class ListarPedido extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static DefaultTableModel modelPedido;
	private static Object[] rows;
	private JLabel Confirmar;
	private JTable tablePedido;
	private OrdenCompra selectedOrdenCompra = null;
	int indexCbx = 0;
	private JComboBox<String> cbxTipoPedido;
	private JLabel lblAddDistribuidor;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarPedido dialog = new ListarPedido();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarPedido() {
		setUndecorated(true);
		setBounds(100, 100, 1117, 703);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBackground(Color.white);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		cbxTipoPedido = new JComboBox<String>();
		cbxTipoPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				indexCbx = cbxTipoPedido.getSelectedIndex();
				if (indexCbx != -1) {
					loadTablePedido(indexCbx);
				}
			}
		});
		cbxTipoPedido.setModel(new DefaultComboBoxModel<String>(new String[] {"<Todos>", "Procesados", "Sin procesar"}));
		cbxTipoPedido.setFont(new Font("Tahoma", Font.PLAIN, 17));
		cbxTipoPedido.setBounds(352, 63, 187, 30);
		contentPanel.add(cbxTipoPedido);
		
		JScrollPane scrollPanePedido = new JScrollPane();
		scrollPanePedido.setBounds(352, 104, 720, 546);
		contentPanel.add(scrollPanePedido);
		
		String header[] = {"Codigo", "Distribuidor", "Producto", "Cantidad", "Precio total", "Fecha de Solicitud", "Procesado"};
		modelPedido = new DefaultTableModel();
		modelPedido.setColumnIdentifiers(header);
				
		tablePedido = new JTable();
		tablePedido.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = -1;
				index = tablePedido.getSelectedRow();
				if(index != -1) {
					int codigo = Integer.parseInt(modelPedido.getValueAt(index, 0).toString());
					selectedOrdenCompra = buscarOrdenDeCompraByCod(codigo);
					if (selectedOrdenCompra != null){
						Confirmar.setEnabled(true);
					}
				}
			}

		});
		tablePedido.setModel(modelPedido);
		tablePedido.getTableHeader().setBackground(new Color(0, 155, 124));
		tablePedido.getTableHeader().setForeground(Color.WHITE);
		scrollPanePedido.setViewportView(tablePedido);
		scrollPanePedido.setViewportView(tablePedido);
		scrollPanePedido.getViewport().setBackground(Color.white);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 13, 288, 677);
		panel_1.setBackground(new Color(36, 37, 38));
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(48, 26, 209, 83);
		panel_1.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon(ListarPedido.class.getResource("/Imagenes/PedidosLabelBlanco.png")));

		Confirmar = new JLabel("Confirmar");
		Confirmar.setHorizontalAlignment(SwingConstants.CENTER);
		Confirmar.setBounds(0, 158, 288, 44);
		panel_1.add(Confirmar);
		Confirmar.setEnabled(false);
		Confirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (selectedOrdenCompra != null && Confirmar.isEnabled()) {
					if (selectedOrdenCompra.isProcesada() == false && selectedOrdenCompra.getProducto() != null) {
						// selectedOrdenCompra.setProducto(buscarProductoByNumSerie());
						ConfirmarPedido confirmarPedido = new ConfirmarPedido(selectedOrdenCompra);
						confirmarPedido.setModal(true);
						confirmarPedido.setVisible(true);
						Confirmar.setEnabled(false);
						loadTablePedido(indexCbx);
					}
					else {
						JOptionPane.showMessageDialog(null, "Esta orden de compra ya esta procesada", "Confirmar Orden de Compra", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				Confirmar.setBackground(new Color(0, 155, 124));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Confirmar.setBackground(new Color(36, 37, 38));
			}
		});
		Confirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Confirmar.setIcon(new ImageIcon(ListarPedido.class.getResource("/Imagenes/ConfirmarIcon.png")));
		Confirmar.setOpaque(true);
		Confirmar.setForeground(Color.WHITE);
		Confirmar.setFont(new Font("Tahoma", Font.PLAIN, 34));
		Confirmar.setBackground(new Color(36, 37, 38));
		
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
		
	    lblAddDistribuidor = new JLabel("Distribuidor");
	    lblAddDistribuidor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAddDistribuidor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				RegDistribuidor regDistribuidor = new RegDistribuidor();
				regDistribuidor.setModal(true);
				regDistribuidor.setVisible(true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblAddDistribuidor.setBackground(new Color(0, 155, 124));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblAddDistribuidor.setBackground(new Color(36, 37, 38));
			}
		});
		lblAddDistribuidor.setIcon(new ImageIcon(ListarPedido.class.getResource("/Imagenes/A\u00F1adirIcon.png")));
		lblAddDistribuidor.setOpaque(true);
		lblAddDistribuidor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddDistribuidor.setForeground(Color.WHITE);
		lblAddDistribuidor.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblAddDistribuidor.setBackground(new Color(36, 37, 38));
		lblAddDistribuidor.setBounds(0, 208, 288, 44);
		panel_1.add(lblAddDistribuidor);
		
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
		
		// Tienda.getInstance().crearOrdenesCompra();
		loadTablePedido(0);
	}
	
	public static void loadTablePedido(int selection) {
		
		modelPedido.setRowCount(0);
		rows = new Object[modelPedido.getColumnCount()];
		ResultSet rs = null;
		String nombreDistribuidor = null;

		switch (selection) {
		case 0:
		try {
			String SQL = "SELECT * FROM orden_compra;";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String SQL2 = "SELECT nombre FROM distribuidor WHERE distribuidor.idDistribuidor = ?;";
				PreparedStatement pstmt2 = Conexion.getInstance().prepareStatement(SQL2);

				int idDistribuidor = rs.getInt("idDistribuidor");
				pstmt2.setInt(1, idDistribuidor);

				ResultSet rs2 = pstmt2.executeQuery();
				
				while(rs2.next()){
					nombreDistribuidor = rs2.getString("nombre");
				}

				rows[0] = rs.getString("codigo");
				rows[1] =  nombreDistribuidor;
				rows[2] = rs.getString("numSerie");
				rows[3] = rs.getString("cantidad");
				rows[4] = rs.getString("montoTotal");
				rows[5] = rs.getDate("fechaSolicitud");
				rows[6] = rs.getString("procesada");
				modelPedido.addRow(rows);
				pstmt2.close();
				rs2.close();
				nombreDistribuidor = null;
			}
			
			rs.close();
			pstmt.close();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
			break;
			
		case 1:
			try {
				String SQL = "SELECT * FROM orden_compra WHERE procesada = 1;";
				PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					String SQL2 = "SELECT nombre FROM distribuidor WHERE distribuidor.idDistribuidor = ?;";
					PreparedStatement pstmt2 = Conexion.getInstance().prepareStatement(SQL2);
					int idDistribuidor = rs.getInt("idDistribuidor");
					pstmt2.setInt(1, idDistribuidor);
					ResultSet rs2 = pstmt2.executeQuery();
					
					while(rs2.next()){
						nombreDistribuidor = rs2.getString("nombre");
					}

					rows[0] = rs.getString("codigo");
					rows[1] =  nombreDistribuidor;
					rows[2] = rs.getString("numSerie");
					rows[3] = rs.getString("cantidad");
					rows[4] = rs.getString("montoTotal");
					rows[5] = rs.getDate("fechaSolicitud");
					rows[6] = rs.getString("procesada");
					modelPedido.addRow(rows);
					pstmt2.close();
					rs2.close();
					nombreDistribuidor = null;
				}
				
				rs.close();
				pstmt.close();
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}				
			break;
			
		case 2:
		try {
			String SQL = "SELECT * FROM orden_compra WHERE procesada = 0;";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String SQL2 = "SELECT nombre FROM distribuidor WHERE distribuidor.idDistribuidor = ?;";
				PreparedStatement pstmt2 = Conexion.getInstance().prepareStatement(SQL2);
				int idDistribuidor = rs.getInt("idDistribuidor");
				pstmt2.setInt(1, idDistribuidor);
				ResultSet rs2 = pstmt2.executeQuery();
				
				while(rs2.next()){
					nombreDistribuidor = rs2.getString("nombre");
				}

				rows[0] = rs.getString("codigo");
				rows[1] =  nombreDistribuidor;
				rows[2] = rs.getString("numSerie");
				rows[3] = rs.getString("cantidad");
				rows[4] = rs.getString("montoTotal");
				rows[5] = rs.getDate("fechaSolicitud");
				rows[6] = rs.getString("procesada");
				modelPedido.addRow(rows);
				pstmt2.close();
				rs2.close();
				nombreDistribuidor = null;
			}
			
			rs.close();
			pstmt.close();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
			break;
		}
		
	}

	private OrdenCompra buscarOrdenDeCompraByCod(int codigo) {
		ResultSet rs = null;
		OrdenCompra ordenAux = null;
		
		try {
			String SQL = "SELECT * FROM orden_compra WHERE codigo = ?;";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setInt(1, codigo);
			rs = pstmt.executeQuery();
			rs.next();
			
			Producto productoCombo = Tienda.getInstance().buscarProductoByNumSerie(rs.getString("numSerie"));
			if (productoCombo != null){
				ordenAux = new OrdenCompra(rs.getInt("codigo"), productoCombo);
				int procesada = rs.getInt("procesada");
				if (procesada == 1)
					ordenAux.setProcesada(true );
				else
					ordenAux.setProcesada(false );

			}
			else
				JOptionPane.showMessageDialog(null, "No se encontro el producto de este combo.", "Error buscando el producto", JOptionPane.ERROR_MESSAGE);

			rs.close();
			pstmt.close();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
		return ordenAux;
	}
	
}
