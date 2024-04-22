package logico;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;

//Esta es la clase objeto del Administrador
public class Administrador extends Empleado implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<Vendedor> misVendedores;
	
	public Administrador(String username, String password, String nombre, String cedula, String telefono, String direccion, Blob imagen) {
		super(username, password, nombre, cedula, telefono, direccion, imagen);
		this.misVendedores = new ArrayList<Vendedor>();
	}
	
	public ArrayList<Vendedor> getMisVendedores() {
		return misVendedores;
	}
	public void setMisVendedores(ArrayList<Vendedor> misVendedores) {
		this.misVendedores = misVendedores;
	}
	public void addVendedor(Vendedor vendedor) {
		misVendedores.add(vendedor);
	}
	public void eliminarVendedor(Vendedor vendedor) {
		misVendedores.remove(vendedor);
	}
	
}
