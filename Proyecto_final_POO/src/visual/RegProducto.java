package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Cliente;
import logico.Conexion;
import logico.DiscoDuro;
import logico.MemoriaRam;
import logico.MicroProcesador;
import logico.MotherBoard;
import logico.Producto;
import logico.Tienda;

import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Cursor;
import javax.swing.JTextField;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class RegProducto extends JDialog {

	private JPanel contentPane;
	private JTextField txtMarca;
	private JTextField txtPrecio;
	private JPanel panelRegistro;
	Cliente selected = null;
	private JTextField txtNumSerie;
	private JTextField txtModeloTM;
	private JTextField txtModeloDiscoDuro;
	private JPanel panelDiscoDuro;
	private JTextField txtCapacidadDiscoDuro;
	private JTextField txtCapacidadMRAM;
	private JPanel panelMemoriaRam;
	private JTextField txtModeloMicroP;
	private JTextField txtVelProcesamiento;
	private JPanel panelTarjetaMadre;
	private JPanel panelMicroProcesador;
	private JRadioButton btnDiscoDuro;
	private JRadioButton btnMemoriaRam;
	private JRadioButton btnTarjetaMadre;
	private JRadioButton btnMicroProcesador;
	private JComboBox<String> cbxTipoRamTM;
	private JComboBox<String> cbxSocketTM;
	private JComboBox<String> cbxTipoMemoriaRam;
	private JComboBox<String> cbxSocketDiscoDuro;
	private JSpinner spnDispMin;
	private JSpinner spnDispReal;
	private JSpinner spnDispMax;
	private JComboBox<String> cbxSocketMicroP;
	private JLabel lblRegistrar;
	private JLabel lblTitle;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField txtModeloMRAM;
	private JComboBox<String> cbxTipoConexiones;
	Producto productSelected = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegProducto dialog = new RegProducto(null);
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
	public RegProducto(Producto producto) {
		productSelected = producto;
		
		setUndecorated(true);
		setBounds(100, 100, 700, 750);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(36, 37, 38));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		panelMicroProcesador = new JPanel();
		panelMicroProcesador.setVisible(false);
		
		panelTarjetaMadre = new JPanel();
		panelTarjetaMadre.setVisible(false);
		panelTarjetaMadre.setLayout(null);
		panelTarjetaMadre.setBackground(Color.WHITE);
		panelTarjetaMadre.setBounds(11, 465, 669, 215);
		panel.add(panelTarjetaMadre);
		
		JLabel lblModeloTM = new JLabel("Modelo:");
		lblModeloTM.setForeground(Color.BLACK);
		lblModeloTM.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblModeloTM.setBounds(20, 5, 82, 45);
		panelTarjetaMadre.add(lblModeloTM);
		
		txtModeloTM = new JTextField();
		txtModeloTM.setForeground(new Color(0, 153, 153));
		txtModeloTM.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtModeloTM.setColumns(10);
		txtModeloTM.setBackground(Color.WHITE);
		txtModeloTM.setBounds(159, 5, 500, 45);
		panelTarjetaMadre.add(txtModeloTM);
		
		JLabel lblSocketTM = new JLabel("Socket:");
		lblSocketTM.setForeground(Color.BLACK);
		lblSocketTM.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSocketTM.setBounds(20, 60, 82, 45);
		panelTarjetaMadre.add(lblSocketTM);
		
		cbxSocketTM = new JComboBox<String>();
		cbxSocketTM.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cbxSocketTM.setModel(new DefaultComboBoxModel<String>(new String[] {"<Seleccione>", "LGA", "hexLGA", "PGA", "mPGA"}));
		cbxSocketTM.setBounds(159, 55, 500, 45);
		panelTarjetaMadre.add(cbxSocketTM);
		
		JLabel lblTipoRamTM = new JLabel("Tipo de Ram:");
		lblTipoRamTM.setForeground(Color.BLACK);
		lblTipoRamTM.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTipoRamTM.setBounds(20, 110, 138, 45);
		panelTarjetaMadre.add(lblTipoRamTM);
		
		cbxTipoRamTM = new JComboBox<String>();
		cbxTipoRamTM.setModel(new DefaultComboBoxModel<String>(new String[] {"<Seleccione>", "DDR", "DDR-2", "DDR-3", "DDR-4", "SDRAM", "RDRAM"}));
		cbxTipoRamTM.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cbxTipoRamTM.setBounds(159, 105, 500, 45);
		panelTarjetaMadre.add(cbxTipoRamTM);
		
		JLabel lblTipoConexiones = new JLabel("Tipo de Conexiones HD:");
		lblTipoConexiones.setForeground(Color.BLACK);
		lblTipoConexiones.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTipoConexiones.setBounds(20, 155, 230, 45);
		panelTarjetaMadre.add(lblTipoConexiones);
		
		cbxTipoConexiones = new JComboBox<String>();
		cbxTipoConexiones.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "IDE", "SATA", "mSATA", "SCSI", "PC1"}));
		cbxTipoConexiones.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cbxTipoConexiones.setBounds(259, 155, 400, 45);
		panelTarjetaMadre.add(cbxTipoConexiones);
		panelMicroProcesador.setLayout(null);
		panelMicroProcesador.setBackground(Color.WHITE);
		panelMicroProcesador.setBounds(11, 465, 669, 215);
		panel.add(panelMicroProcesador);
		
		lblNewLabel_1 = new JLabel("GHz");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(605, 151, 50, 45);
		panelMicroProcesador.add(lblNewLabel_1);
		
		JLabel lblModeloMicroP = new JLabel("Modelo:");
		lblModeloMicroP.setForeground(Color.BLACK);
		lblModeloMicroP.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblModeloMicroP.setBounds(33, 12, 112, 55);
		panelMicroProcesador.add(lblModeloMicroP);
		
		JLabel lblSocketMicroP = new JLabel("Socket:");
		lblSocketMicroP.setForeground(Color.BLACK);
		lblSocketMicroP.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSocketMicroP.setBounds(33, 79, 112, 55);
		panelMicroProcesador.add(lblSocketMicroP);
		
		cbxSocketMicroP = new JComboBox<String>();
		cbxSocketMicroP.setModel(new DefaultComboBoxModel<String>(new String[] {"<Seleccione>", "LGA", "hexLGA", "PGA", "mPGA"}));
		cbxSocketMicroP.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cbxSocketMicroP.setBounds(179, 84, 480, 45);
		panelMicroProcesador.add(cbxSocketMicroP);
		
		txtModeloMicroP = new JTextField();
		txtModeloMicroP.setForeground(new Color(0, 153, 153));
		txtModeloMicroP.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtModeloMicroP.setColumns(10);
		txtModeloMicroP.setBackground(Color.WHITE);
		txtModeloMicroP.setBounds(179, 17, 480, 45);
		panelMicroProcesador.add(txtModeloMicroP);
		
		JLabel lblVelProcesamiento = new JLabel("Velocidad de procesamiento:");
		lblVelProcesamiento.setForeground(Color.BLACK);
		lblVelProcesamiento.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVelProcesamiento.setBounds(33, 146, 268, 55);
		panelMicroProcesador.add(lblVelProcesamiento);
		
		txtVelProcesamiento = new JTextField();
		txtVelProcesamiento.setForeground(new Color(0, 153, 153));
		txtVelProcesamiento.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtVelProcesamiento.setColumns(10);
		txtVelProcesamiento.setBackground(Color.WHITE);
		txtVelProcesamiento.setBounds(300, 151, 300, 45);
		panelMicroProcesador.add(txtVelProcesamiento);
		
		panelMemoriaRam = new JPanel();
		panelMemoriaRam.setVisible(false);
		panelMemoriaRam.setLayout(null);
		panelMemoriaRam.setBackground(Color.WHITE);
		panelMemoriaRam.setBounds(11, 465, 669, 215);
		panel.add(panelMemoriaRam);
		
		JLabel lblNewLabel = new JLabel("GB");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(610, 11, 50, 50);
		panelMemoriaRam.add(lblNewLabel);
		
		JLabel lblCapacidadMRam = new JLabel("Capacidad:");
		lblCapacidadMRam.setForeground(Color.BLACK);
		lblCapacidadMRam.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCapacidadMRam.setBounds(33, 11, 112, 55);
		panelMemoriaRam.add(lblCapacidadMRam);
		
		JLabel lblTipoMemoriaRam = new JLabel("Tipo de Memoria:");
		lblTipoMemoriaRam.setForeground(Color.BLACK);
		lblTipoMemoriaRam.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTipoMemoriaRam.setBounds(33, 132, 165, 55);
		panelMemoriaRam.add(lblTipoMemoriaRam);
		
		JLabel lblModeloMemoriaRam = new JLabel("Modelo: ");
		lblModeloMemoriaRam.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblModeloMemoriaRam.setForeground(Color.BLACK);
		lblCapacidadMRam.setForeground(Color.BLACK);
		lblCapacidadMRam.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblModeloMemoriaRam.setBounds(33, 68, 100, 55);
		panelMemoriaRam.add(lblModeloMemoriaRam);
		
		cbxTipoMemoriaRam = new JComboBox<String>();
		cbxTipoMemoriaRam.setModel(new DefaultComboBoxModel<String>(new String[] {"<Seleccione>", "DDR", "DDR-2", "DDR-3", "DDR-4", "SDRAM", "RDRAM"}));
		cbxTipoMemoriaRam.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cbxTipoMemoriaRam.setBounds(215, 135, 444, 50);
		panelMemoriaRam.add(cbxTipoMemoriaRam);
		
		txtCapacidadMRAM = new JTextField();
		txtCapacidadMRAM.setForeground(new Color(0, 153, 153));
		txtCapacidadMRAM.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtCapacidadMRAM.setColumns(10);
		txtCapacidadMRAM.setBackground(Color.WHITE);
		txtCapacidadMRAM.setBounds(215, 11, 380, 50);
		panelMemoriaRam.add(txtCapacidadMRAM);
		
		txtModeloMRAM = new JTextField();
		txtModeloMRAM.setForeground(Color.BLACK);
		txtModeloMRAM.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtModeloMRAM.setBounds(215, 73, 444, 50);
		panelMemoriaRam.add(txtModeloMRAM);
		txtModeloMRAM.setColumns(10);
		
		panelDiscoDuro = new JPanel();
		panelDiscoDuro.setLayout(null);
		panelDiscoDuro.setBackground(Color.WHITE);
		panelDiscoDuro.setBounds(11, 465, 669, 215);
		panel.add(panelDiscoDuro);
		
		JLabel lblModeloDiscoDuro = new JLabel("Modelo:");
		lblModeloDiscoDuro.setForeground(Color.BLACK);
		lblModeloDiscoDuro.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblModeloDiscoDuro.setBounds(33, 12, 125, 55);
		panelDiscoDuro.add(lblModeloDiscoDuro);
		
		txtModeloDiscoDuro = new JTextField();
		txtModeloDiscoDuro.setForeground(new Color(0, 153, 153));
		txtModeloDiscoDuro.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtModeloDiscoDuro.setColumns(10);
		txtModeloDiscoDuro.setBackground(Color.WHITE);
		txtModeloDiscoDuro.setBounds(190, 16, 469, 50);
		panelDiscoDuro.add(txtModeloDiscoDuro);
		
		JLabel lblCapacidadDiscoDuro = new JLabel("Capacidad:");
		lblCapacidadDiscoDuro.setForeground(Color.BLACK);
		lblCapacidadDiscoDuro.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCapacidadDiscoDuro.setBounds(33, 79, 112, 55);
		panelDiscoDuro.add(lblCapacidadDiscoDuro);
		
		JLabel lblTipoSocketDiscoDuro = new JLabel("Tipo de Socket:");
		lblTipoSocketDiscoDuro.setForeground(Color.BLACK);
		lblTipoSocketDiscoDuro.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTipoSocketDiscoDuro.setBounds(33, 146, 154, 55);
		panelDiscoDuro.add(lblTipoSocketDiscoDuro);
		
		cbxSocketDiscoDuro = new JComboBox<String>();
		cbxSocketDiscoDuro.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cbxSocketDiscoDuro.setModel(new DefaultComboBoxModel<String>(new String[] {"<Seleccione>", "LGA", "hexLGA", "PGA", "mPGA"}));
		cbxSocketDiscoDuro.setBounds(190, 148, 469, 50);
		panelDiscoDuro.add(cbxSocketDiscoDuro);
		
		txtCapacidadDiscoDuro = new JTextField();
		txtCapacidadDiscoDuro.setForeground(new Color(0, 153, 153));
		txtCapacidadDiscoDuro.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtCapacidadDiscoDuro.setColumns(10);
		txtCapacidadDiscoDuro.setBackground(Color.WHITE);
		txtCapacidadDiscoDuro.setBounds(190, 82, 400, 50);
		panelDiscoDuro.add(txtCapacidadDiscoDuro);
		
		lblNewLabel_2 = new JLabel("GB");
		lblNewLabel_2.setForeground(Color.BLACK);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(600, 82, 56, 50);
		panelDiscoDuro.add(lblNewLabel_2);
		
		panelRegistro = new JPanel();
		panelRegistro.setLayout(null);
		panelRegistro.setBackground(Color.WHITE);
		panelRegistro.setBounds(11, 12, 669, 395);
		panel.add(panelRegistro);
		
		txtPrecio = new JTextField();
		txtPrecio.setForeground(new Color(0, 153, 153));
		txtPrecio.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtPrecio.setColumns(10);
		txtPrecio.setBackground(Color.WHITE);
		txtPrecio.setBounds(192, 210, 467, 45);
		panelRegistro.add(txtPrecio);
		
		txtMarca = new JTextField();
		txtMarca.setForeground(new Color(0, 153, 153));
		txtMarca.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtMarca.setColumns(10);
		txtMarca.setBackground(Color.WHITE);
		txtMarca.setBounds(192, 147, 467, 45);
		panelRegistro.add(txtMarca);
		
		JLabel lblDispMax = new JLabel("Disp. M\u00E1xima:");
		lblDispMax.setForeground(Color.BLACK);
		lblDispMax.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDispMax.setBounds(364, 330, 136, 55);
		panelRegistro.add(lblDispMax);
		
		JLabel lblDispMin = new JLabel("Disp. M\u00EDnima:");
		lblDispMin.setForeground(Color.BLACK);
		lblDispMin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDispMin.setBounds(33, 330, 162, 55);
		panelRegistro.add(lblDispMin);
		
		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setForeground(Color.BLACK);
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCantidad.setBounds(33, 268, 162, 55);
		panelRegistro.add(lblCantidad);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setForeground(Color.BLACK);
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPrecio.setBounds(33, 205, 125, 55);
		panelRegistro.add(lblPrecio);
		
		lblTitle = new JLabel("Registrar Producto:");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblTitle.setBounds(33, 13, 322, 55);
		panelRegistro.add(lblTitle);
		
		JLabel lblNumSerie = new JLabel("Num. De serie:");
		lblNumSerie.setForeground(Color.BLACK);
		lblNumSerie.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNumSerie.setBounds(33, 79, 196, 55);
		panelRegistro.add(lblNumSerie);
		
		txtNumSerie = new JTextField();
		txtNumSerie.setForeground(new Color(0, 153, 153));
		txtNumSerie.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtNumSerie.setColumns(10);
		txtNumSerie.setBackground(Color.WHITE);
		txtNumSerie.setBounds(192, 84, 467, 45);
		panelRegistro.add(txtNumSerie);
		
		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setForeground(Color.BLACK);
		lblMarca.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMarca.setBounds(33, 142, 125, 55);
		panelRegistro.add(lblMarca);
		
		final JLabel lblX = new JLabel("X");
		lblX.setBounds(602, 0, 57, 55);
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
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(0, 155, 124));
		panel1.setBounds(34, 64, 420, 4);
		panelRegistro.add(panel1);
		
		spnDispMin = new JSpinner();
		spnDispMin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		spnDispMin.setBounds(192, 335, 100, 45);
		panelRegistro.add(spnDispMin);
		
		spnDispMax = new JSpinner();
		spnDispMax.setFont(new Font("Tahoma", Font.PLAIN, 20));
		spnDispMax.setBounds(559, 335, 100, 45);
		panelRegistro.add(spnDispMax);
		
		spnDispReal = new JSpinner();
		spnDispReal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		spnDispReal.setBounds(192, 273, 467, 45);
		panelRegistro.add(spnDispReal);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(11, 415, 669, 42);
		panel.add(panel_2);
		
		btnDiscoDuro = new JRadioButton("Disco Duro");
		btnDiscoDuro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelDiscoDuro.setVisible(true);
				panelMemoriaRam.setVisible(false);
				panelTarjetaMadre.setVisible(false);
				panelMicroProcesador.setVisible(false);
				
				btnDiscoDuro.setSelected(true);
				btnMemoriaRam.setSelected(false);
				btnTarjetaMadre.setSelected(false);
				btnMicroProcesador.setSelected(false);
			}
		});
		btnDiscoDuro.setSelected(true);
		btnDiscoDuro.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDiscoDuro.setForeground(new Color(0, 0, 0));
		btnDiscoDuro.setBounds(11, 9, 133, 25);
		panel_2.add(btnDiscoDuro);
		
		btnMemoriaRam = new JRadioButton("Memoria Ram");
		btnMemoriaRam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelDiscoDuro.setVisible(false);
				panelMemoriaRam.setVisible(true);
				panelTarjetaMadre.setVisible(false);
				panelMicroProcesador.setVisible(false);
				
				btnDiscoDuro.setSelected(false);
				btnMemoriaRam.setSelected(true);
				btnTarjetaMadre.setSelected(false);
				btnMicroProcesador.setSelected(false);
			}
		});
		btnMemoriaRam.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnMemoriaRam.setForeground(new Color(0, 0, 0));
		btnMemoriaRam.setBounds(155, 9, 150, 25);
		panel_2.add(btnMemoriaRam);
		
		btnTarjetaMadre = new JRadioButton("Tarjeta Madre");
		btnTarjetaMadre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelDiscoDuro.setVisible(false);
				panelMemoriaRam.setVisible(false);
				panelTarjetaMadre.setVisible(true);
				panelMicroProcesador.setVisible(false);
				
				btnDiscoDuro.setSelected(false);
				btnMemoriaRam.setSelected(false);
				btnTarjetaMadre.setSelected(true);
				btnMicroProcesador.setSelected(false);
			}
		});
		btnTarjetaMadre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTarjetaMadre.setForeground(new Color(0, 0, 0));
		btnTarjetaMadre.setBounds(316, 9, 151, 25);
		panel_2.add(btnTarjetaMadre);
		
		btnMicroProcesador = new JRadioButton("Micro Procesador");
		btnMicroProcesador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelDiscoDuro.setVisible(false);
				panelMemoriaRam.setVisible(false);
				panelTarjetaMadre.setVisible(false);
				panelMicroProcesador.setVisible(true);
				
				btnDiscoDuro.setSelected(false);
				btnMemoriaRam.setSelected(false);
				btnTarjetaMadre.setSelected(false);
				btnMicroProcesador.setSelected(true);
			}
		});
		btnMicroProcesador.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnMicroProcesador.setForeground(new Color(0, 0, 0));
		btnMicroProcesador.setBounds(478, 9, 176, 25);
		panel_2.add(btnMicroProcesador);
		
		JLabel lblCancelar = new JLabel("Cancelar");
		lblCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblCancelar.setBounds(80, 687, 225, 45);
		panel.add(lblCancelar);
		lblCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		lblCancelar.setForeground(Color.WHITE);
		lblCancelar.setBackground(new Color(0, 155, 124));
		lblCancelar.setOpaque(true);
		lblCancelar.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancelar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		String boton = null;
		if (productSelected == null) {
			boton = "Registrar";
		}else {
			boton = "Modificar";
		}
		lblRegistrar = new JLabel(boton);
		lblRegistrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean continuar = true;
				String numSerie = txtNumSerie.getText();
				String marca = txtMarca.getText();
				float precio = Float.valueOf(txtPrecio.getText().toString());
				int dispReal =  Integer.valueOf(spnDispReal.getValue().toString());
				int dispMax =  Integer.valueOf(spnDispMax.getValue().toString());
				int dispMin =  Integer.valueOf(spnDispMin.getValue().toString());
				// float precio = new Float(txtPrecio.getText().toString());
				// int dispReal = new Integer(spnDispReal.getValue().toString());
				// int dispMax = new Integer(spnDispMax.getValue().toString());
				// int dispMin = new Integer(spnDispMin.getValue().toString());
				
				if (productSelected == null) {
					if (precio < 0 || txtPrecio.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El precio del producto no esta correcto.", "Registro de Producto", JOptionPane.WARNING_MESSAGE);
						txtPrecio.setText("");
						continuar = false;
					}
					else if (dispMax < dispMin) {
						JOptionPane.showMessageDialog(null, "Disponibilidad maxima no puede ser menor que la minima.", "Registro de Producto", JOptionPane.WARNING_MESSAGE);
						continuar = false;
					}
					else if(dispReal > dispMax) {
						JOptionPane.showMessageDialog(null, "Disponibilidad real no puede ser mayor que la maxima.", "Registro de Producto", JOptionPane.WARNING_MESSAGE);
						continuar = false;
					}
					
					if(btnDiscoDuro.isSelected()) {
						String modelo = txtModeloDiscoDuro.getText();
						String socket = cbxSocketDiscoDuro.getSelectedItem().toString();
						int capacidad =  Integer.valueOf(txtCapacidadDiscoDuro.getText());
						if (capacidad <= 0) {
							JOptionPane.showMessageDialog(null, "Capacidad no puede ser menor o igual a cero.", "Registro de Producto", JOptionPane.WARNING_MESSAGE);
							continuar = false;
						}
						if (continuar){
							// productSelected = new DiscoDuro(numSerie, dispReal, precio, marca, dispMin, dispMax, modelo, capacidad, socket);
							try {
								String SQL = "INSERT INTO producto"
								+ "(numSerie, cantStock, precio, marca, modelo, dispMin, dispMax, capacidad, socket, tipoProducto, unitCapacidad)"
								+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
								PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
								pstmt.setString(1, numSerie);
								pstmt.setInt(2, dispReal);
								pstmt.setFloat(3, precio);
								pstmt.setString(4, marca);
								pstmt.setString(5, modelo);
								pstmt.setInt(6, dispMin);
								pstmt.setInt(7, dispMax);
								pstmt.setInt(8, capacidad);
								pstmt.setString(9, socket);
								pstmt.setString(10,"Disco Duro");
								pstmt.setString(11,"GB");
								int count = pstmt.executeUpdate();		
								System.out.println("Filas afectadas: "+count);
							  	pstmt.close();	
							 }
							 catch (Exception e1) {
							   e1.printStackTrace();
							   JOptionPane.showMessageDialog(null, "El producto no pudo ser registrado!", "Registro de Producto", JOptionPane.ERROR_MESSAGE);
							   continuar = false;
							 }
						}
					}
					else if(btnMemoriaRam.isSelected()) {
						String tipo = cbxTipoMemoriaRam.getSelectedItem().toString();
						String modelo = txtModeloMRAM.getText();
						int capacidad = Integer.valueOf(txtCapacidadMRAM.getText());
						if (capacidad <= 0) {
							JOptionPane.showMessageDialog(null, "Capacidad no puede ser menor o igual a cero.", "Registro de Producto", JOptionPane.WARNING_MESSAGE);
							continuar = false;
						}
						if (continuar){
							// productSelected = new MemoriaRam(numSerie, dispReal, precio, marca, dispMin, dispMax, capacidad, tipo);
							try {
								String SQL = "INSERT INTO producto"
								+ "(numSerie, cantStock, precio, marca, modelo, dispMin, dispMax, capacidad, tipoRam, tipoProducto, unitCapacidad)"
								+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
								PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
								pstmt.setString(1, numSerie);
								pstmt.setInt(2, dispReal);
								pstmt.setFloat(3, precio);
								pstmt.setString(4, marca);
								pstmt.setString(5, modelo);
								pstmt.setInt(6, dispMin);
								pstmt.setInt(7, dispMax);
								pstmt.setInt(8, capacidad);
								pstmt.setString(9, tipo);
								pstmt.setString(10,"Memoria Ram");
								pstmt.setString(11,"GB");
								int count = pstmt.executeUpdate();
								System.out.println("Filas afectadas: "+count);
								pstmt.close();
							 }
							 catch (Exception e1) {
							   e1.printStackTrace();
							   JOptionPane.showMessageDialog(null, "El producto no pudo ser registrado!", "Registro de Producto", JOptionPane.ERROR_MESSAGE);
							   continuar = false;
							 }
						}
					}
					else if(btnMicroProcesador.isSelected()) {
						String modelo = txtModeloMicroP.getText();
						String socket = cbxSocketMicroP.getSelectedItem().toString();
						float velocidad =  Float.valueOf(txtVelProcesamiento.getText());
						if (velocidad <= 0) {
							JOptionPane.showMessageDialog(null, "Velocidad no puede ser menor o igual a cero.", "Registro de Producto", JOptionPane.WARNING_MESSAGE);
							continuar = false;
						}
						if (continuar){
							// productSelected = new MicroProcesador(numSerie, dispReal, precio, marca, dispMin, dispMax, modelo, socket, velocidad);
							try {
								String SQL = "INSERT INTO producto"
								+ "(numSerie, cantStock, precio, marca, modelo, dispMin, dispMax, socket, velocidadProcesamiento, tipoProducto, unitCapacidad)"
								+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
								PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
								pstmt.setString(1, numSerie);
								pstmt.setInt(2, dispReal);
								pstmt.setFloat(3, precio);
								pstmt.setString(4, marca);
								pstmt.setString(5, modelo);
								pstmt.setInt(6, dispMin);
								pstmt.setInt(7, dispMax);
								pstmt.setString(8, socket);
								pstmt.setFloat(9, velocidad);
								pstmt.setString(10,"Micro Procesador");
								pstmt.setString(11,"GHZ");
								int count = pstmt.executeUpdate();
								System.out.println("Filas afectadas: "+count);
							  	pstmt.close();
							 }
							 catch (Exception e1) {
							   e1.printStackTrace();
							   JOptionPane.showMessageDialog(null, "El producto no pudo ser registrado!", "Registro de Producto", JOptionPane.ERROR_MESSAGE);
							   continuar = false;
							 }
						}
					}
					else if(btnTarjetaMadre.isSelected()) {
						String modelo = txtModeloTM.getText();
						String socket = cbxSocketTM.getSelectedItem().toString();
						String tipoRam = cbxTipoRamTM.getSelectedItem().toString();
						String tipoConexiones = cbxTipoConexiones.getSelectedItem().toString();
						// productSelected = new MotherBoard(numSerie, dispReal, precio, marca, dispMin, dispMax, modelo, socket, tipoRam);
						try {
							String SQL = "INSERT INTO producto"
							+ "(numSerie, cantStock, precio, marca, modelo, dispMin, dispMax, socket, tipoRam, tipoConexionHD, tipoProducto)"
							+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
							PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
							pstmt.setString(1, numSerie);
							pstmt.setInt(2, dispReal);
							pstmt.setFloat(3, precio);
							pstmt.setString(4, marca);
							pstmt.setString(5, modelo);
							pstmt.setInt(6, dispMin);
							pstmt.setInt(7, dispMax);
							pstmt.setString(8, socket);
							pstmt.setString(9, tipoRam);
							pstmt.setString(10, tipoConexiones);
							pstmt.setString(11,"Tarjeta Madre");
							int count = pstmt.executeUpdate();
							System.out.println("Filas afectadas: "+count);
							pstmt.close();							
						 }
						 catch (Exception e1) {
						   e1.printStackTrace();
						   JOptionPane.showMessageDialog(null, "El producto no pudo ser registrado!", "Registro de Producto", JOptionPane.ERROR_MESSAGE);
						   continuar = false;
						 }
					}
					if (continuar) {
						if (dispReal <= dispMin){
							int option = JOptionPane.showConfirmDialog(null, "La cantidad es menor o igual a la disponibilida minima. Desea crear una orden de compra para este pedido?", "Orden de compra",JOptionPane.YES_NO_OPTION);
							if (option == JOptionPane.YES_OPTION) {
								try {
									String SQL = "EXEC PCD_crear_orden_compra @numSerie = ?;";
									PreparedStatement pstmt;
									pstmt = Conexion.getInstance().prepareStatement(SQL);
									pstmt.setString(1, numSerie);
									pstmt.executeQuery();
									pstmt.close();
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							}
						}
						Tienda.getInstance().addProducto(productSelected);
						JOptionPane.showMessageDialog(null, "El producto ha sido registrado satisfactoriamente !", "Registro de Producto", JOptionPane.INFORMATION_MESSAGE);
						productSelected = null;
						clean();	
						ListarProducto.loadTableProductos(0);
					}	
				}else {
					if (precio < 0 || txtPrecio.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El precio del producto no esta correcto.", "Registro de Producto", JOptionPane.WARNING_MESSAGE);
						txtPrecio.setText("");
					}
					else if (dispMax < dispMin) {
						JOptionPane.showMessageDialog(null, "Disponibilidad maxima no puede ser menor que la minima.", "Registro de Producto", JOptionPane.WARNING_MESSAGE);
					}
					else if(dispReal > dispMax) {
						JOptionPane.showMessageDialog(null, "Disponibilidad real no puede ser mayor que la maxima.", "Registro de Producto", JOptionPane.WARNING_MESSAGE);
					}
					else {
						//Esto es modificar un producto. (Falta por probar)
						productSelected.setPrecio( Float.valueOf(txtPrecio.getText().toString()));
						productSelected.setCantidad( Integer.valueOf(spnDispReal.getValue().toString()));
						productSelected.setDispMax( Integer.valueOf(spnDispMax.getValue().toString()));
						productSelected.setDispMin( Integer.valueOf(spnDispMin.getValue().toString()));

						try {
							String SQL = "UPDATE producto SET precio = ?, cantStock = ?, dispMax = ?, dispMin = ? WHERE numSerie = ?;";
							PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
							pstmt.setFloat(1, Float.valueOf(productSelected.getPrecio()));
							pstmt.setInt(2, Integer.valueOf(productSelected.getCantidad()));
							pstmt.setInt(3, Integer.valueOf(productSelected.getDispMax()));
							pstmt.setInt(4, Integer.valueOf(productSelected.getDispMin()));
							pstmt.setString(5, productSelected.getNumSerie());
	
							int count = pstmt.executeUpdate();
							
							System.out.println("Filas afectadas: "+count);
							pstmt.close();
							JOptionPane.showMessageDialog(null, "El producto ha sido modificado satisfactoriamente !", "Modificar Producto", JOptionPane.CLOSED_OPTION);
						 }
						 catch (Exception e1) {
						   e1.printStackTrace();
						   JOptionPane.showMessageDialog(null, "El producto no pudo ser modificado correctamente !", "Modificar Producto", JOptionPane.CLOSED_OPTION);
						 }
						ListarProducto.loadTableProductos(0);
						Home.loadHome();
						dispose();
					}
				}		
			}
		});
		lblRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblRegistrar.setBounds(385, 687, 225, 45);
		panel.add(lblRegistrar);

		lblRegistrar.setOpaque(true);
		lblRegistrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrar.setForeground(Color.WHITE);
		lblRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRegistrar.setBackground(new Color(0, 155, 124));
		
		loadProducto(productSelected);
		if (productSelected != null) {
			lblTitle.setText("Modificar Producto:");
		}
	}
	
	private void loadProducto(Producto selected) {
		
		if (selected != null) {
			txtNumSerie.setText(selected.getNumSerie());
			txtNumSerie.setEnabled(false);
			txtMarca.setText(selected.getMarca());
			txtMarca.setEnabled(false);
			txtPrecio.setText(String.valueOf(selected.getPrecio()));
			spnDispReal.setValue(selected.getCantidad()); //Esto debe de tomarse en cuenta para la modificacion final
			spnDispMax.setValue(selected.getDispMax());
			spnDispMin.setValue(selected.getDispMin());
			
			spnDispReal.setEnabled(false);
			//spnDispMax.setEnabled(false);
			//spnDispMin.setEnabled(false);
			
			if (selected.getTipoProducto().equalsIgnoreCase(("Disco Duro"))) {
				btnMemoriaRam.setEnabled(false);
				btnMicroProcesador.setEnabled(false);
				btnTarjetaMadre.setEnabled(false);
				txtModeloDiscoDuro.setText(((DiscoDuro)selected).getModelo());
				txtModeloDiscoDuro.setEnabled(false);
				txtCapacidadDiscoDuro.setText(Integer.toString(((DiscoDuro)selected).getCapacidad()));
				txtCapacidadDiscoDuro.setEnabled(false);
				cbxSocketDiscoDuro.setEnabled(false);
				cbxSocketDiscoDuro.setSelectedItem(((DiscoDuro)selected).getSocket());
				
			}else if (selected.getTipoProducto().equalsIgnoreCase("Memoria Ram")) {
				panelDiscoDuro.setVisible(false);
				panelMemoriaRam.setVisible(true);
				btnDiscoDuro.setSelected(false);
				btnMemoriaRam.setSelected(true);
				btnDiscoDuro.setEnabled(false);
				btnMicroProcesador.setEnabled(false);
				btnTarjetaMadre.setEnabled(false);
				txtCapacidadMRAM.setText(Integer.toString(((MemoriaRam)selected).getCapacidad()));
				txtCapacidadMRAM.setEnabled(false);
				txtModeloMRAM.setText(((MemoriaRam)selected).getModelo());
				txtCapacidadMRAM.setEnabled(false);
				cbxTipoMemoriaRam.setEnabled(false);
				cbxTipoMemoriaRam.setSelectedItem(((MemoriaRam)selected).getTipoMemoria());
				
			}else if (selected.getTipoProducto().equalsIgnoreCase("Micro Procesador") ) {
				panelDiscoDuro.setVisible(false);
				panelMicroProcesador.setVisible(true);
				btnDiscoDuro.setSelected(false);
				btnMicroProcesador.setSelected(true);
				btnMemoriaRam.setEnabled(false);
				btnDiscoDuro.setEnabled(false);
				btnTarjetaMadre.setEnabled(false);
				txtModeloMicroP.setText(((MicroProcesador)selected).getModelo());
				txtModeloMicroP.setEnabled(false);
				cbxSocketMicroP.setEnabled(false);
				cbxSocketMicroP.setSelectedItem(((MicroProcesador)selected).getSocket());
				txtVelProcesamiento.setText(Float.toString(((MicroProcesador)selected).getVelocidadProcesamiento()));
				txtVelProcesamiento.setEnabled(false);
				
			}else if (selected.getTipoProducto().equalsIgnoreCase("Tarjeta madre") ) {
				panelDiscoDuro.setVisible(false);
				panelTarjetaMadre.setVisible(true);
				btnDiscoDuro.setSelected(false);
				btnTarjetaMadre.setSelected(true);
				btnMemoriaRam.setEnabled(false);
				btnMicroProcesador.setEnabled(false);
				btnDiscoDuro.setEnabled(false);
				txtModeloTM.setText(((MotherBoard)selected).getModelo());
				txtModeloTM.setEnabled(false);
				cbxSocketTM.setEnabled(false);
				cbxSocketTM.setSelectedItem(((MotherBoard)selected).getSocket());
				cbxTipoRamTM.setEnabled(false);
				cbxTipoRamTM.setSelectedItem(((MotherBoard)selected).getTipoRam());
				cbxTipoConexiones.setEnabled(false);
				cbxTipoConexiones.setSelectedItem(((MotherBoard)selected).getConexionesHD());
			}
		}
	}
	
	private void clean() {
		txtNumSerie.setText("");
		txtMarca.setText("");
		txtModeloDiscoDuro.setText("");
		txtPrecio.setText("");
		txtModeloTM.setText("");
		txtModeloMicroP.setText("");
		txtModeloDiscoDuro.setText("");
		txtCapacidadDiscoDuro.setText("");
		txtCapacidadMRAM.setText("");
		txtModeloMRAM.setText("");
		
		spnDispReal.setValue(0);
		spnDispMax.setValue(0);
		spnDispMin.setValue(0);
		
		cbxSocketMicroP.setSelectedIndex(0);
		cbxSocketTM.setSelectedIndex(0);
		cbxTipoMemoriaRam.setSelectedIndex(0);
		cbxTipoRamTM.setSelectedIndex(0);
		cbxSocketDiscoDuro.setSelectedIndex(0);
		cbxTipoConexiones.setSelectedIndex(0);
	}
}
