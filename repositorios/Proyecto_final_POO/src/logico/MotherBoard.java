package logico;

import java.io.Serializable;

public class MotherBoard extends Producto implements Serializable{

private static final long serialVersionUID = 1L;
private String socket;
private String tipoRam;
private String conexionesHD;

	public MotherBoard(String numSerie, int cantidad, float precio, String marca, String modelo, int dispMin,
			int dispMax, String socket, String tipoRam, String conexionesHD, String tipoProducto) {
		super(numSerie, cantidad, precio, marca, modelo, dispMin, dispMax, "Tarjeta Madre");
		this.socket = socket;
		this.tipoRam = tipoRam;
		this.conexionesHD = conexionesHD;	}
	
	public String getSocket() {
		return socket;
	}
	public void setSocket(String socket) {
		this.socket = socket;
	}
	
	public String getTipoRam() {
		return tipoRam;
	}
	public void setTipoRam(String tipoRam) {
		this.tipoRam = tipoRam;
	}
	
	public String getConexionesHD() {
		return conexionesHD;
	}
	public void setConexionesHD(String conexionesHD) {
		this.conexionesHD = conexionesHD;
	}
}

	