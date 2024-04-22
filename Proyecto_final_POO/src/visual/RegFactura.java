package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Cliente;
import logico.Combo;
import logico.Conexion;
import logico.DiscoDuro;
import logico.Factura;
import logico.MemoriaRam;
import logico.MicroProcesador;
import logico.MotherBoard;
import logico.Producto;
import logico.Tienda;
import logico.Vendedor;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Cursor;
import javax.swing.JTextField;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class RegFactura extends JDialog {

	private JPanel contentPane;
	private JPanel panelDatosClientes;
	private JPanel panelCombos;
	private JPanel panelProductos;
	private JTextField txtNombre;
	private JTextField txtDireccion;
	private JTextField txtCedula;
	private JTextField txtPrecioTotal;
	private JTextField txtCodigo;
	private JTextField txtTelefono;
	private JRadioButton btnAddProducto;
	private JRadioButton btnAddCombo;
	private JRadioButton rdbtnFacturaACredito;
	private JLabel lblRegCliente;
	private JLabel lblRegistrar;
	private JLabel lblBuscar;
	private JLabel lblArribaCombo;
	private JLabel lblAbajoCombo;
	private JLabel lblAbajoProductos;
	private JLabel lblArribaProductos;
	private JList<String> listComboDisp;
	private JList<String> listComboSel;
	private JList<String> listProductosDisp;
	private JList<String> listProductosSel;
	private DefaultListModel<String> listModelComboDisp;
	private DefaultListModel<String> listModelComboSel;
	private DefaultListModel<String> listModelProductosDisp;
	private DefaultListModel<String> listModelProductosSel;
	private ArrayList<Combo> combosSeleccionados= new ArrayList<Combo>();
	private ArrayList<Producto> productosSeleccionados= new ArrayList<Producto>();
	private ArrayList<Combo> misCombos= new ArrayList<Combo>();
	private ArrayList<Producto> misProductos= new ArrayList<Producto>();
	private Cliente auxCliente = null;
	private Vendedor auxVendedor = null;
	private Factura factura = null;
	private boolean nuevoCliente = false;
	private JComboBox cbxMunicipio;
	private JComboBox cbxProvincia;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegFactura dialog = new RegFactura();
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
	public RegFactura() {
		setUndecorated(true);
		setBounds(100, 100, 1114, 787);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(36, 37, 38));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		panelCombos = new JPanel();
		panelCombos.setVisible(false);
		panelCombos.setLayout(null);
		panelCombos.setBackground(Color.WHITE);
		panelCombos.setBounds(630, 67, 465, 645);
		panel.add(panelCombos);
				
		lblArribaCombo = new JLabel("\u2191\u2191");
		lblArribaCombo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listComboSel.getSelectedValue() != null) {
					String aux = listComboSel.getSelectedValue().toString();
					Combo auxCombo = Tienda.getInstance().buscarComboByCod(aux.substring(aux.indexOf(':')+1, aux.length()));
					combosSeleccionados.remove(auxCombo);
					listModelComboSel.remove(listComboSel.getSelectedIndex());
					listModelComboDisp.addElement(aux);
					
					for (Producto producto : auxCombo.getMisProductos()) {
						aumentarStock(producto);
					}
					combosSeleccionados.remove(auxCombo);
					//combosSeleccionados.clear();
					
					
					
					lblArribaCombo.setBackground(new Color(0, 85, 70));
					lblArribaCombo.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
					txtPrecioTotal.setText("RD$ "+calcTotal());
				}else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar el Combo que desea quitar de la factura.", "Informacion", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		lblArribaCombo.setOpaque(true);
		lblArribaCombo.setHorizontalAlignment(SwingConstants.CENTER);
		lblArribaCombo.setForeground(Color.WHITE);
		lblArribaCombo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblArribaCombo.setBackground(new Color(0, 85, 70));
		lblArribaCombo.setBounds(155, 288, 74, 45);
		panelCombos.add(lblArribaCombo);
		
		lblAbajoCombo = new JLabel("\u2193\u2193");
		lblAbajoCombo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!txtCedula.getText().isEmpty()) {
					if(listComboDisp.getSelectedValue() != null) {
						String aux = listComboDisp.getSelectedValue().toString();
						Combo auxCombo = Tienda.getInstance().buscarComboByCod(aux.substring(aux.indexOf(':')+1, aux.length()));
						listModelComboSel.addElement(aux);

						for (Producto producto : auxCombo.getMisProductos()) {
							reducirStock(producto);
						}
						
						listModelComboDisp.removeElement(aux);
						combosSeleccionados.add(auxCombo);
						txtPrecioTotal.setText("RD$ "+calcTotal());
						//loadProductosDisponibles();
						
						lblAbajoCombo.setBackground(new Color(0, 85, 70));
						lblAbajoCombo.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));						
					}else {
						JOptionPane.showMessageDialog(null, "Debe seleccionar el Combo que desea agregar a la factura.", "Informacion", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Debe especificar una cedula para seleccionar un Combo.", "Informacion", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		lblAbajoCombo.setOpaque(true);
		lblAbajoCombo.setHorizontalAlignment(SwingConstants.CENTER);
		lblAbajoCombo.setForeground(Color.WHITE);
		lblAbajoCombo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAbajoCombo.setBackground(new Color(0, 85, 70));
		lblAbajoCombo.setBounds(241, 288, 74, 45);
		panelCombos.add(lblAbajoCombo);
		
		JLabel lblCombosDisponibles = new JLabel("Combos disponibles:");
		lblCombosDisponibles.setForeground(Color.BLACK);
		lblCombosDisponibles.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCombosDisponibles.setBounds(12, 0, 190, 55);
		panelCombos.add(lblCombosDisponibles);
		
		JScrollPane scrollPaneCombosDisp = new JScrollPane();
		scrollPaneCombosDisp.setBounds(12, 51, 441, 227);
		panelCombos.add(scrollPaneCombosDisp);
		
		listComboDisp = new JList<String>();
		listComboDisp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int index = -1;
				index = listComboDisp.getSelectedIndex();
				if (index != -1) {
					lblAbajoCombo.setBackground(new Color(0, 155, 124));
					lblAbajoCombo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			}
		});
		listModelComboDisp = new DefaultListModel<String>();
		listComboDisp.setModel(listModelComboDisp);
		listComboDisp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		listComboDisp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneCombosDisp.setViewportView(listComboDisp);
		
		JLabel lblCombosElejidos = new JLabel("Seleccionados:");
		lblCombosElejidos.setForeground(Color.BLACK);
		lblCombosElejidos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCombosElejidos.setBounds(10, 293, 140, 55);
		panelCombos.add(lblCombosElejidos);
		
		JScrollPane scrollPaneCombosSel = new JScrollPane();
		scrollPaneCombosSel.setBounds(12, 358, 441, 245);
		panelCombos.add(scrollPaneCombosSel);
		
		listComboSel = new JList<String>();
		listComboSel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int index = -1;
				index = listComboSel.getSelectedIndex();
				if (index != -1) {
					lblArribaCombo.setBackground(new Color(0, 155, 124));
					lblArribaCombo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			}
		});
		listModelComboSel = new DefaultListModel<String>();
		listComboSel.setModel(listModelComboSel);
		listComboSel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		listComboSel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneCombosSel.setViewportView(listComboSel);
		
		JLabel lblCancelar = new JLabel("Cancelar");
		lblCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblCancelar.setBounds(630, 722, 225, 45);
		panel.add(lblCancelar);
		lblCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!listModelComboSel.isEmpty()) {
					for (Combo combo : combosSeleccionados) {
						for (Producto producto : combo.getMisProductos()) {
							aumentarStock(producto);
						}
					}
				}
				if (!listModelProductosSel.isEmpty()) {
					for (Producto producto : productosSeleccionados) {
						aumentarStock(producto);
					}
				}
				// if(nuevoCliente) {
				// 	int opcion = JOptionPane.showConfirmDialog(null,"Tambien desea eliminar la informacion del nuevo cliente registrado?", "Facturacion Cancelada", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				//     if(opcion == JOptionPane.YES_OPTION){
				//     	Tienda.getInstance().eliminarCliente(auxCliente);
				//     }
				// }
				dispose();
			}
		});
		lblCancelar.setForeground(Color.WHITE);
		lblCancelar.setBackground(new Color(0, 155, 124));
		lblCancelar.setOpaque(true);
		lblCancelar.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancelar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		lblRegistrar = new JLabel("Registrar");
		lblRegistrar.setEnabled(false);
		lblRegistrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listModelComboSel.isEmpty() && listModelProductosSel.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un (1) producto o combo.", "Informacion", JOptionPane.WARNING_MESSAGE);
				}else {
					if(auxCliente == null) {
						auxCliente = new Cliente(txtCedula.getText(), txtNombre.getText(), txtDireccion.getText(), txtTelefono.getText());

						try {
							String SQL = "EXEC crea_persona_y_cliente ?, ?, 31, ?, ?, ?;";
							PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
							pstmt.setString(1, auxCliente.getCedula());
							pstmt.setString(2, auxCliente.getNombre());
							//pstmt.setInt(3, cbxMunicipio);  //FALTA PROGRAMAR MUNICIPIO
							pstmt.setString(3, auxCliente.getDireccion());
							pstmt.setString(4, auxCliente.getTelefono());
							pstmt.setInt(5, 0);
							pstmt.execute();

						  	pstmt.close();	
						}
						catch (Exception e1) {
						  e1.printStackTrace();
						}
					}
					auxVendedor = (Vendedor) Tienda.getInstance().getLoginUserEmpleado();
					ArrayList<Producto> auxListCompra = new ArrayList<Producto>();

					if (!listModelProductosSel.isEmpty()) {
						for (Producto producto : productosSeleccionados) {
							auxListCompra.add(producto);
						}						
					}
					if (!listModelComboSel.isEmpty()) {
						for (Combo combo : combosSeleccionados) {
							for (Producto producto : combo.getMisProductos()) {
								auxListCompra.add(producto);
							}
							actualizarCombo(combo);
						}
					}
					boolean esACredito = false;
					if(rdbtnFacturaACredito.isSelected()) {
						esACredito = true;
					}
					Date fecha = new Date();
					
					//Factura(String codigo, Vendedor miVendedor, Cliente miCliente, ArrayList<Producto> misProductos, Date fecha) {
					factura = new Factura(txtCodigo.getText(), auxVendedor, auxCliente, auxListCompra, fecha);
					factura.setACredito(esACredito);
					factura.setPrecioTotal(calcTotal());
					
					System.out.println(factura.getPrecioTotal());
					
					if ( (crearFactura(auxVendedor, auxCliente, auxListCompra, calcTotal(), esACredito)) ){
						JOptionPane.showMessageDialog(null, "La factura ha sido registrada satisfactoriamente!", "Registro de factura", JOptionPane.INFORMATION_MESSAGE);
						
						actualizarVendedor(auxVendedor);
						actualizarCliente(auxCliente);
					}else{
						JOptionPane.showMessageDialog(null, "La factura no pudo ser registrada!", "Registro de factura", JOptionPane.ERROR_MESSAGE);
					}

					/*
					auxVendedor.setTotalVendido(factura.getPrecioTotal());
					auxVendedor.setComision((float) (factura.getPrecioTotal()*0.05)); //(EJEMPLO: Para una tasa fija de 5% comision)///////////////////////////////////
					auxCliente.setCantCompras(auxCliente.getCantCompras() + 1);			
					*/
					cleanCliente();
					nuevoCliente = false;
					auxCliente = null;
					txtPrecioTotal.setText("RD$ 0.0");
					lblBuscar.setEnabled(true);
					txtCedula.setEnabled(true);
					rdbtnFacturaACredito.setEnabled(true);
					rdbtnFacturaACredito.setSelected(false);
					listModelProductosSel.removeAllElements();
					listModelComboSel.removeAllElements();
					productosSeleccionados.clear();
					combosSeleccionados.clear();
					Home.loadTableFactura(0, null);
					
					//else {
					/*
						JOptionPane.showMessageDialog(null, "FATAL ERROR! La factura NO ha sido registrada.", "Error", JOptionPane.ERROR_MESSAGE);
						auxVendedor.setTotalVendido(auxVendedor.getTotalVendido() - factura.getPrecioTotal());
						auxVendedor.setComision(auxVendedor.getComision() - ((float) (factura.getPrecioTotal()*0.05))); 
						auxCliente.setCantCompras(auxCliente.getCantCompras() - 1);
						for(Producto producto : auxListCompra) {
							producto.setCantidad(producto.getCantidad() + 1);
						}
						Tienda.getInstance().eliminarFactura(factura);
					}
					*/
				}
			}
		});
		lblRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRegistrar.setBounds(870, 722, 225, 45);
		panel.add(lblRegistrar);
		
		lblRegistrar.setOpaque(true);
		lblRegistrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrar.setForeground(Color.WHITE);
		lblRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRegistrar.setBackground(new Color(0, 155, 124));
		
		panelDatosClientes = new JPanel();
		panelDatosClientes.setLayout(null);
		panelDatosClientes.setBackground(Color.WHITE);
		panelDatosClientes.setBounds(11, 12, 607, 489);
		panel.add(panelDatosClientes);
		
		txtDireccion = new JTextField();
		txtDireccion.setEnabled(false);
		txtDireccion.setForeground(new Color(0, 153, 153));
		txtDireccion.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtDireccion.setColumns(10);
		txtDireccion.setBackground(Color.WHITE);
		txtDireccion.setBounds(143, 248, 453, 45);
		panelDatosClientes.add(txtDireccion);
		
		txtNombre = new JTextField();
		txtNombre.setEnabled(false);
		txtNombre.setForeground(new Color(0, 153, 153));
		txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtNombre.setColumns(10);
		txtNombre.setBackground(Color.WHITE);
		txtNombre.setBounds(143, 185, 453, 45);
		panelDatosClientes.add(txtNombre);
		
		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setForeground(Color.BLACK);
		lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTelefono.setBounds(33, 244, 162, 55);
		panelDatosClientes.add(lblTelefono);
		
		JLabel lblPrecio = new JLabel("Provincia:");
		lblPrecio.setForeground(Color.BLACK);
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPrecio.setBounds(33, 303, 125, 55);
		panelDatosClientes.add(lblPrecio);
		
		JLabel lblblCrearFactura = new JLabel("Crear Factura:");
		lblblCrearFactura.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblblCrearFactura.setBounds(33, 13, 322, 55);
		panelDatosClientes.add(lblblCrearFactura);
		
		JLabel lblCedula = new JLabel("C\u00E9dula:");
		lblCedula.setForeground(Color.BLACK);
		lblCedula.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCedula.setBounds(33, 117, 196, 55);
		panelDatosClientes.add(lblCedula);
		
		txtCedula = new JTextField();
		txtCedula.setForeground(new Color(0, 153, 153));
		txtCedula.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtCedula.setColumns(10);
		txtCedula.setBackground(Color.WHITE);
		txtCedula.setBounds(143, 127, 311, 45);
		panelDatosClientes.add(txtCedula);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setForeground(Color.BLACK);
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNombre.setBounds(33, 180, 125, 55);
		panelDatosClientes.add(lblNombre);
		
		final JLabel lblX = new JLabel("X");
		lblX.setBounds(539, 11, 57, 55);
		panelDatosClientes.add(lblX);
		lblX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!listModelComboSel.isEmpty()) {
					for (Combo combo : combosSeleccionados) {
						for (Producto producto : combo.getMisProductos()) {
							aumentarStock(producto);
						}
					}
				}
				if (!listModelProductosSel.isEmpty()) {
					for (Producto producto : productosSeleccionados) {
						aumentarStock(producto);
					}
				}
				// if(nuevoCliente) {
				// 	int opcion = JOptionPane.showConfirmDialog(null,"Tambien desea eliminar la informacion del nuevo cliente registrado?", "Facturacion Cancelada", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				//     if(opcion == JOptionPane.YES_OPTION){
				//     	Tienda.getInstance().eliminarCliente(auxCliente);
				//     }
				// }
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
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(0, 155, 124));
		panel1.setBounds(34, 64, 420, 4);
		panelDatosClientes.add(panel1);
		
		lblBuscar = new JLabel("Buscar");
		lblBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(txtCedula.isEnabled()) {
					Cliente clienteBuscado = buscarClienteByCedula(txtCedula.getText());
					if (clienteBuscado != null) {
						cargarCliente(clienteBuscado);
						auxCliente = clienteBuscado;
						nuevoCliente = false;
						lblRegistrar.setEnabled(true);
						txtCedula.setEnabled(false);
						lblBuscar.setEnabled(false);
					}else {
						if (txtCedula.getText().length() != 13 || txtCedula.getText().indexOf('-') != 3 || txtCedula.getText().charAt(11) != '-'){
							JOptionPane.showMessageDialog(null,"La cedula es invalida", "Registrar Cliente", JOptionPane.WARNING_MESSAGE);
						}else{
							txtNombre.setEnabled(true);
							txtDireccion.setEnabled(true);
							txtTelefono.setEnabled(true);
							lblRegCliente.setEnabled(true);
							lblRegCliente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							txtCedula.setEnabled(false);
							lblBuscar.setEnabled(false);
						}
					}
				}
			}
		});
		lblBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBuscar.setIcon(new ImageIcon(RegFactura.class.getResource("/Imagenes/Lupa.png")));
		lblBuscar.setOpaque(true);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.BLACK);
		lblBuscar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblBuscar.setBackground(new Color(0, 155, 124));
		lblBuscar.setBounds(460, 127, 136, 45);
		panelDatosClientes.add(lblBuscar);
		
		JLabel lblClienteDatos = new JLabel("Datos del Cliente:");
		lblClienteDatos.setForeground(Color.BLACK);
		lblClienteDatos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblClienteDatos.setBounds(33, 70, 196, 55);
		panelDatosClientes.add(lblClienteDatos);
		
		txtTelefono = new JTextField();
		txtTelefono.setForeground(new Color(0, 153, 153));
		txtTelefono.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtTelefono.setEnabled(false);
		txtTelefono.setColumns(10);
		txtTelefono.setBackground(Color.WHITE);
		txtTelefono.setBounds(143, 375, 453, 45);
		panelDatosClientes.add(txtTelefono);
		
		lblRegCliente = new JLabel("Registrar");
		lblRegCliente.setEnabled(false);
		lblRegCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!txtNombre.getText().isEmpty() && !txtDireccion.getText().isEmpty() && !txtTelefono.getText().isEmpty()) {
					if(auxCliente == null) {
						auxCliente = new Cliente(txtCedula.getText(), txtNombre.getText(), txtDireccion.getText(), txtTelefono.getText());

						try {
							String SQL = "EXEC crea_persona_y_cliente ?, ?, ?, ?, ?, ?;";
							PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
							pstmt.setString(1, auxCliente.getCedula());
							pstmt.setString(2, auxCliente.getNombre());
							pstmt.setInt(3, Integer.parseInt(cbxMunicipio.getSelectedItem().toString().substring(0, cbxMunicipio.getSelectedItem().toString().indexOf("-"))));
							pstmt.setString(4, auxCliente.getDireccion());
							pstmt.setString(5, auxCliente.getTelefono());
							pstmt.setInt(6, 0);
							pstmt.execute();

						  	pstmt.close();	
						}
						catch (Exception e1) {
						  e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "El cliente ha sido registrado satisfactoriamente.", "Informacion" , JOptionPane.INFORMATION_MESSAGE);
						lblRegistrar.setEnabled(true);
						lblRegCliente.setEnabled(false);
						txtNombre.setEnabled(false);
						txtDireccion.setEnabled(false);
						txtTelefono.setEnabled(false);
						cbxMunicipio.setEnabled(false);
						cbxProvincia.setEnabled(false);
						lblRegCliente.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						nuevoCliente = true;
					}else {
						JOptionPane.showMessageDialog(null, "Ya se ha registrado este cliente.", "Informacion", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Debe completar los campos para registrar un nuevo cliente.", "Informacion", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		lblRegCliente.setOpaque(true);
		lblRegCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegCliente.setForeground(Color.BLACK);
		lblRegCliente.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRegCliente.setBackground(new Color(0, 155, 124));
		lblRegCliente.setBounds(460, 430, 136, 45);
		panelDatosClientes.add(lblRegCliente);
		
		JLabel lblDireccin = new JLabel("Direccion:");
		lblDireccin.setForeground(Color.BLACK);
		lblDireccin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDireccin.setBounds(33, 366, 136, 55);
		panelDatosClientes.add(lblDireccin);
		
		cbxProvincia = new JComboBox();
		cbxProvincia.setBounds(143, 312, 453, 45);
		panelDatosClientes.add(cbxProvincia);
		
		JLabel lblMunicipio = new JLabel("Municipio");
		lblMunicipio.setForeground(Color.BLACK);
		lblMunicipio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMunicipio.setBounds(33, 424, 136, 55);
		panelDatosClientes.add(lblMunicipio);
		
		cbxMunicipio = new JComboBox();
		cbxMunicipio.setBounds(143, 433, 311, 45);
		panelDatosClientes.add(cbxMunicipio);
		
		JPanel panelRadioButtons = new JPanel();
		panelRadioButtons.setLayout(null);
		panelRadioButtons.setBackground(Color.WHITE);
		panelRadioButtons.setBounds(630, 12, 465, 42);
		panel.add(panelRadioButtons);
		
		btnAddProducto = new JRadioButton("Agregar Productos");
		btnAddProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelProductos.setVisible(true);
				panelCombos.setVisible(false);
				
				btnAddProducto.setSelected(true);
				btnAddCombo.setSelected(false);
			}
		});
		btnAddProducto.setSelected(true);
		btnAddProducto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAddProducto.setForeground(new Color(0, 0, 0));
		btnAddProducto.setBounds(20, 9, 199, 25);
		panelRadioButtons.add(btnAddProducto);
		
		btnAddCombo = new JRadioButton("Agregar Combos");
		btnAddCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelProductos.setVisible(false);
				panelCombos.setVisible(true);
				
				btnAddProducto.setSelected(false);
				btnAddCombo.setSelected(true);
			}
		});
		btnAddCombo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAddCombo.setForeground(new Color(0, 0, 0));
		btnAddCombo.setBounds(239, 9, 206, 25);
		panelRadioButtons.add(btnAddCombo);
		
		JPanel panelDatosFactura = new JPanel();
		panelDatosFactura.setLayout(null);
		panelDatosFactura.setBackground(Color.WHITE);
		panelDatosFactura.setBounds(13, 511, 607, 256);
		panel.add(panelDatosFactura);
		
		txtPrecioTotal = new JTextField();
		txtPrecioTotal.setText("RD$ 0.0");
		txtPrecioTotal.setEnabled(false);
		txtPrecioTotal.setForeground(new Color(0, 153, 153));
		txtPrecioTotal.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtPrecioTotal.setColumns(10);
		txtPrecioTotal.setBackground(Color.WHITE);
		txtPrecioTotal.setBounds(169, 115, 427, 45);
		panelDatosFactura.add(txtPrecioTotal);
		
		JLabel lblCodigo = new JLabel("C\u00F3digo:");
		lblCodigo.setForeground(Color.BLACK);
		lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCodigo.setBounds(32, 47, 196, 55);
		panelDatosFactura.add(lblCodigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setEnabled(false);

		
		txtCodigo.setText(getCodigo());
		txtCodigo.setForeground(new Color(0, 153, 153));
		txtCodigo.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtCodigo.setColumns(10);
		txtCodigo.setBackground(Color.WHITE);
		txtCodigo.setBounds(169, 57, 427, 45);
		panelDatosFactura.add(txtCodigo);
		
		JLabel lblPrecioTotal = new JLabel("Precio Total:");
		lblPrecioTotal.setForeground(Color.BLACK);
		lblPrecioTotal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPrecioTotal.setBounds(32, 110, 125, 55);
		panelDatosFactura.add(lblPrecioTotal);
		
		rdbtnFacturaACredito = new JRadioButton("Factura a Credito");
		rdbtnFacturaACredito.setEnabled(true);
		rdbtnFacturaACredito.setForeground(Color.BLACK);
		rdbtnFacturaACredito.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rdbtnFacturaACredito.setBounds(211, 190, 199, 25);
		panelDatosFactura.add(rdbtnFacturaACredito);
		
		JLabel lblDatosFactura = new JLabel("Datos de la Factura:");
		lblDatosFactura.setForeground(Color.BLACK);
		lblDatosFactura.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDatosFactura.setBounds(32, 0, 196, 55);
		panelDatosFactura.add(lblDatosFactura);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(12, 173, 584, 55);
		panelDatosFactura.add(lblNewLabel);
		
		panelProductos = new JPanel();
		panelProductos.setLayout(null);
		panelProductos.setBackground(Color.WHITE);
		panelProductos.setBounds(630, 67, 465, 561);
		panel.add(panelProductos);
		
		lblArribaProductos = new JLabel("\u2191\u2191");
		lblArribaProductos.setOpaque(true);
		lblArribaProductos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(listProductosSel.getSelectedValue() != null) {
					String aux = listProductosSel.getSelectedValue().toString();
					Producto auxProducto = buscarProductoByNumSerie(aux.substring(aux.indexOf('(')+1, aux.indexOf('|')-1));
					aumentarStock(auxProducto);
					
					listModelProductosSel.remove(listProductosSel.getSelectedIndex());
					productosSeleccionados.remove(auxProducto);
					auxProducto.setCantidad(auxProducto.getCantidad()+1);

					boolean existe = false;
					int index = 0;
					for(int i = 0; i < listModelProductosDisp.size(); i++) {
						String productoDisp = new String(listModelProductosDisp.elementAt(i));
						if(aux.equalsIgnoreCase(productoDisp)){
							existe = true;
							index = i;
						}
					}											
					if(!existe) {
						listModelProductosDisp.addElement(aux);
					}
					
					lblArribaProductos.setBackground(new Color(0, 85, 70));
					lblArribaProductos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					
					txtPrecioTotal.setText("RD$ "+calcTotal()); //estoy probando
					//loadProductosDisponibles();
					//loadCombosDisponibles();
				}else {
					JOptionPane.showMessageDialog(null, "Debe seleccionar el Producto que desea quitar de la factura.", "Informacion", JOptionPane.WARNING_MESSAGE);
				}	
			}
		});
		lblArribaProductos.setHorizontalAlignment(SwingConstants.CENTER);
		lblArribaProductos.setForeground(Color.WHITE);
		lblArribaProductos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblArribaProductos.setBackground(new Color(0, 85, 70));
		lblArribaProductos.setBounds(157, 275, 74, 45);
		panelProductos.add(lblArribaProductos);
		
		lblAbajoProductos = new JLabel("\u2193\u2193");
		lblAbajoProductos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!txtCedula.getText().isEmpty()) {
					if(listProductosDisp.getSelectedValue() != null) {
						String aux = listProductosDisp.getSelectedValue().toString();
						String auxString = aux.substring(aux.indexOf('(')+1, aux.indexOf('|')-1);
						Producto auxProducto = buscarProductoByNumSerie(auxString);
						reducirStock(auxProducto);
						
						listModelProductosSel.addElement(aux);
						productosSeleccionados.add(auxProducto);
						auxProducto.setCantidad(auxProducto.getCantidad()-1);
						
						if(auxProducto.getCantidad() == 0) {
							listModelProductosDisp.remove(listProductosDisp.getSelectedIndex());
						}
						
						lblAbajoProductos.setBackground(new Color(0, 85, 70));
						lblAbajoProductos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						
						txtPrecioTotal.setText("RD$ "+calcTotal());
					}else {
						JOptionPane.showMessageDialog(null, "Debe seleccionar el Producto que desea agregar a la factura.", "Informacion", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Debe especificar una cedula para seleccionar un Producto.", "Informacion", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		lblAbajoProductos.setOpaque(true);
		lblAbajoProductos.setHorizontalAlignment(SwingConstants.CENTER);
		lblAbajoProductos.setForeground(Color.WHITE);
		lblAbajoProductos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAbajoProductos.setBackground(new Color(0, 85, 70));
		lblAbajoProductos.setBounds(243, 275, 74, 45);
		panelProductos.add(lblAbajoProductos);
		
		JLabel lblProductosDisp = new JLabel("Productos disponibles:");
		lblProductosDisp.setForeground(Color.BLACK);
		lblProductosDisp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblProductosDisp.setBounds(12, 0, 300, 55);
		panelProductos.add(lblProductosDisp);
		
		JScrollPane scrollPaneProductosDisp = new JScrollPane();
		scrollPaneProductosDisp.setBounds(12, 51, 441, 215);
		panelProductos.add(scrollPaneProductosDisp);
		
		listProductosDisp = new JList<String>();
		listProductosDisp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int index = -1;
				index = listProductosDisp.getSelectedIndex();
				if (index != -1) {
					lblAbajoProductos.setBackground(new Color(0, 155, 124));
					lblAbajoProductos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			}
		});
		listModelProductosDisp = new DefaultListModel<String>();
		listProductosDisp.setModel(listModelProductosDisp);
		listProductosDisp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		listProductosDisp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneProductosDisp.setViewportView(listProductosDisp);
		
		JLabel lblElejidos = new JLabel("Seleccionados:");
		lblElejidos.setForeground(Color.BLACK);
		lblElejidos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblElejidos.setBounds(12, 280, 140, 55);
		panelProductos.add(lblElejidos);
		
		JScrollPane scrollPaneProductosSel = new JScrollPane();
		scrollPaneProductosSel.setBounds(12, 333, 441, 215);
		panelProductos.add(scrollPaneProductosSel);
		
		listProductosSel = new JList<String>();
		listProductosSel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int index = -1;
				index = listProductosSel.getSelectedIndex();
				if (index != -1) {
					lblArribaProductos.setBackground(new Color(0, 155, 124));
					lblArribaProductos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			}
		});
		listModelProductosSel = new DefaultListModel<String>();
		listProductosSel.setModel(listModelProductosSel);
		listProductosSel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		listProductosSel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneProductosSel.setViewportView(listProductosSel);

		addMisProductosToLocal();
		addMisCombosToLocal();
		loadProductosDisponibles();
		loadCombosDisponibles();
		cargarComboBox();
	}
	
	private boolean crearFactura(Vendedor auxVendedor, Cliente auxCliente, ArrayList<Producto> auxListCompra, float montoTotal, boolean aCredito) {		

		boolean continuar = false;
		int cantidad = 0;
		int codFactura = Integer.parseInt(getCodigo());

		try {
			Conexion.getInstance().setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("Entro a un error...");
			e.printStackTrace();
		}

		try {
			String SQL = "INSERT INTO factura (cedulaCliente, cedulaVendedor, fecha, montoTotal, esACredito) values(?, ?, getDate(), ?, ?); ";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setString(1, auxCliente.getCedula());
			pstmt.setString(2, auxVendedor.getCedula());
			pstmt.setFloat(3, montoTotal);
			pstmt.setBoolean(4, aCredito);
			int count = pstmt.executeUpdate();
			
			System.out.println("Filas afectadas: "+count);
			pstmt.close();		
		 }
		 catch (Exception e1) {
			System.out.println("Entro a un error...");
			JOptionPane.showMessageDialog(null, "La factura no pudo ser creada correctamente.", "Error al crear factura", JOptionPane.ERROR_MESSAGE);
		   e1.printStackTrace();
		try {
			Conexion.getInstance().rollback();
		} catch (SQLException e) {
			System.out.println("Entro a un error...");
			e.printStackTrace();
		}
			return continuar;
		}

		ArrayList<Producto> productosSinRepetir = new ArrayList<Producto>();
		ArrayList<Producto> productos = new ArrayList<Producto>();
		
		productos.addAll(productosSeleccionados);
		
		for (int i = 0; i < productos.size(); i++) {
			int j = 1;
			cantidad = contarProducto(productos.get(i), productosSeleccionados);
			if (cantidad > 1) {
				while (j < cantidad) {
					productos.remove(productos.lastIndexOf(productos.get(i)));
					j++;
				}
				productosSinRepetir.add(productos.get(i));
			}else {
				productosSinRepetir.add(productos.get(i));
			}
		}
		
		try {
			for (Producto producto : productosSinRepetir)
			{
				cantidad = contarProducto(producto, productosSeleccionados);				
				String SQL = "INSERT INTO detalle_factura_producto values (?, ?, ?);";
				PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
				pstmt.setInt(1, codFactura);
				pstmt.setString(2, producto.getNumSerie());
				pstmt.setInt(3, cantidad);
				int count = pstmt.executeUpdate();
				System.out.println("Filas afectadas: "+count);
			}

			for (Combo combo : combosSeleccionados)
			{
				String SQL = "INSERT INTO detalle_factura_combo values (?, ?);";
				PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
				pstmt.setInt(1, codFactura);
				pstmt.setInt(2, Integer.valueOf(combo.getCodigo()));
				System.out.println("Combo: "+codFactura+" : "+combo.getCodigo());
				int count = pstmt.executeUpdate();
				System.out.println("Filas afectadas: "+count);
			}
			
		} catch (Exception e) {
			System.out.println("Entro a un error...");
			continuar = false;
			try {
				Conexion.getInstance().rollback();
			} catch (SQLException e1) {
				System.out.println("Entro a un error...");
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "La factura no pudo ser creada correrctamente.", "Error al crear factura", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return continuar;
		}
		try {
			Conexion.getInstance().commit();
			continuar = true;
		} catch (SQLException e) {
			System.out.println("Entro a un error...");
			e.printStackTrace();
		}
		try {
			Conexion.getInstance().setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println("Entro a un error...");
			e.printStackTrace();
		}
		return continuar;
	}

	private int contarProducto(Producto productoAux, ArrayList<Producto> misProductos){
		int cont = 0;

		for (Producto producto : misProductos) {
			if (producto.getNumSerie().equalsIgnoreCase(productoAux.getNumSerie())){
				cont++;
			}
		}

		return cont;
	}
	
	private void actualizarVendedor(Vendedor vendedor){
		ResultSet rs1 = null;
		try {
			try {
				String SQL1 = "SELECT * FROM vendedor WHERE cedulaEmpleado = ?";
				PreparedStatement pstmt1 = Conexion.getInstance().prepareStatement(SQL1);
				pstmt1.setString(1, vendedor.getCedula());
				rs1 = pstmt1.executeQuery();
				rs1.next();
				
			} catch (Exception e1){
				 e1.printStackTrace();
				 JOptionPane.showMessageDialog(null, "Error al extraer de la Base de Datos", "Modificar Producto", JOptionPane.CLOSED_OPTION);
			}
			String SQL2 = "UPDATE vendedor SET totalVendido = ?, comision = ? WHERE cedulaEmpleado = ?";
			PreparedStatement pstmt2 = Conexion.getInstance().prepareStatement(SQL2);
			pstmt2.setFloat(1, (rs1.getFloat("totalVendido") + factura.getPrecioTotal()));
			pstmt2.setFloat(2, rs1.getFloat("comision") + (float)(factura.getPrecioTotal() * 0.05));
			pstmt2.setString(3, vendedor.getCedula());
			pstmt2.executeUpdate();

			pstmt2.close();
			rs1.close();
		} catch (Exception e1) {
			   e1.printStackTrace();
		}
	}
	
	private void actualizarCliente(Cliente cliente) {

		try {
			String SQL2 = "UPDATE cliente SET cantCompras = cantCompras + 1 WHERE cedulaPersona = ?";
			PreparedStatement pstmt2 = Conexion.getInstance().prepareStatement(SQL2);
			pstmt2.setString(1, cliente.getCedula());
			pstmt2.executeUpdate();
		} catch (Exception e1){
			 e1.printStackTrace();
			 JOptionPane.showMessageDialog(null, "Error al extraer de la Base de Datos", "Modificar Producto", JOptionPane.CLOSED_OPTION);
		}

	}
	
	private void actualizarCombo(Combo combo) {
		try {
			String SQL = "UPDATE combo SET fueVendido = 1 WHERE codigo = ?";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setInt(1, Integer.valueOf(combo.getCodigo()));
			pstmt.executeUpdate();
	
			pstmt.close();
		} catch (Exception e1) {
			   e1.printStackTrace();
		}
	}
	
	private void addMisProductosToLocal()
	{
		ResultSet rs = null;

		try {
			String SQL = "SELECT * FROM producto WHERE cantStock > 0;";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Producto aux = buscarProductoByNumSerie(rs.getString("numSerie"));
				misProductos.add(aux);
			}
		
			rs.close();
			pstmt.close();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void addMisCombosToLocal() {

		ResultSet rs = null;
		ArrayList<Producto> misProductosEnCombos = new ArrayList<Producto>();

		try {
			String SQL = "SELECT * FROM combo WHERE fueVendido = 0";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				try {
					String SQL2 = "SELECT * FROM combo_producto WHERE codigoCombo = ?";
					PreparedStatement pstmt2 = Conexion.getInstance().prepareStatement(SQL2);
					pstmt2.setString(1, rs.getString("codigo"));
					ResultSet rs2 = pstmt2.executeQuery();
					while (rs2.next()) {
						misProductosEnCombos.add(buscarProductoByNumSerie(rs2.getString("numSerie")));
					}
					rs2.close();
					pstmt2.close();
	
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Combo aux = new Combo(rs.getString("codigo"), rs.getString("nombre"), misProductosEnCombos, rs.getFloat("descuentoPorProducto"));
				misCombos.add(aux);
			}
		
			rs.close();
			pstmt.close();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void loadCombosDisponibles() {

		Statement selectStmt = null;
		boolean existe = false;
		try {
			selectStmt = Conexion.getInstance().createStatement();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectarse a la Base de Datos", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		try {
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM combo "
					+"INNER JOIN combo_producto ON combo_producto.codigoCombo = combo.codigo "
					+"ORDER BY codigo ASC;");
			
			while (rs.next()) {
				String aux = new String(rs.getString("Nombre")+" ("+ rs.getInt("descuentoPorProducto") +"% off) | Codigo: "+rs.getString("codigo"));
				
				for(int i = 0; i < listModelComboDisp.size(); i++) {
					String comboDisp = new String(listModelComboDisp.elementAt(i));
					if(aux.equals(comboDisp)) {
						existe = true;
					}else {
						existe = false;
					}
				}
				if(!existe) {
					listModelComboDisp.addElement(aux);
				}
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al buscar productos en la BD", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		
		
		//listModelComboDisp.removeAllElements();
		// ResultSet rs = null;
		
		// try {
		// 	String SQL = "SELECT * FROM combo WHERE producto.cantStock > 0;";
		// 	PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
		// 	rs = pstmt.executeQuery();
			
		// 	rs.next();
			
		// 	rs.close();
		// 	pstmt.close();
		// }
		// catch (Exception e1) {
		// 	e1.printStackTrace();
		// }
		
		// for (Combo combo : misCombos) {
		// 	String aux = new String(combo.getCodigo()+" | "+combo.getNombre());
			
		// 	for(Producto producto : combo.getMisProductos()) {
		// 		if(producto.getCantidad() == 0) {
		// 			disponible = false;
		// 		}
		// 	}
		// 	if(disponible) {
		// 		listModelComboDisp.addElement(aux);
		// 	}			
		// }
		//for (Combo combo : misCombos) {
		//	String aux = new String(combo.getCodigo()+" | "+combo.getNombre());
		//	listModelComboDisp.addElement(aux);
		//}
	}

	private void loadProductosDisponibles() {
		Statement selectStmt = null;
		
		try {
			selectStmt = Conexion.getInstance().createStatement();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectarse a la Base de Datos", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		try {
			ResultSet rs = selectStmt.executeQuery("SELECT numSerie, marca, modelo, precio, cantStock, dispMin, tipoProducto FROM producto ORDER BY numSerie ASC;");
			
			while (rs.next()) {
				String aux = new String(rs.getString("marca")+" "+rs.getString("modelo")+ " ("+rs.getString("numSerie")+" | "+rs.getString("tipoProducto")+")");

				if(rs.getInt("cantStock") > 0) {
					listModelProductosDisp.addElement(aux);
				}
			}
			rs.close();
	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al buscar productos en la BD", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void reducirStock(Producto auxProducto) {
		ResultSet rs1 = null;
		try {
			try {
				String SQL1 = "SELECT * FROM producto WHERE numSerie = ?";
				PreparedStatement pstmt1 = Conexion.getInstance().prepareStatement(SQL1);
				pstmt1.setString(1, auxProducto.getNumSerie());
				rs1 = pstmt1.executeQuery();
				rs1.next();
				
			} catch (Exception e1){
				 e1.printStackTrace();
				 JOptionPane.showMessageDialog(null, "Error al extraer de la Base de Datos", "Modificar Producto", JOptionPane.CLOSED_OPTION);
			}
			String SQL2 = "UPDATE producto SET cantStock = ? WHERE numSerie = ?";
			PreparedStatement pstmt2 = Conexion.getInstance().prepareStatement(SQL2);
			pstmt2.setInt(1, (rs1.getInt("cantStock")-1));
			pstmt2.setString(2, auxProducto.getNumSerie());
			pstmt2.executeUpdate();

			pstmt2.close();
			rs1.close();
		} catch (Exception e1) {
			   e1.printStackTrace();
		}
	}
	
	private void aumentarStock(Producto auxProducto) {
		ResultSet rs1 = null;
		try {
			try {
				String SQL1 = "SELECT * FROM producto WHERE numSerie = ?";
				PreparedStatement pstmt1 = Conexion.getInstance().prepareStatement(SQL1);
				pstmt1.setString(1, auxProducto.getNumSerie());
				rs1 = pstmt1.executeQuery();
				rs1.next();
				
			} catch (Exception e1){
				 e1.printStackTrace();
				 JOptionPane.showMessageDialog(null, "Error al extraer de la Base de Datos", "Modificar Producto", JOptionPane.CLOSED_OPTION);
			}
			
			String SQL2 = "UPDATE producto SET cantStock = ? WHERE numSerie = ?";
			PreparedStatement pstmt2 = Conexion.getInstance().prepareStatement(SQL2);
			pstmt2.setInt(1, (rs1.getInt("cantStock")+1));
			pstmt2.setString(2, auxProducto.getNumSerie());
			pstmt2.executeUpdate();

			pstmt2.close();
			rs1.close();
			
		} catch (Exception e1) {
			   e1.printStackTrace();
		}
	}
	
	private void cargarCliente(Cliente clienteBuscado) {
		
		cbxMunicipio.setEnabled(false);
		cbxProvincia.setEnabled(false);
		txtNombre.setText(clienteBuscado.getNombre());
		txtDireccion.setText(clienteBuscado.getDireccion());
		txtTelefono.setText(clienteBuscado.getTelefono());
		String direccion, municipio, provincia;
		int idMunicipio;
		int idProvincia;
		
		ResultSet rs = null;
		ResultSet rs2 = null;

		try {
			String SQL = "SELECT * FROM persona WHERE cedulaPersona = ?;";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setString(1, clienteBuscado.getCedula());
			rs = pstmt.executeQuery();
			rs.next();
						
			direccion = rs.getString("direccion");
			idMunicipio = rs.getInt("idMunicipio");
			
			String SQL2 = "SELECT provincia.nombre as nombreP, municipio.nombre as nombreM, provincia.idProvincia FROM provincia, persona, municipio WHERE cedulaPersona = ? and municipio.idProvincia = provincia.idProvincia and municipio.idMunicipio = persona.idMunicipio;";
			PreparedStatement pstmt2 = Conexion.getInstance().prepareStatement(SQL2);
			pstmt2.setString(1, clienteBuscado.getCedula());
			rs2 = pstmt2.executeQuery();
			rs2.next();
			
			direccion = rs.getString("direccion");
			idMunicipio = rs.getInt("idMunicipio");
			
			idProvincia = rs2.getInt("idProvincia");
			municipio = rs2.getString("nombreM");
			provincia = rs2.getString("nombreP");
			
			txtDireccion.setText(direccion);
			cbxMunicipio.setSelectedItem(idMunicipio + "-" + municipio);
			cbxProvincia.setSelectedItem(idProvincia + "-" + provincia);
						
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private void cleanCliente() {
		txtCedula.setText("");
		txtNombre.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtCodigo.setText(getCodigo());
		txtPrecioTotal.setText("");
	}
	
	private float calcTotal() {
		float total = 0;
		
		if(!listModelProductosSel.isEmpty()) {
			for(int i = 0; i < listModelProductosSel.size(); i++) {
				String auxProducto = new String(listModelProductosSel.elementAt(i));
				String numSerie = auxProducto.substring(auxProducto.indexOf('(')+1, auxProducto.indexOf('|')-1);
				Producto productoTemp = Tienda.getInstance().buscarProductoByNumSerie(numSerie);
				
				total += productoTemp.getPrecio();
			}
		}
		
		if(!listModelComboSel.isEmpty()) {
			for(int i = 0; i < listModelComboSel.size(); i++) {
				String auxCombo = new String(listModelComboSel.elementAt(i));
				String codigo = auxCombo.substring(auxCombo.indexOf(':')+1, auxCombo.length());
				Combo comboTemp = Tienda.getInstance().buscarComboByCod(codigo);
				
				float auxTotal = 0;
				for(Producto productoTemp: comboTemp.getMisProductos()) {
					auxTotal += productoTemp.getPrecio();
				}
				auxTotal = auxTotal - (auxTotal * (comboTemp.getDescuento() / 100));
				total += auxTotal;
			}
		}
		
		
		
		/*
		if (!productosSeleccionados.isEmpty()) {		
			for (Producto producto : productosSeleccionados) {
				total += producto.getPrecio();
			}
		}
		if (!combosSeleccionados.isEmpty()) {
			for (Combo combo : combosSeleccionados) {
				total += combo.getPrecioTotal();
			}
		}
		total = (float) (Math.round(total * 100.0) / 100.0);
		
		if(auxCliente == null) {
			JOptionPane.showMessageDialog(null, "No se ha identificado el Cliente de esta factura.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		*/
		return total;
	}
	
	private String getCodigo() {
		Statement selectStmtCodigo = null;
		String auxCodigo = null;
		
		try {
			selectStmtCodigo = Conexion.getInstance().createStatement();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectarse a la Base de Datos", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		try {
			ResultSet rs = selectStmtCodigo.executeQuery("select MAX(codigo) as max from factura;");
			rs.next();
			auxCodigo = new String(String.valueOf(rs.getInt("max")+1));
			rs.close();
	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al buscar productos en la BD", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return auxCodigo;
	}
	
	protected Cliente buscarClienteByCedula(String cedula) {
		ResultSet rs = null;
		Cliente clienteAux = null;
		
		try {
			String SQL = "SELECT * FROM datos_persona_cliente WHERE cedulaPersona = ?;";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setString(1, cedula);
			rs = pstmt.executeQuery();
			if(!rs.next()) {
				return clienteAux;
			}

			String cedulaAux = rs.getString("cedulaPersona");
			String nombre = rs.getString("nombre");
			String direccion = rs.getString("direccion") + ", " + rs.getString("nombreMunicipio");
			String telefono = rs.getString("telefono");
			
			clienteAux = new Cliente(cedulaAux, nombre, direccion, telefono );
			clienteAux.setCantCompras(rs.getInt("cantCompras"));

			rs.close();
			pstmt.close();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
		return clienteAux;
	}

	public Producto buscarProductoByNumSerie(String numSerie) {
		ResultSet rs = null;

		try {
			String SQL = "SELECT * FROM producto WHERE numSerie = ?;";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setString(1, numSerie);
			rs = pstmt.executeQuery();
			rs.next();
			String productType = rs.getString("tipoProducto");

			if (productType.equalsIgnoreCase("disco duro")){
				DiscoDuro selected;
				selected = new DiscoDuro(rs.getString("numSerie"), rs.getInt("cantStock"), rs.getFloat("precio"),
					rs.getString("marca"), rs.getString("modelo"), rs.getInt("dispMin"), rs.getInt("dispMax"), rs.getInt("capacidad"),rs.getString("socket"), rs.getString("tipoProducto") );
				return (Producto)selected;
				
			}else if (productType.equalsIgnoreCase("memoria ram")){
				MemoriaRam selected;
				selected = new MemoriaRam(rs.getString("numSerie"), rs.getInt("cantStock"), rs.getFloat("precio"),
					rs.getString("marca"), rs.getString("modelo"), rs.getInt("dispMin"), rs.getInt("dispMax"), rs.getInt("capacidad"),rs.getString("tipoRam"), rs.getString("tipoProducto") );
				return (Producto)selected;
		
			}else if (productType.equalsIgnoreCase("Micro procesador")){
				MicroProcesador selected;
				selected = new MicroProcesador(rs.getString("numSerie"), rs.getInt("cantStock"), rs.getFloat("precio"),
					rs.getString("marca"), rs.getString("modelo"), rs.getInt("dispMin"), rs.getInt("dispMax"),rs.getString("socket"), rs.getFloat("velocidadProcesamiento"),rs.getString("tipoProducto") );
				return (Producto)selected;
			}else if (productType.equalsIgnoreCase("Tarjeta Madre")){
				MotherBoard selected;
				selected = new MotherBoard(rs.getString("numSerie"), rs.getInt("cantStock"), rs.getFloat("precio"),
					rs.getString("marca"), rs.getString("modelo"), rs.getInt("dispMin"), rs.getInt("dispMax"),rs.getString("socket"), rs.getString("tipoRam"), rs.getString("tipoConexionHD"), rs.getString("tipoProducto") );
				return (Producto)selected;
			}
			rs.close();
			pstmt.close();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;	
	}
	
	private void cargarComboBox(){
		
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		cbxProvincia.setModel(model);
		
		Statement selectStmt = null;
		try {
			selectStmt = Conexion.getInstance().createStatement();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectarse a la Base de Datos", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		try {
			ResultSet rs = selectStmt .executeQuery("select cast(provincia.idProvincia as VARCHAR(12)) + '-' + provincia.nombre as nombre from provincia;");
			
			while (rs.next()) {
				
				model.addElement(rs.getString("nombre"));
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al buscar municipios en la BD", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		cargarComboBoxMunicipio();
	}
	
	private void cargarComboBoxMunicipio(){
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel();
		cbxMunicipio.setModel(model);
		
		Statement selectStmt = null;
		try {
			selectStmt = Conexion.getInstance().createStatement();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectarse a la Base de Datos", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		try {
			ResultSet rs = selectStmt .executeQuery("select distinct cast(municipio.idMunicipio as VARCHAR(12)) + '-' + municipio.nombre as nombre from municipio, provincia where municipio.idProvincia = " + cbxProvincia.getSelectedItem().toString().substring(0, cbxProvincia.getSelectedItem().toString().indexOf("-"))+ ";");
			
			while (rs.next()) {
				
				model.addElement(rs.getString("nombre"));
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al buscar municipios en la BD", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}