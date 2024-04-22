package logico;

import java.io.Serializable;

public class MicroProcesador extends Producto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String socket;
	private float velocidadProcesamiento;
	
	public MicroProcesador(String numSerie, int cantidad, float precio, String marca, String modelo, int dispMin, int dispMax, String socket,
			float velocidadProcesamiento, String tipoProducto) {
		super(numSerie, cantidad, precio, marca, modelo, dispMin, dispMax, "Micro Procesador");
		this.socket = socket;
		this.velocidadProcesamiento = velocidadProcesamiento;
	}
	
	public String getSocket() {
		return socket;
	}
	public void setSocket(String socket) {
		this.socket = socket;
	}
	
	public float getVelocidadProcesamiento() {
		return velocidadProcesamiento;
	}
	public void setVelocidadProcesamiento(float velocidadProcesamiento) {
		this.velocidadProcesamiento = velocidadProcesamiento;
	}
	
}
