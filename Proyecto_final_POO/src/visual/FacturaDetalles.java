package visual;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Conexion;
import logico.Factura;
import logico.Producto;
import logico.Tienda;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;

@SuppressWarnings("serial")
public class FacturaDetalles extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static DefaultTableModel model;
	private static Object[] rows;
	int indexCbx = 0;
	private Color colorVerde = new Color(0, 155, 124);
	private JTable table;
	private Factura selectedFactura;
	private JPanel panelFactura;
	private JLabel lblTitle;
	private JLabel lblImprimir;
	private static JLabel lbl_PrecioTotal;
	private static JLabel lblMonto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FacturaDetalles dialog = new FacturaDetalles(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FacturaDetalles(Factura factura) {
		selectedFactura = factura;
		setUndecorated(true);
		setBounds(100, 100, 780, 630);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBackground(Color.white);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		String headerFactura[] = {"Num", "Marca", "Categoria", "cant", "Precio", "Total"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(headerFactura);
		
		panelFactura = new JPanel();
		panelFactura.setBackground(UIManager.getColor("Button.background"));
		panelFactura.setBounds(12, 46, 756, 571);
		contentPanel.add(panelFactura);
		panelFactura.setLayout(null);
		
		lblImprimir = new JLabel("");
		lblImprimir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblImprimir.setIcon(new ImageIcon(FacturaDetalles.class.getResource("/Imagenes/pdfIcon.png")));
		lblImprimir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblImprimir.setVisible(false);
				
				try {
					   ImageIO.write(createImage(panelFactura), "png", new File("src/Facturas/factura"+selectedFactura.getMiCliente().getCedula()+".png"));
					} catch (IOException eio) {
					   System.out.println("Error de escritura");
					}
								
				Document documento = new Document();
				try {
					PdfWriter.getInstance(documento, new FileOutputStream("src/Facturas/factura"+selectedFactura.getMiCliente().getCedula()+".pdf"));
					Image imagen = Image.getInstance("src/Facturas/factura"+selectedFactura.getMiCliente().getCedula()+".png");
					documento.open();
					imagen.scaleToFit(500, 1000);
					imagen.setAlignment(Chunk.ALIGN_CENTER);
					
					Paragraph parrafo = new Paragraph();
					parrafo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
					parrafo.add("\n\n");
					documento.add(parrafo);
					documento.add(imagen);
					Paragraph parrafo2 = new Paragraph();
					parrafo2.setAlignment(Paragraph.ALIGN_JUSTIFIED);
					parrafo2.add("\n\nGracias Por preferirnos Tecno C\n\n");
					parrafo2.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));
					documento.add(parrafo2);
					documento.close();
				} catch (Exception i) {
					
				}
				lblImprimir.setVisible(true);
				
				try {
				     File path = new File ("src/Facturas/factura"+selectedFactura.getMiCliente().getCedula()+".pdf");
				     Desktop.getDesktop().open(path);
				}catch (IOException ex) {
				     ex.printStackTrace();
				}
			}
		});
		lblImprimir.setOpaque(true);
		lblImprimir.setHorizontalAlignment(SwingConstants.CENTER);
		lblImprimir.setForeground(Color.WHITE);
		lblImprimir.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblImprimir.setBounds(465, 9, 79, 51);
		lblImprimir.setBackground(new Color(0, 155, 124));
		panelFactura.add(lblImprimir);
		
		lblMonto = new JLabel(""+selectedFactura.getMisProductos().size());
		lblMonto.setForeground(Color.BLACK);
		lblMonto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMonto.setBounds(525, 308, 219, 28);
		panelFactura.add(lblMonto);
		
		JLabel lblPrecioTotal = new JLabel("Cantidad Articulos");
		lblPrecioTotal.setForeground(Color.WHITE);
		lblPrecioTotal.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblPrecioTotal.setBounds(516, 270, 230, 28);
		panelFactura.add(lblPrecioTotal);
		
		JLabel lblProductos = new JLabel("Articulos");
		lblProductos.setForeground(Color.WHITE);
		lblProductos.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblProductos.setBounds(22, 270, 187, 28);
		panelFactura.add(lblProductos);
		
		JLabel lbldireccionCliente = new JLabel(selectedFactura.getMiCliente().getDireccion());
		lbldireccionCliente.setForeground(Color.BLACK);
		lbldireccionCliente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbldireccionCliente.setBounds(22, 200, 300, 28);
		panelFactura.add(lbldireccionCliente);
		
		JLabel lbltelefonoCliente = new JLabel(selectedFactura.getMiCliente().getTelefono());
		lbltelefonoCliente.setForeground(Color.BLACK);
		lbltelefonoCliente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbltelefonoCliente.setBounds(22, 160, 300, 28);
		panelFactura.add(lbltelefonoCliente);
		
		JLabel lblFacturarA = new JLabel("Facturar a");
		lblFacturarA.setForeground(Color.WHITE);
		lblFacturarA.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblFacturarA.setBounds(23, 75, 187, 28);
		panelFactura.add(lblFacturarA);
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setForeground(Color.WHITE);
		lblFecha.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblFecha.setBounds(567, 75, 187, 28);
		panelFactura.add(lblFecha);
		
		JLabel lblFactura = new JLabel("Factura#");
		lblFactura.setForeground(Color.WHITE);
		lblFactura.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblFactura.setBounds(354, 75, 137, 28);
		panelFactura.add(lblFactura);
		
		lblTitle = new JLabel("FACTURA");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblTitle.setForeground(colorVerde);
		lblTitle.setBounds(556, 13, 198, 39);
		panelFactura.add(lblTitle);
		
		JLabel label_1 = new JLabel("");
		label_1.setOpaque(true);
		label_1.setForeground(new Color(0, 155, 124));
		label_1.setBackground(colorVerde);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 40));
		label_1.setBounds(344, 75, 400, 28);
		panelFactura.add(label_1);
		
		JLabel lblDato = new JLabel(selectedFactura.getCodigo());
		lblDato.setForeground(Color.BLACK);
		lblDato.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDato.setBounds(354, 121, 194, 28);
		panelFactura.add(lblDato);
		
		JLabel lblFecha_1 = new JLabel(new SimpleDateFormat("dd-MM-yyyy").format(selectedFactura.getFecha()));
		lblFecha_1.setForeground(Color.BLACK);
		lblFecha_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFecha_1.setBounds(557, 121, 187, 28);
		panelFactura.add(lblFecha_1);
		
		JLabel lblCliente = new JLabel("Vendedor");
		lblCliente.setForeground(Color.WHITE);
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblCliente.setBounds(354, 162, 190, 28);
		panelFactura.add(lblCliente);
		
		JLabel label_3 = new JLabel("");
		label_3.setOpaque(true);
		label_3.setForeground(new Color(0, 155, 124));
		label_3.setFont(new Font("Tahoma", Font.BOLD, 40));
		label_3.setBackground(new Color(0, 155, 124));
		label_3.setBounds(344, 162, 400, 28);
		panelFactura.add(label_3);
		
		JLabel lblCedula = new JLabel(selectedFactura.getMiVendedor().getNombre());
		lblCedula.setForeground(Color.BLACK);
		lblCedula.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCedula.setBounds(354, 209, 392, 28);
		panelFactura.add(lblCedula);
		
		JLabel label_4 = new JLabel("");
		label_4.setOpaque(true);
		label_4.setBackground(Color.WHITE);
		label_4.setForeground(Color.BLACK);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 23));
		label_4.setBounds(344, 116, 400, 39);
		panelFactura.add(label_4);
		
		JLabel label_5 = new JLabel("");
		label_5.setOpaque(true);
		label_5.setForeground(Color.BLACK);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 23));
		label_5.setBackground(Color.WHITE);
		label_5.setBounds(344, 203, 400, 39);
		panelFactura.add(label_5);
		
		JLabel label_8 = new JLabel("");
		label_8.setOpaque(true);
		label_8.setForeground(new Color(0, 155, 124));
		label_8.setFont(new Font("Tahoma", Font.BOLD, 40));
		label_8.setBackground(new Color(0, 155, 124));
		label_8.setBounds(12, 75, 320, 28);
		panelFactura.add(label_8);
		
		JLabel lblnombreCliente = new JLabel(selectedFactura.getMiCliente().getNombre());
		lblnombreCliente.setForeground(Color.BLACK);
		lblnombreCliente.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblnombreCliente.setBounds(22, 121, 300, 28);
		panelFactura.add(lblnombreCliente);
		
		JLabel label_13 = new JLabel("");
		label_13.setOpaque(true);
		label_13.setForeground(Color.BLACK);
		label_13.setFont(new Font("Tahoma", Font.BOLD, 23));
		label_13.setBackground(Color.WHITE);
		label_13.setBounds(12, 116, 320, 126);
		panelFactura.add(label_13);
		
		JLabel label_2 = new JLabel("");
		label_2.setOpaque(true);
		label_2.setForeground(new Color(0, 155, 124));
		label_2.setFont(new Font("Tahoma", Font.BOLD, 40));
		label_2.setBackground(new Color(0, 155, 124));
		label_2.setBounds(12, 270, 479, 28);
		panelFactura.add(label_2);
		
		JLabel label_7 = new JLabel("");
		label_7.setOpaque(true);
		label_7.setForeground(new Color(0, 155, 124));
		label_7.setFont(new Font("Tahoma", Font.BOLD, 40));
		label_7.setBackground(new Color(0, 155, 124));
		label_7.setBounds(510, 270, 234, 28);
		panelFactura.add(label_7);
		
		JLabel label_9 = new JLabel("");
		label_9.setOpaque(true);
		label_9.setForeground(Color.BLACK);
		label_9.setFont(new Font("Tahoma", Font.BOLD, 23));
		label_9.setBackground(Color.WHITE);
		label_9.setBounds(510, 303, 234, 38);
		panelFactura.add(label_9);
		
		JLabel label_10 = new JLabel("Precio total");
		label_10.setForeground(Color.WHITE);
		label_10.setFont(new Font("Tahoma", Font.BOLD, 21));
		label_10.setBounds(516, 480, 187, 28);
		panelFactura.add(label_10);
		
		JLabel label_11 = new JLabel("");
		label_11.setOpaque(true);
		label_11.setForeground(new Color(0, 155, 124));
		label_11.setFont(new Font("Tahoma", Font.BOLD, 40));
		label_11.setBackground(new Color(0, 155, 124));
		label_11.setBounds(510, 480, 234, 28);
		panelFactura.add(label_11);
		
		// JLabel label_12 = new JLabel("" + selectedFactura.getPrecioTotal());
		if (selectedFactura != null) {
			lbl_PrecioTotal = new JLabel("$"+String.valueOf(selectedFactura.getPrecioTotal()));
		}else
			lbl_PrecioTotal = new JLabel("$0.0");
		
		lbl_PrecioTotal.setForeground(Color.BLACK);
		lbl_PrecioTotal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_PrecioTotal.setBounds(520, 525, 219, 28);
		panelFactura.add(lbl_PrecioTotal);
		
		JLabel label_14 = new JLabel("");
		label_14.setOpaque(true);
		label_14.setForeground(Color.BLACK);
		label_14.setFont(new Font("Tahoma", Font.BOLD, 23));
		label_14.setBackground(Color.WHITE);
		label_14.setBounds(510, 520, 234, 38);
		panelFactura.add(label_14);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 303, 479, 255);
		panelFactura.add(scrollPane);
		
		table = new JTable();
		table.setModel(model);
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);
		
		JLabel lblTecno = new JLabel("Tecno");
		lblTecno.setForeground(Color.DARK_GRAY);
		lblTecno.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblTecno.setBounds(12, 13, 121, 39);
		panelFactura.add(lblTecno);
		
		JLabel lblC = new JLabel("C");
		lblC.setForeground(new Color(0, 155, 124));
		lblC.setFont(new Font("Tahoma", Font.ITALIC, 50));
		lblC.setBounds(138, 7, 59, 51);
		panelFactura.add(lblC);
		scrollPane.getViewport().setBackground(Color.white);
		
		final JLabel label = new JLabel("X");
		label.setBounds(745, 0, 23, 49);
		contentPanel.add(label);
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

		loadTableFactura(selectedFactura);
	}
	
	public BufferedImage createImage(JPanel panel) {

	    int w = panel.getWidth();
	    int h = panel.getHeight();
	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = bi.createGraphics();
	    panel.paint(g);
	    g.dispose();
	    return bi;
	}
	
	public static void loadTableFactura(Factura selectedFactura) {
		
		model.setRowCount(0);
		rows = new Object[model.getColumnCount()];
		float tmpPrecioTotal = 0;
		Statement selectStmt = null;
		
		try {
			selectStmt = Conexion.getInstance().createStatement();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectarse a la Base de Datos", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		String headerDetalles[] = {"Num. de Serie", "Modelo", "Tipo de Producto", "Cantidad ", "Precio","Total"};
		model.setColumnIdentifiers(headerDetalles);
		
		try {
			String SQL = ("SELECT producto.numSerie, producto.marca, producto.modelo, producto.tipoProducto, detalle_factura_producto.cantidad, producto.precio "
					+ "FROM detalle_factura_producto "
					+ "JOIN producto on producto.numSerie = detalle_factura_producto.numSerie "
					+ "WHERE detalle_factura_producto.codigoFactura = ?;");
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setString(1, selectedFactura.getCodigo());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				rows[0] = rs.getString("numSerie");
				rows[1] = rs.getString("marca")+ " " + rs.getString("modelo");
				rows[2] = rs.getString("tipoProducto");
				rows[3] = rs.getInt("cantidad");
				rows[4] = rs.getFloat("precio");
				// tmpPrecioTotal += rs.getFloat("precio") * rs.getInt("cantidad");
				rows[5] = rs.getFloat("precio") * rs.getInt("cantidad");
				
				model.addRow(rows);
			}
			
			String SQL2 = ("SELECT * "
					+ "FROM productos_vendido_combo "
					+ "WHERE codigoFactura = ?;");
			PreparedStatement pstmt2 = Conexion.getInstance().prepareStatement(SQL2);
			pstmt2.setString(1, selectedFactura.getCodigo());
			ResultSet rs2 = pstmt2.executeQuery();
			
			while (rs2.next()) {
				Producto productoTmp = Tienda.getInstance().buscarProductoByNumSerie(rs2.getString("numSerie"));
				rows[0] = productoTmp.getNumSerie();
				rows[1] = productoTmp.getMarca() + " " + productoTmp.getModelo();
				rows[2] = productoTmp.getTipoProducto();
				rows[3] = rs2.getInt("cantidad");
				rows[4] = productoTmp.getPrecio() - (productoTmp.getPrecio() * ((rs2.getFloat("descuentoPorProducto") / 100)));
				rows[5] = (productoTmp.getPrecio() - (productoTmp.getPrecio() * ((rs2.getFloat("descuentoPorProducto") / 100)))) * rs2.getInt("cantidad");
				// tmpPrecioTotal += (productoTmp.getPrecio() - (productoTmp.getPrecio() * ((rs2.getFloat("descuentoPorProducto") / 100)))) * rs2.getInt("cantidad");
				model.addRow(rows);
				selectedFactura.getMisProductos().add(productoTmp);
			}
			lbl_PrecioTotal.setText("$"+Tienda.getInstance().buscarFacturaByCodigo(selectedFactura.getCodigo()).getPrecioTotal());
			lblMonto.setText("" + selectedFactura.getMisProductos().size());
			
			rs.close();
			pstmt.close();
			rs2.close();
			pstmt2.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al buscar productos en la BD", "Error", JOptionPane.ERROR_MESSAGE);
		}	
	}
}