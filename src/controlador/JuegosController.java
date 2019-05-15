package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import modelo.Juego;

public class JuegosController {
	private final static String drv="com.mysql.jdbc.Driver";
	private final static String db= "jdbc:mysql://localhost:3306/juegos?useSSL=false";
	private static String userAndPass="root";
	private Connection cn;
	private Statement st;
	private ResultSet rs;
	private List<Juego> juegos;
	public JuegosController() {
		super();
	}
	
	public void abrirConexion() throws ClassNotFoundException, SQLException {
		Class.forName(drv);
		cn= DriverManager.getConnection(db, userAndPass, "");
		System.out.println("La conexion se realizo con �xito");
	}
	public void cerrarConexion() throws SQLException {
		if(rs!=null) {
			rs.close();
		}
		if(st!=null) {
			st.close();
		}
		if(cn!=null) {
			cn.close();
		}
		System.out.println("Conexion cerrada");
	}
}
