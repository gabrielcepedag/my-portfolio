package logico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Factura implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String codigo;
	private Cliente miCliente;
	private Vendedor miVendedor;
	private ArrayList<Producto> misProductos = new ArrayList<Producto>();
	private float precioTotal;
	private boolean aCredito;
	private Date fecha;
	public static int cod = 1;
	
	public Factura(String codigo, Vendedor miVendedor, Cliente miCliente, ArrayList<Producto> misProductos, Date fecha) {
		super();
		this.codigo = codigo;
		this.miVendedor = miVendedor;
		this.miCliente = miCliente;
		this.misProductos = misProductos;
		this.precioTotal = 0;
		this.aCredito = false;
		this.fecha = fecha;
		Factura.cod++;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public Cliente getMiCliente() {
		return miCliente;
	}
	public void setMiCliente(Cliente miCliente) {
		this.miCliente = miCliente;
	}

	public Vendedor getMiVendedor() {
		return miVendedor;
	}
	public ArrayList<Producto> getMisProductos() {
		return misProductos;
	}

	public void setMiVendedor(Vendedor miVendedor) {
		this.miVendedor = miVendedor;
	}

	public void setMisProductos(ArrayList<Producto> misProductos) {
		this.misProductos = misProductos;
	}

	public float getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(float precioTotal) {
		this.precioTotal = precioTotal;
	}
	
	public boolean isACredito() {
		return aCredito;
	}

	public void setACredito(boolean aCredito) {
		this.aCredito = aCredito;
	}

	/*public float calcPrecioFactura() {
		if (precioTotal > 0) {
			return precioTotal;
		}else {
			float total = 0;
			
			for (Producto producto : misProductos) {
				total += producto.getPrecio();
			}
			this.precioTotal = total;
			return precioTotal;
		}
	}*/
}
