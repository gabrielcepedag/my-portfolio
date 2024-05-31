package logico;

import java.io.Serializable;

public abstract class Producto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String numSerie;
	protected int cantidad;
	protected float precio;
	protected String marca;
	protected String modelo;
	protected int dispMin;
	protected int dispMax;
	protected String tipoProducto;
	
	public Producto(String numSerie, int cantidad, float precio, String marca, String modelo, int dispMin, int dispMax, String tipoProducto) {
		super();
		this.numSerie = numSerie;
		this.cantidad = cantidad;
		this.precio = precio;
		this.marca = marca;
		this.dispMax = dispMax;
		this.dispMin = dispMin;
		this.modelo = modelo;
		this.tipoProducto = tipoProducto;
	}
	
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String modelo) {
		this.tipoProducto = tipoProducto;
	}
	
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getDispMin() {
		return dispMin;
	}

	public void setDispMin(int dispMin) {
		this.dispMin = dispMin;
	}

	public int getDispMax() {
		return dispMax;
	}

	public void setDispMax(int dispMax) {
		this.dispMax = dispMax;
	}
	
}
