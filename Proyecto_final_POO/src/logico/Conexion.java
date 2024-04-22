package logico;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	private static Connection conexion = null;

	public static Connection getConexion() {
		String conexionUrlString = "jdbc:sqlserver://192.168.100.118:1433;"
				+ "database=TecnoC;"
				+ "user=emartinez;"
				+ "password=Prueba123@;"
				+ "loginTimeout=30;"
				+ "trustServerCertificate=true";
				
		try {
			Connection con = DriverManager.getConnection(conexionUrlString);
			return con;
		} catch (SQLException e) {
			System.out.println(e.toString());
			return null;
		}
	}

	public static Connection getInstance() {
		if (conexion == null) {
			conexion = Conexion.getConexion();
		}
		return conexion;
	}
	
	// public static void main(String[] args) {
	// 	try {
	// 		Statement SQL = Conexion.getConexion().createStatement();
	// 		System.out.println("Conexion Correcta");
			
	// 	} catch (SQLException e) {
	// 		System.out.println("Error" + e.toString());
	// 	}
	// }
	// public static void main(String[] args) {
	// 	try {
	// 		// Statement con = Conexion.getConexion().createStatement();
	// 		Connection con = Conexion.getConexion(); //Hacer esto global
	// 		String SQL = "SELECT * FROM persona";
	// 		Statement stmt = con.createStatement();
	// 		ResultSet rs = stmt.executeQuery(SQL);
		 
	// 		while (rs.next()) {
	// 		System.out.println(rs.getString("cedulaPersona") + " " + rs.getString("nombre"));
	// 	 	}
		   
	// 	   rs.close();
	// 	   stmt.close();
	// 	 }
	// 	 catch (Exception e) {
	// 	   e.printStackTrace();
	// 	 }
	// }
}
