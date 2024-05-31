package logico;

import java.io.Serializable;

public class MemoriaRam extends Producto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int capacidad;
	private String tipoMemoria;
	
	public MemoriaRam(String numSerie, int cantidad, float precio, String marca, String modelo, int dispMin, int dispMax, int capacidad,
			String tipoMemoria, String tipoProducto) {
		super(numSerie, cantidad, precio, marca, modelo, dispMin, dispMax,"Memoria Ram");
		this.capacidad = capacidad;
		this.tipoMemoria = tipoMemoria;
	}
	
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
	public String getTipoMemoria() {
		return tipoMemoria;
	}
	public void setTipoMemoria(String tipoMemoria) {
		this.tipoMemoria = tipoMemoria;
	}

}
