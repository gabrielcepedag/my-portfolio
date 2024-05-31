package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Administrador;
import logico.Conexion;
import logico.Empleado;
import logico.Tienda;
import logico.Vendedor;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.security.Identity;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Cursor;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.io.FileUtilities;
import org.jfree.ui.ExtensionFileFilter;

import com.itextpdf.text.pdf.codec.Base64;

import javax.swing.JComboBox;

public class RegVendedor extends JDialog {

	private JPanel contentPane;
	private JPasswordField txtPassword;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtUsername;
	private JPanel panelRegistro;
	Empleado selected = null;
	private JTextField txtcedula;
	private JLabel labelIcon;
	private File f;
	private JLabel lblCargarImagen;
	private JComboBox cbxProvincia;
	private JComboBox cbxMunicipio;
	private JTextField txtDireccion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegVendedor dialog = new RegVendedor(null);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public RegVendedor(Empleado vendedor) throws IOException {
		if (vendedor != null) {
			if (vendedor.isVendedor()) {
				selected = (Vendedor)vendedor;
			}else {
				selected = (Administrador)vendedor;
			}
		}
		setUndecorated(true);
		setBounds(100, 100, 919, 739);
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
		panelRegistro.setBounds(11, 12, 886, 707);
		panel.add(panelRegistro);
		
		labelIcon = new JLabel("");
		labelIcon.setHorizontalAlignment(SwingConstants.CENTER);
		labelIcon.setBounds(552, 363, 321, 269);
		panelRegistro.add(labelIcon);
		labelIcon.setBorder(new LineBorder(new Color(0, 155, 124), 5));
		labelIcon.setForeground(Color.BLACK);
		labelIcon.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		lblCargarImagen = new JLabel("Cargar Imagen");
		lblCargarImagen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblCargarImagen.setBounds(552, 642, 321, 55);
		panelRegistro.add(lblCargarImagen);
		lblCargarImagen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (lblCargarImagen.isEnabled()) {
					try {
						JFileChooser jfc = new JFileChooser();
						jfc.setDialogTitle("Busca la imagen");
				        jfc.showOpenDialog(null);
				        f = jfc.getSelectedFile();
				        Image bi;
				        if (f != null) {
				        	bi = ImageIO.read(f);
							labelIcon.setText("");
							labelIcon.setIcon(new ImageIcon(bi.getScaledInstance(234,203, 0)));
						}
					} catch (IOException i) {
						i.printStackTrace();
					}
				}
			}
		});
		lblCargarImagen.setOpaque(true);
		lblCargarImagen.setHorizontalAlignment(SwingConstants.CENTER);
		lblCargarImagen.setForeground(Color.WHITE);
		lblCargarImagen.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCargarImagen.setBackground(new Color(0, 155, 124));
		
		txtUsername = new JTextField();
		txtUsername.setForeground(new Color(0, 153, 153));
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtUsername.setColumns(10);
		txtUsername.setBackground(Color.WHITE);
		txtUsername.setBounds(190, 298, 683, 50);
		panelRegistro.add(txtUsername);
		
		txtTelefono = new JTextField();
		txtTelefono.setForeground(new Color(0, 153, 153));
		txtTelefono.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtTelefono.setColumns(10);
		txtTelefono.setBackground(Color.WHITE);
		txtTelefono.setBounds(190, 233, 683, 50);
		panelRegistro.add(txtTelefono);
		
		txtNombre = new JTextField();
		txtNombre.setForeground(new Color(0, 153, 153));
		txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtNombre.setColumns(10);
		txtNombre.setBackground(Color.WHITE);
		txtNombre.setBounds(190, 168, 683, 50);
		panelRegistro.add(txtNombre);
		
		JLabel lblDireccin = new JLabel("Provincia:");
		lblDireccin.setIcon(new ImageIcon(Login.class.getResource("/Imagenes/ubicacion.png")));
		lblDireccin.setForeground(Color.BLACK);
		lblDireccin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDireccin.setBounds(34, 428, 136, 55);
		panelRegistro.add(lblDireccin);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setIcon(new ImageIcon(Login.class.getResource("/Imagenes/Password.png")));
		lblContrasea.setForeground(Color.BLACK);
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblContrasea.setBounds(34, 360, 162, 55);
		panelRegistro.add(lblContrasea);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setIcon(new ImageIcon(Login.class.getResource("/Imagenes/Email.png")));
		lblUsername.setForeground(Color.BLACK);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsername.setBounds(34, 290, 162, 55);
		panelRegistro.add(lblUsername);
		
		JLabel lblNmero = new JLabel("N\u00FAmero:");
		lblNmero.setIcon(new ImageIcon(Login.class.getResource("/Imagenes/Telefono.png")));
		lblNmero.setForeground(Color.BLACK);
		lblNmero.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNmero.setBounds(34, 227, 125, 55);
		panelRegistro.add(lblNmero);
		
		JLabel lblTitle = new JLabel("Registrar Vendedor:");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblTitle.setBounds(33, 13, 431, 55);
		panelRegistro.add(lblTitle);
		
		JLabel label_14 = new JLabel("C\u00E9dula:");
		label_14.setIcon(new ImageIcon(Login.class.getResource("/Imagenes/Cedula.png")));
		label_14.setForeground(Color.BLACK);
		label_14.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label_14.setBounds(32, 101, 125, 55);
		panelRegistro.add(label_14);
		
		txtcedula = new JTextField();
		txtcedula.setForeground(new Color(0, 153, 153));
		txtcedula.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtcedula.setColumns(10);
		txtcedula.setBackground(Color.WHITE);
		txtcedula.setBounds(190, 103, 683, 50);
		panelRegistro.add(txtcedula);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setIcon(new ImageIcon(Login.class.getResource("/Imagenes/Name.png")));
		lblNombre.setForeground(Color.BLACK);
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNombre.setBounds(34, 164, 125, 55);
		panelRegistro.add(lblNombre);
		
		txtPassword = new JPasswordField();
		txtPassword.setForeground(new Color(0, 153, 153));
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtPassword.setBounds(190, 363, 344, 50);
		panelRegistro.add(txtPassword);
		
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
		lblNewLabel.setBounds(34, 642, 225, 55);
		panelRegistro.add(lblNewLabel);
		
		final JLabel lblX = new JLabel("X");
		lblX.setBounds(817, 0, 57, 55);
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
		
		JLabel lblConfirmar = new JLabel("Registrar");
		lblConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblConfirmar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selected == null) {
					if (txtcedula.getText().equalsIgnoreCase("") || txtDireccion.getText().equalsIgnoreCase("") || txtNombre.getText().equalsIgnoreCase("") || txtPassword.getPassword().length == 0
							|| txtTelefono.getText().equalsIgnoreCase("") || txtUsername.getText().equalsIgnoreCase("") || labelIcon.getIcon() == null) {
						JOptionPane.showMessageDialog(null, "Debes llenar todos los campos !", "Registro de Vendedor", JOptionPane.WARNING_MESSAGE);
					}else {
						
						try {
							String SQL = "INSERT INTO persona"
							+ "(cedulaPersona, nombre, idMunicipio, direccion, telefono, foto)"
							+ "values(?, ?, ?, ?, ?, ?); ";
							PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
							pstmt.setString(1, txtcedula.getText());
							pstmt.setString(2, txtNombre.getText());
							int idMunicipio = Integer.parseInt(cbxMunicipio.getSelectedItem().toString().substring(0, cbxMunicipio.getSelectedItem().toString().indexOf("-")));
							pstmt.setInt(3, idMunicipio);
							pstmt.setString(4, txtDireccion.getText());
							pstmt.setString(5, txtTelefono.getText());
							FileInputStream convertir_imagen = new FileInputStream(f);
							pstmt.setBlob(6, convertir_imagen, f.length());
							int count = pstmt.executeUpdate();
							
							System.out.println("Filas afectadas: "+count);
							pstmt.close();
							
							String SQLEmpleado = "INSERT INTO empleado"
									+ "(cedulaPersona, username, password)"
									+ "values(?, ?, ?); ";
									PreparedStatement pstmtEmpleado = Conexion.getInstance().prepareStatement(SQLEmpleado);
									pstmtEmpleado.setString(1, txtcedula.getText());
									pstmtEmpleado.setString(2, txtUsername.getText());
									pstmtEmpleado.setBytes(3, txtPassword.getText().getBytes());
									count = pstmtEmpleado.executeUpdate();
									
									System.out.println("Filas afectadas: "+count);
									pstmtEmpleado.close();
									
							String SQLVendedor = "INSERT INTO vendedor"
									+ "(cedulaEmpleado)"
									+ "values(?); ";
									PreparedStatement pstmtVendedor = Conexion.getInstance().prepareStatement(SQLVendedor);
									pstmtVendedor.setString(1, txtcedula.getText());
									count = pstmtVendedor.executeUpdate();
									System.out.println("Filas afectadas: "+count);
									pstmtVendedor.close();
							
						 }
						 catch (Exception e1) {
						   e1.printStackTrace();
						 }
						
						ListarVendedor.loadTableVendedor(null);	
						dispose();
					}
				}
				else {
					if (selected instanceof Vendedor) {
						if (txtcedula.getText().equalsIgnoreCase("") || txtDireccion.getText().equalsIgnoreCase("") || txtNombre.getText().equalsIgnoreCase("") || txtPassword.getPassword().length == 0
								|| txtTelefono.getText().equalsIgnoreCase("") || txtUsername.getText().equalsIgnoreCase("") || labelIcon.getIcon() == null) {
							JOptionPane.showMessageDialog(null, "Debes llenar todos los campos!", "Modificar Vendedor", JOptionPane.WARNING_MESSAGE);
						}else {
							try {
								String SQL = "UPDATE persona SET nombre = ?, idMunicipio = ?, direccion = ?, telefono = ?, foto = ? WHERE cedulaPersona = ?";
								PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
								pstmt.setString(1, txtNombre.getText());
								pstmt.setInt(2, Integer.parseInt(cbxMunicipio.getSelectedItem().toString().substring(0, cbxMunicipio.getSelectedItem().toString().indexOf("-"))));
								pstmt.setString(3, txtDireccion.getText());
								pstmt.setString(4, txtTelefono.getText());
								if (f != null) {
									FileInputStream convertir_imagen = new FileInputStream(f);
									pstmt.setBlob(5, convertir_imagen, f.length());
									pstmt.setString(6, selected.getCedula());
								}else {
									pstmt.setBlob(5, selected.getImagen());
									pstmt.setString(6, selected.getCedula());
								}
		
								System.out.println("Cedula Actualizada: " + selected.getCedula());
								int count = pstmt.executeUpdate();
								System.out.println("Filas afectadas: "+count);
								
								pstmt.close();
								
								JOptionPane.showMessageDialog(null, "El vendedor ha sido modificado satisfactoriamente !", "Modificar vendedor", JOptionPane.CLOSED_OPTION);
								
							 } catch (Exception e1) {
								   e1.printStackTrace();
								   JOptionPane.showMessageDialog(null, "El vendedor no pudo ser modificado correctamente !", "Modificar vendedor", JOptionPane.CLOSED_OPTION);
							 }
							
							try {
								String SQL = "UPDATE empleado SET password = ? WHERE cedulaPersona = ?";
								PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
								pstmt.setBytes(1,  txtPassword.getText().getBytes());
								pstmt.setString(2, selected.getCedula());
		
								System.out.println("PASSWORD: " + txtPassword.getText() + "   " + "PASSWORD BYTE: " + txtPassword.getText().getBytes());
								
								int count = pstmt.executeUpdate();
								System.out.println("Filas afectadas: "+count);
								
								pstmt.close();
								
							 } catch (Exception e1) {
								   e1.printStackTrace();
							 }
							
							ListarVendedor.loadTableVendedor(null);	
						}
				}
				if (selected instanceof Administrador){
					if (txtcedula.getText().equalsIgnoreCase("") || txtDireccion.getText().equalsIgnoreCase("") || txtNombre.getText().equalsIgnoreCase("") || txtPassword.getPassword().length == 0
							|| txtTelefono.getText().equalsIgnoreCase("") || txtUsername.getText().equalsIgnoreCase("") || labelIcon.getIcon() == null) {
						JOptionPane.showMessageDialog(null, "Debes llenar todos los campos!", "Modificar Vendedor", JOptionPane.WARNING_MESSAGE);
					}else {
						try {
							String SQL = "UPDATE persona SET nombre = ?, idMunicipio = ?, direccion = ?, telefono = ? WHERE cedulaPersona = ?";
							PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
							pstmt.setString(1, txtNombre.getText());
							pstmt.setInt(2, Integer.parseInt(cbxMunicipio.getSelectedItem().toString().substring(0, cbxMunicipio.getSelectedItem().toString().indexOf("-"))));
							pstmt.setString(3, txtDireccion.getText());
							pstmt.setString(4, txtTelefono.getText());
							pstmt.setString(5, selected.getCedula());

	
							System.out.println("Cedula Actualizada: " + selected.getCedula());
							int count = pstmt.executeUpdate();
							System.out.println("Filas afectadas: "+count);
							
							pstmt.close();
							
							JOptionPane.showMessageDialog(null, "El vendedor ha sido modificado satisfactoriamente !", "Modificar vendedor", JOptionPane.CLOSED_OPTION);
							
						 } catch (Exception e1) {
							   e1.printStackTrace();
							   JOptionPane.showMessageDialog(null, "El vendedor no pudo ser modificado correctamente !", "Modificar vendedor", JOptionPane.CLOSED_OPTION);
						 }
						
						try {
							String SQL = "UPDATE empleado SET password = ?, username = ? WHERE cedulaPersona = ?";
							PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
							pstmt.setBytes(1,  txtPassword.getText().getBytes());
							pstmt.setString(2, txtUsername.getText());
							pstmt.setString(3, selected.getCedula());
								
							int count = pstmt.executeUpdate();
							System.out.println("Filas afectadas: "+count);
							
							pstmt.close();
							
						 } catch (Exception e1) {
							   e1.printStackTrace();
						 }
					}
				}
			}
		}
		});
		lblConfirmar.setOpaque(true);
		lblConfirmar.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmar.setForeground(Color.WHITE);
		lblConfirmar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblConfirmar.setBackground(new Color(0, 155, 124));
		lblConfirmar.setBounds(293, 642, 225, 55);
		panelRegistro.add(lblConfirmar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 155, 124));
		panel_1.setBounds(34, 64, 390, 4);
		panelRegistro.add(panel_1);
		
		JLabel lblMunicipio = new JLabel("Municipio");
		lblMunicipio.setIcon(new ImageIcon(RegVendedor.class.getResource("/Imagenes/ubicacion.png")));
		lblMunicipio.setForeground(Color.BLACK);
		lblMunicipio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMunicipio.setBounds(34, 493, 136, 55);
		panelRegistro.add(lblMunicipio);
		
		JLabel lblDireccin_1_1 = new JLabel("Dirección:");
		lblDireccin_1_1.setIcon(new ImageIcon(RegVendedor.class.getResource("/Imagenes/ubicacion.png")));
		lblDireccin_1_1.setForeground(Color.BLACK);
		lblDireccin_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDireccin_1_1.setBounds(34, 558, 136, 55);
		panelRegistro.add(lblDireccin_1_1);
		
		txtDireccion = new JTextField();
		txtDireccion.setForeground(new Color(0, 153, 153));
		txtDireccion.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtDireccion.setColumns(10);
		txtDireccion.setBackground(Color.WHITE);
		txtDireccion.setBounds(190, 558, 344, 50);
		panelRegistro.add(txtDireccion);
		
		cbxProvincia = new JComboBox();
		cbxProvincia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarComboBoxMunicipio();
			}
		});
		cbxProvincia.setBounds(190, 434, 344, 50);
		panelRegistro.add(cbxProvincia);
		
		cbxMunicipio = new JComboBox();
		cbxMunicipio.setBounds(190, 493, 344, 50);
		panelRegistro.add(cbxMunicipio);
		
		if (selected != null) {
			
			lblConfirmar.setText("Modificar");
			cargarComboBox();
			txtcedula.setEnabled(true);
			txtUsername.setEnabled(true);
			
			if (selected instanceof Administrador) {
				lblTitle.setText("Modificar Administrador:");
				lblCargarImagen.setEnabled(false);
				txtcedula.setEnabled(false);
				labelIcon.setIcon(new ImageIcon(Home.class.getResource("/Imagenes/AdminDefectIcon.png")));
				txtcedula.setText(selected.getCedula());
				txtDireccion.setText(selected.getDireccion());
				txtNombre.setText(selected.getNombre());
				txtPassword.setText(selected.getPassword());
				txtTelefono.setText(selected.getTelefono());
				txtUsername.setText(selected.getUsername());
				
				String direccion, municipio, provincia;
				int idMunicipio;
				int idProvincia;
				
				ResultSet rs = null;
				ResultSet rs2 = null;

				try {
					String SQL = "SELECT * FROM persona WHERE cedulaPersona = ?;";
					PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
					pstmt.setString(1, selected.getCedula());
					rs = pstmt.executeQuery();
					rs.next();
					
					direccion = rs.getString("direccion");
					idMunicipio = rs.getInt("idMunicipio");
					
					String SQL2 = "SELECT provincia.nombre as nombreP, municipio.nombre as nombreM, provincia.idProvincia FROM provincia, persona, municipio WHERE cedulaPersona = ? and municipio.idProvincia = provincia.idProvincia and municipio.idMunicipio = persona.idMunicipio;";
					PreparedStatement pstmt2 = Conexion.getInstance().prepareStatement(SQL2);
					pstmt2.setString(1, selected.getCedula());
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
			else {
				cargarComboBox();
				loadEmpleado();
				lblTitle.setText("Modificar Vendedor:");
				
				txtcedula.setEnabled(false);
				txtUsername.setEnabled(false);
			}
			
		}
		else {
			cargarComboBox();
		}
		
	}
	private void clean() {
		txtcedula.setText("");
		txtDireccion.setText("");
		txtNombre.setText("");
		txtPassword.setText("");
		txtTelefono.setText("");
		txtUsername.setText("");
		labelIcon.setIcon(new ImageIcon());
	}
	private void loadEmpleado() throws IOException {
		
		txtcedula.setText(selected.getCedula());
		txtNombre.setText(selected.getNombre());
		txtPassword.setText(selected.getPassword());
		txtTelefono.setText(selected.getTelefono());
		txtUsername.setText(selected.getUsername());
		
		if (selected.getImagen() != null) {
			byte[] recuperar;
			try {
				recuperar = selected.getImagen().getBytes(1,  (int) selected.getImagen().length());
				BufferedImage img = ImageIO.read(new ByteArrayInputStream(recuperar));
				Image foto = img.getScaledInstance(labelIcon.getWidth(), labelIcon.getHeight(), Image.SCALE_SMOOTH);
				labelIcon.setIcon(new ImageIcon(foto));
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String direccion, municipio, provincia;
		int idMunicipio;
		int idProvincia;
		
		ResultSet rs = null;
		ResultSet rs2 = null;

		try {
			String SQL = "SELECT * FROM persona WHERE cedulaPersona = ?;";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setString(1, selected.getCedula());
			rs = pstmt.executeQuery();
			rs.next();
			
			direccion = rs.getString("direccion");
			idMunicipio = rs.getInt("idMunicipio");
			
			String SQL2 = "SELECT provincia.nombre as nombreP, municipio.nombre as nombreM, provincia.idProvincia FROM provincia, persona, municipio WHERE cedulaPersona = ? and municipio.idProvincia = provincia.idProvincia and municipio.idMunicipio = persona.idMunicipio;";
			PreparedStatement pstmt2 = Conexion.getInstance().prepareStatement(SQL2);
			pstmt2.setString(1, selected.getCedula());
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