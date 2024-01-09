package logico;

import java.io.Serializable;

public class DiscoDuro extends Producto implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private int capacidad;
	private String socket;
	
	public DiscoDuro(String numSerie, int cantidad, float precio, String marca, String modelo, int dispMin, int dispMax, int capacidad,
			String socket, String tipoProducto) {
		super(numSerie, cantidad, precio, marca, modelo, dispMin, dispMax,"Disco Duro");
		this.capacidad = capacidad;
		this.socket = socket;
	}

	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
	public String getSocket() {
		return socket;
	}
	public void setSocket(String socket) {
		this.socket = socket;
	}
	
	
}
