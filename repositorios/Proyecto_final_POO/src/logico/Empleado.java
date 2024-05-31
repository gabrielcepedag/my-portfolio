package logico;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Empleado implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String username;
	protected String password;
	protected String nombre;
	protected String cedula;
	protected String telefono;
	protected String direccion;
	protected Blob imagen;
	
	public Empleado(String username, String password, String nombre, String cedula, String telefono, String direccion, Blob blob) {
		super();
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.cedula = cedula;
		this.telefono = telefono;
		this.direccion = direccion;
		this.imagen = blob;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
/*
 * public void setPassword(String password) {
 *		this.password = password;
 *	}
 */
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Blob getImagen() {
		return imagen;
	}

	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}

	public boolean isAdministrador() {
		ResultSet rs = null;
		boolean retorno = false;
		
		try {
			Statement st = Conexion.getInstance().createStatement();

			rs = st.executeQuery("SELECT * from administrador a where a.cedulaEmpleado = '"+cedula+"'");
			if(rs.next()) {
				return true;
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return retorno;
	}

	public boolean isVendedor() {
		ResultSet rs = null;
		boolean retorno = false;
		
		try {
			Statement st = Conexion.getInstance().createStatement();

			rs = st.executeQuery("SELECT * from vendedor v where v.cedulaEmpleado = '"+cedula+"'");
			if(rs.next()) {
				return retorno =  true;
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return retorno;
	}	
	
}
