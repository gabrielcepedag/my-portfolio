package logico;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class Tienda implements Serializable{

	private static final long serialVersionUID = 1L;
	private static Tienda tienda = null;
	private static Empleado loginUserEmpleado = null;
	private int staticPedidos = 1;
	private int staticCombo = 1;
	private int staticFactura = 1;
	private ArrayList<Producto> misProductos;
	private ArrayList<Cliente> misClientes;
	private ArrayList<Factura> misFacturas;
	private ArrayList<Empleado> misEmpleados;
	private ArrayList<Combo> misCombos;
	private ArrayList<OrdenCompra> misOrdenesCompra;
	private ArrayList<String> misDistribuidores = new ArrayList<String>();

	private Tienda() {
		super();
		this.misProductos = new ArrayList<Producto>();
		this.misClientes = new ArrayList<Cliente>();
		this.misFacturas = new ArrayList<Factura>();
		this.misEmpleados = new ArrayList<Empleado>();
		this.misCombos = new ArrayList<Combo>();
		this.misOrdenesCompra = new ArrayList<OrdenCompra>();
	}
	
	public static Tienda getInstance() {
		if (tienda == null) {
			tienda = new Tienda();
		}
		return tienda;
	}
	
	public ArrayList<Producto> getMisProductos() {
		return misProductos;
	}
	public void setMisProductos(ArrayList<Producto> misProductos) {
		this.misProductos = misProductos;
	}

	public ArrayList<Cliente> getMisClientes() {
		return misClientes;
	}
	public void setMisClientes(ArrayList<Cliente> misClientes) {
		this.misClientes = misClientes;
	}
		
	public ArrayList<Factura> getMisFacturas() {
		return misFacturas;
	}
	public void setMisFacturas(ArrayList<Factura> misFacturas) {
		this.misFacturas = misFacturas;
	}

	public ArrayList<Empleado> getMisEmpleados() {
		return misEmpleados;
	}
	public void setMisEmpleados(ArrayList<Empleado> misEmpleados) {
		this.misEmpleados = misEmpleados;
	}

	public ArrayList<Combo> getMisCombos() {
		return misCombos;
	}
	public void setMisCombos(ArrayList<Combo> misCombos) {
		this.misCombos = misCombos;
	}

	public ArrayList<OrdenCompra> getMisOrdenesCompra() {
		return misOrdenesCompra;
	}
	public void setMisOrdenesCompra(ArrayList<OrdenCompra> misOrdenesCompra) {
		this.misOrdenesCompra = misOrdenesCompra;
	}
	
	public ArrayList<String> getMisDistribuidores() {
		return misDistribuidores;
	}
	public void setMisDistribuidores(ArrayList<String> misDistribuidores) {
		this.misDistribuidores = misDistribuidores;
	}
	
	public Empleado getLoginUserEmpleado() {
		return loginUserEmpleado;
	}
	public void setLoginUserEmpleado(Empleado loginUserEmpleado) {
		Tienda.loginUserEmpleado = loginUserEmpleado;
	}

	public void actualizarVariablesStatic() {
		staticCombo = Combo.cod;
		staticPedidos = OrdenCompra.numOrdenCompra;
		staticFactura = Factura.cod;
	}
	
	public int getStaticCombo() {
		return staticCombo;
	}
	
	public int getStaticFactura() {
		return staticFactura;
	}
	
	public  int getStaticPedido() {
		return staticPedidos;
	}
	
	public static void setTienda(Tienda tienda) {
		Tienda.tienda = tienda;
	}
	
	public void addDistribuidor(String distribuidor) {
		misDistribuidores.add(distribuidor);
	}
	
	public void addCliente(Cliente cliente) {
		misClientes.add(cliente);
	}
	
	public void addProducto(Producto producto) {
		misProductos.add(producto);
	}
	
	public void addFactura(Factura factura) {
		misFacturas.add(factura);
	}
	
	public void addEmpleado(Empleado empleado) {
		misEmpleados.add(empleado);
	}

	public boolean addCombo(Combo comboAux) {
		boolean continuar = false;
		int cantidad = 0;

		try {
			Conexion.getInstance().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			String SQL = "INSERT INTO combo "
			+ "(nombre, descuentoPorProducto, precioNeto, precioTotal)"
			+ "values( ?, ?, ?, ?); ";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setString(1, comboAux.getNombre());
			pstmt.setFloat(2, comboAux.getDescuento());
			pstmt.setFloat(3, comboAux.getPrecioNeto());
			pstmt.setFloat(4, comboAux.getPrecioTotal());
			
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

		ArrayList<Producto> productosSinRepetir = new ArrayList<Producto>();
		ArrayList<Producto> productos = new ArrayList<Producto>();
		
		productos.addAll(comboAux.getMisProductos());
		
		for (int i = 0; i < productos.size(); i++) {
			int j = 1;
			cantidad = contarProducto(productos.get(i), comboAux.getMisProductos());
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
		
		 for (Producto producto : productosSinRepetir){
			try {
				cantidad = contarProducto(producto, comboAux.getMisProductos());
				String SQL = "INSERT INTO combo_producto"
				+ "(numSerie, codigoCombo, cantidad)"
				+ "values(?, ?, ?); ";
				PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
				pstmt.setString(1, producto.getNumSerie());
				pstmt.setInt(2, Integer.parseInt(comboAux.getCodigo()));
				pstmt.setInt(3, cantidad);

				System.out.println(producto.getNumSerie() + " " + comboAux.getCodigo() + " " + cantidad);

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
		}
		try {
			Conexion.getInstance().commit();
			continuar = true;
		} catch (SQLException e) {
			try {
				Conexion.getInstance().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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

	public void addOrdenCompra(OrdenCompra orden) {
		misOrdenesCompra.add(orden);
	}
	
	public void eliminarProducto(Producto producto) {

		// misProductos.remove(producto);
		try {
			String SQL = "DELETE FROM orden_compra  WHERE orden_compra.numSerie = ?;"
						+ "DELETE FROM producto WHERE producto.numSerie = ?;";
						
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setString(1, producto.getNumSerie());
			pstmt.setString(2, producto.getNumSerie());
			int count = pstmt.executeUpdate();
			
			System.out.println("Filas afectadas: "+count);

			pstmt.close();
			
		 }
		 catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "No se pudo eliminar el producto correctamente.", "error al eliminar", JOptionPane.ERROR_MESSAGE);
		   e1.printStackTrace();
		 }
	}
	
	public void eliminarCombo(Combo combo) {
		misCombos.remove(combo);
	}
	
	public void eliminarDistribuidor(String distribuidor) {
		misDistribuidores.remove(distribuidor);
	}
	
	public void eliminarVendededor(Vendedor vendedor) {
		
	}
	
	public void eliminarCliente(Cliente cliente) {
		misClientes.remove(cliente);
	}
	
	public void eliminarFactura(Factura factura) {
		misFacturas.remove(factura);
	}
	
	public boolean confirmLogin(String user, String password) {
		boolean login = false;
		ResultSet rs = null;

		try {
			Statement st = Conexion.getInstance().createStatement();

			rs = st.executeQuery("SELECT * from empleado e where e.username = '"+user+"' AND e.password = cast(cast( '"+password+"' as varbinary) as varchar);");

			if(rs.next()) {
				login = true;
				
				Empleado aux = (Empleado)Tienda.getInstance().buscarEmpleadoByCedula(rs.getString("cedulaPersona"));
				tienda.setLoginUserEmpleado(aux);
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		/*
		for (Empleado empleado : misEmpleados) { 
			if (empleado.getUsername().equals(user) && empleado.getPassword().equals(password)) {
				loginUserEmpleado = empleado;
				login = true;
			}
		}
		*/
		
		return login;
	}
	
	public Cliente buscarClienteByCedula(String cedula) {
		ResultSet rs = null;
		Cliente clienteAux = null;
		
		try {
			String SQL = "SELECT * FROM datos_persona_cliente WHERE cedulaPersona = ?;";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setString(1, cedula);
			rs = pstmt.executeQuery();
			rs.next();

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
					rs.getString("marca"), rs.getString("modelo"), rs.getInt("dispMin"), rs.getInt("dispMax"),rs.getString("socket"), rs.getString("tipoRam"), rs.getString("tipoConexionHD"),rs.getString("tipoProducto") );
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
	


	public Factura buscarFacturaByCodigo(String codigo) {

		ResultSet rs = null;
		Factura result = null;
		Cliente resultCliente = null;
		ArrayList<Producto> productosFactura = new ArrayList<Producto>();

		try {
			String SQL = "SELECT * FROM factura WHERE codigo = ?;";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setString(1, codigo);
			rs = pstmt.executeQuery();
			rs.next();
			
			String codFactura = rs.getString("codigo");
			
			try {
				String SQL2 = "SELECT * FROM detalle_factura_producto WHERE codigoFactura = ?";
				PreparedStatement pstmt2 = Conexion.getInstance().prepareStatement(SQL2);
				pstmt2.setString(1, rs.getString("codigo"));
				ResultSet rs2 = pstmt2.executeQuery();
				
				while(rs2.next()) {
					productosFactura.add(Tienda.getInstance().buscarProductoByNumSerie(rs2.getString("numSerie")));
				}			
				rs2.close();
				pstmt2.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			try {
				String SQL3 = "SELECT * FROM datos_persona_cliente WHERE cedulaPersona = ?";
				PreparedStatement pstmt3 = Conexion.getInstance().prepareStatement(SQL3);
				pstmt3.setString(1, rs.getString("cedulaCliente"));
				ResultSet rs3 = pstmt3.executeQuery();
				rs3.next();
				
				String direccion = rs3.getString("direccion") + ", " + rs3.getString("nombreMunicipio");
				resultCliente = new Cliente(rs3.getString("cedulaPersona"), rs3.getString("nombre"), direccion, rs3.getString("telefono"));
				
				rs3.close();
				pstmt3.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}			
		
			if(codFactura.equals(codigo)) {
				result = new Factura(rs.getString("codigo"), (Vendedor)Tienda.getInstance().buscarEmpleadoByCedula(rs.getString("cedulaVendedor")),
						resultCliente, productosFactura, rs.getDate("fecha"));
				result.setPrecioTotal(rs.getFloat("montoTotal"));
				result.setACredito(false);
				
			}
			rs.close();
			pstmt.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return (Factura)result;
	}
	
	public Empleado buscarEmpleadoByCedula(String cedula) {
		ResultSet rs = null;

		try {
			String SQL = "SELECT * FROM datos_persona_empleado WHERE cedulaPersona = ?;";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);
			pstmt.setString(1, cedula);
			rs = pstmt.executeQuery();
			
			if ( (rs.next()) ){						
				String passwordString = new String(rs.getBytes("password"), java.nio.charset.StandardCharsets.UTF_8);
										
				Statement consultaVerificar = Conexion.getInstance().createStatement();
				if (consultaVerificar.executeQuery("select * from vendedor where cedulaEmpleado like '" + cedula + "'").next()) {
					Empleado vendedorEmpleado = new Vendedor(rs.getString("username"), passwordString, rs.getString("nombre"), rs.getString("cedulaPersona"), rs.getString("telefono"), rs.getString("direccion"), rs.getBlob("foto"));
					return vendedorEmpleado;
				}
				else {
					Empleado vendedorEmpleado = new Administrador(rs.getString("username"), passwordString, rs.getString("nombre"), rs.getString("cedulaPersona"), rs.getString("telefono"), rs.getString("direccion"), rs.getBlob("foto"));
					return vendedorEmpleado;
				}
				
			}

		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public Combo buscarComboByCod(String codigo) {
		Combo comboAux = null;

		ResultSet rsCombo = null;
		ResultSet rs = null;

		ArrayList<Producto> productosCombo = new ArrayList<>();

		try {
			String SQL = "SELECT * FROM combo WHERE codigo = ?;";
			PreparedStatement pstmt = Conexion.getInstance().prepareStatement(SQL);

			String SQLProductos = "select * from combo_producto, producto where combo_producto.codigoCombo = ? and combo_producto.numSerie = producto.numSerie;";
			PreparedStatement pstmtProductos = Conexion.getInstance().prepareStatement(SQLProductos);

			pstmt.setString(1, codigo);
			rsCombo = pstmt.executeQuery();

			pstmtProductos.setString(1, codigo);
			rs = pstmtProductos.executeQuery();

//			public Producto(String numSerie, int cantidad, float precio, String marca, String modelo, int dispMin, int dispMax, String tipoProducto) {

			while (rs.next()) {
				Producto selectedFinal = null;
				String productType = rs.getString("tipoProducto");

				if (productType.equalsIgnoreCase("disco duro")){
					DiscoDuro selected;
					selected = new DiscoDuro(rs.getString("numSerie"), rs.getInt("cantStock"), rs.getFloat("precio"),
						rs.getString("marca"), rs.getString("modelo"), rs.getInt("dispMin"), rs.getInt("dispMax"), rs.getInt("capacidad"),rs.getString("socket"), rs.getString("tipoProducto") );
					selectedFinal = (Producto)selected;
				}else if (productType.equalsIgnoreCase("memoria ram")){
					MemoriaRam selected;
					selected = new MemoriaRam(rs.getString("numSerie"), rs.getInt("cantStock"), rs.getFloat("precio"),
						rs.getString("marca"), rs.getString("modelo"), rs.getInt("dispMin"), rs.getInt("dispMax"), rs.getInt("capacidad"),rs.getString("tipoRam"), rs.getString("tipoProducto") );
					selectedFinal = (Producto)selected;

				}else if (productType.equalsIgnoreCase("Micro procesador")){
					MicroProcesador selected;
					selected = new MicroProcesador(rs.getString("numSerie"), rs.getInt("cantStock"), rs.getFloat("precio"),
						rs.getString("marca"), rs.getString("modelo"), rs.getInt("dispMin"), rs.getInt("dispMax"),rs.getString("socket"), rs.getFloat("velocidadProcesamiento"),rs.getString("tipoProducto") );
					selectedFinal = (Producto)selected;
				}else if (productType.equalsIgnoreCase("Tarjeta Madre")){
					MotherBoard selected;
					selected = new MotherBoard(rs.getString("numSerie"), rs.getInt("cantStock"), rs.getFloat("precio"),
						rs.getString("marca"), rs.getString("modelo"), rs.getInt("dispMin"), rs.getInt("dispMax"),rs.getString("socket"), rs.getString("tipoRam"), rs.getString("tipoConexionHD"), rs.getString("tipoProducto") );
					selectedFinal =  (Producto)selected;
				}
				productosCombo.add(selectedFinal);
			}

			if ( (rsCombo.next()) ){									
				comboAux = new Combo(rsCombo.getString("codigo"), rsCombo.getString("nombre"), productosCombo, rsCombo.getFloat("descuentoPorProducto"));
				comboAux.setPrecioTotal(rsCombo.getFloat("precioTotal"));
				comboAux.setPrecioNeto(rsCombo.getFloat("precioNeto"));
			}

		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
		return comboAux;
	}
	
	public OrdenCompra buscarOrdenDeCompraByCod(int codigo) {
		OrdenCompra ordenCompraAux = null;
		for (OrdenCompra ordenCompra : misOrdenesCompra) {
			if (ordenCompra.getCodigo() == codigo) {
				ordenCompraAux = ordenCompra;
				return ordenCompraAux;
			}
		}
		return ordenCompraAux;
	}
	
	//Function o Procedure
	/*
	public float calcTotalFactura(String codFactura) {
		Factura factura = buscarFacturaByCodigo(codFactura);
		
		return factura.calcPrecioFactura();
	}
	*/
	public float calcPrecioTotalProductos(ArrayList<Producto> productos) {
		float precioTotal = 0;
		for (Producto producto : productos) {
			precioTotal += producto.getPrecio();
		}
		return precioTotal;
	}
	
	public Cliente clienteDelMes(){
		Cliente clienteDelMes = null;
		ResultSet rs = null;

		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT * from cliente_del_mes");
			if ((rs.next())) {
				clienteDelMes = Tienda.getInstance().buscarClienteByCedula(rs.getString("cedulaPersona"));
				
				if (clienteDelMes != null)
					clienteDelMes.setCantCompras(rs.getInt("maximoComprado"));
				else
					return null;
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return clienteDelMes;
	}
	
	public Vendedor vendedorDelMes() {
		Vendedor vendedorDelMes = null;
		ResultSet rs = null;
		// float mayor = 0;
		// float montoEnEseMes = 0;
		// Date fecha = new Date();
		
		// for (Empleado empleado : misEmpleados) {
		// 	if(empleado instanceof Vendedor) {
		// 		for (Factura factura : misFacturas) {
		// 			if (empleado.getCedula().equalsIgnoreCase(factura.getMiVendedor().getCedula())) {
		// 				if (factura.getFecha().getMonth() == fecha.getMonth() && factura.getFecha().getYear() == fecha.getYear()) {
		// 					montoEnEseMes += factura.getPrecioTotal();
		// 				}
		// 			}
		// 		}
		// 		if (montoEnEseMes > mayor) {
		// 			mayor = montoEnEseMes;
		// 			vendedorDelMes = (Vendedor) empleado;
		// 		}
		// 	}
		// }
		// if (mayor == 0) {
		// 	return null;
		// }
		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT * from vendedor_del_mes");
			if (rs.next()) {
				vendedorDelMes = (Vendedor)buscarEmpleadoByCedula(rs.getString("cedulaEmpleado"));
				vendedorDelMes.setTotalVendido(rs.getInt("maximoVendido"));
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return vendedorDelMes;
	}
	
	public void crearOrdenesCompra() {
		
		for (Producto producto : misProductos) {
			Producto productoAOrdenar = null;
			if (producto.getCantidad() < producto.getDispMin()) {
				if (ExisteOrdenDelProducto(producto) == false) {
					productoAOrdenar = producto;
				}
			}
			
			if(productoAOrdenar != null) {
				OrdenCompra orden = new OrdenCompra(OrdenCompra.numOrdenCompra, productoAOrdenar);
				
				Tienda.getInstance().addOrdenCompra(orden);
			}
		}
	}

	private boolean ExisteOrdenDelProducto(Producto producto) {
		boolean existe = false;
		
		for (OrdenCompra orden : misOrdenesCompra) {
			if (orden.getProducto().equals(producto)) {
				existe = true;
				return existe;
			}
		}
		return existe;
	}
	
	public boolean CrearFactura(Factura factura) {
		boolean esPosible = false;
		/*
		if (factura.isACredito()) {
			if (factura.getMiCliente().getCredito() < factura.getPrecioTotal()) {
				return esPosible;
			}
		}
		esPosible = true;
		for (Producto producto : factura.getMisProductos()) {
			producto.setCantidad(producto.getCantidad() - 1);
		}
		*/
		esPosible = true;
		addFactura(factura);
		return esPosible;
	}
	
	/*
	public boolean abonarFacturaCredito(String cod, float abono) {
		boolean esPosible = false;
		Factura f1 = buscarFacturaByCodigo(cod);
		
		if (f1.isACredito()) {
			if (f1.getPrecioTotal() < abono) {
				return esPosible;
			}
			if (f1.getLineaCredito() < abono) {
				return esPosible;
			}
			f1.setLineaCredito(f1.getLineaCredito() - abono);
			if (f1.getLineaCredito() == 0) {
				f1.setACredito(false);
			}
			esPosible = true;
		}
		return esPosible;
	}
	*/
	
	public Cliente getClienteMasCompras() {
		Cliente aux = null;
		ResultSet rs = null;

		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT * from cliente_con_mas_compras");
			if (rs.next()) {
				aux = buscarClienteByCedula(rs.getString("cedulaPersona"));
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return aux;
	}
	
	public Cliente getClienteMenosCompras() {
		Cliente aux = null;
		ResultSet rs = null;

		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT * from cliente_con_menos_compras");
			if (rs.next()) {
				aux = buscarClienteByCedula(rs.getString("cedulaPersona"));
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return aux;
	}
	
	// public Cliente getClienteMayorDeuda(){
	// 	Cliente aux = null;
	// 	float mayor = 0;
		
	// 	for (Factura factura : misFacturas) {
	// 		if(factura.isACredito()) {
	// 			if(factura.getPrecioTotal() > mayor) {
	// 				mayor = factura.getPrecioTotal();
	// 				aux = factura.getMiCliente();
	// 			}
	// 		}
	// 	}
		
	// 	if (mayor == 0) {
	// 		return null;
	// 	}
	// 	return aux;
	// }
	
	public String productoMasComprado() {
		String aux = null;
		ResultSet rs = null;

		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT * from producto_mas_comprado");
			if (rs.next()) {
				aux = rs.getString("numSerie") + " Cant: "+ rs.getString("maxVendido");
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return aux;
	}
	
	public String productoMenosComprado() {
		String aux = null;
		ResultSet rs = null;

		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT * from producto_menos_comprado");
			if (rs.next()) {
				aux = rs.getString("numSerie") + " Cant: "+ rs.getString("menosVendido");
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return aux;
	}
	
	public Vendedor vendedorConMasFacturas() {
		Vendedor aux = null;
		ResultSet rs = null;

		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT * from vendedor_con_mas_compras");
			if (rs.next()) {
				aux = (Vendedor)buscarEmpleadoByCedula(rs.getString("cedulaEmpleado"));
				aux.setTotalVendido(rs.getInt("masCompras"));
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return aux;
	}

	// private int calcFacturasVendedor(Vendedor v1) {
	// 	int cont = 0;
		
	// 	for (Factura f1 : misFacturas) {
	// 		if (f1.getMiVendedor().equals(v1)) {
	// 			cont++;
	// 		}
	// 	}
	// 	return cont;
	// }
	
	// public int CantMayorDeFacturasEnUnVendedor() {
	// 	int mayor = 0, cont = 0;
	// 	Vendedor aux = null;
		
	// 	for (Factura factura : misFacturas) {
	// 		if (factura.getMiVendedor() instanceof Vendedor) {
	// 			cont = calcFacturasVendedor(factura.getMiVendedor());
	// 			if (cont > mayor) {
	// 				mayor = cont;
	// 				aux = factura.getMiVendedor();
	// 			}
	// 		}
	// 	}
		
	// 	return cont;
	// }
	
	public Vendedor vendedorConMenosFacturas() {
		Vendedor aux = null;
		ResultSet rs = null;

		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT * from vendedor_con_menos_compras");
			if (rs.next()) {
				aux = (Vendedor)buscarEmpleadoByCedula(rs.getString("cedulaEmpleado"));
				aux.setTotalVendido(rs.getInt("menosCompras"));
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return aux;
	}
	
	public Factura facturaMasCara() {
		Factura aux = null;
		ResultSet rs = null;

		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT * from factura_mas_cara");
			if (rs.next()) {
				aux = buscarFacturaByCodigo(rs.getString("codigo"));
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return aux;
		
	}
	
	public Factura facturaMenosCara() {
		Factura aux = null;
		ResultSet rs = null;

		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT * from factura_menos_cara");
			if (rs.next()) {
				aux = buscarFacturaByCodigo(rs.getString("codigo"));
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return aux;
	}
	
	// public int cantFacturasAdeudadas() {
	// 	int cont = 0;
		
	// 	for (Factura factura : misFacturas) {
	// 		if (factura.isACredito()) {
	// 			cont++;
	// 		}
	// 	}
	// 	return cont;
	// }
	
	public int cantFacturasPagadas() {
		ResultSet rs = null;
		int cont = 0;

		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT count(codigo) as cantidad from factura WHERE esACredito = 0");
			if (rs.next()) {
				cont = rs.getInt("cantidad");
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return cont;
	}
	
	public float totalVendido() {
		ResultSet rs = null;
		Float totalVendido = null;
		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT * from total_vendido");
			if (rs.next()) {
				totalVendido = rs.getFloat("total");
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return totalVendido;
	}
	
	public String comboMasVendido() {
		String aux = null;
		ResultSet rs = null;

		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT * from combo_mas_comprado");
			if (rs.next()) {
				aux = "cod: "+rs.getString("codigo") +" nombre: "+rs.getString("nombre")
				+ " cant: "+rs.getInt("maxVendido");
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return aux;
	}
	
	public String comboMenosVendido() {
		String aux = null;
		ResultSet rs = null;

		try {
			Statement st = Conexion.getInstance().createStatement();
			rs = st.executeQuery("SELECT * from combo_menos_comprado");
			if (rs.next()) {
				aux = "cod: "+rs.getString("codigo") +" nombre: "+rs.getString("nombre")
				+ " cant: "+rs.getInt("minVendido");
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return aux;
	}

	public boolean verificarUser(String username, int indexEmpleado) {
		boolean diferente = true;
		int i = 0;
		for (Empleado empleado : misEmpleados) {
			if (empleado.getUsername().equals(username) && i != indexEmpleado) {
				diferente = false;
				break;
			}
			i++;
		}
		return diferente;
	}

	public boolean verificarUserRegistrar(String username) {
		boolean diferente = true;
		for (Empleado empleado : misEmpleados) {
			if (empleado.getUsername().equals(username)) {
				diferente = false;
				break;
			}
		}
		return diferente;
	}

	public int buscarIndexEmpleado(Empleado selected) {
		return misEmpleados.indexOf(selected);
	}
}
