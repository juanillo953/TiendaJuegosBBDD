package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import excepciones.CampoVacioException;
import excepciones.CodigoDeBarrasException;
import excepciones.PrecioException;
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
		System.out.println("La conexion se realizo con éxito");
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
	public List<Juego> getConsultaJuegos (String sql) throws SQLException, CampoVacioException, CodigoDeBarrasException, PrecioException{
		//Nos devuelve una lista con todos los juegos de nuestra base de datos
		juegos=new ArrayList<Juego>();
		Juego juego=null;
		st=cn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		rs=st.executeQuery(sql);
		while(rs.next()) {
			String titulo=rs.getString("titulo");
			String estudio=rs.getString("estudio");
			java.sql.Date fechalanzamiento=rs.getDate("fechalanzamiento");
			String codigobarras=rs.getString("codigobarras");
			int precio=rs.getInt("precio");
			String genero=rs.getString("genero");
			juego=new Juego(titulo, estudio, codigobarras, genero, precio, fechalanzamiento);
			juegos.add(juego);
		}
		return juegos;
	}
	public List<Juego> buscaLibros (String campo,String cadenaBusqueda) throws SQLException, CampoVacioException, CodigoDeBarrasException, PrecioException{
		juegos=new ArrayList<Juego>();
		Juego juego=null;
		String sql="select * from juegos where "+campo+" like'%"+cadenaBusqueda+"%'";
		st=cn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
		rs=st.executeQuery(sql);
		rs.last();
		int filas=rs.getRow();
		rs.beforeFirst();
		if(filas>0) {
			while(rs.next()) {
				String titulo=rs.getString("titulo");
				String estudio=rs.getString("estudio");
				java.sql.Date fechalanzamiento=rs.getDate("fechalanzamiento");
				String codigobarras=rs.getString("codigobarras");
				int precio=rs.getInt("precio");
				String genero=rs.getString("genero");
				juego=new Juego(titulo, estudio, codigobarras, genero, precio, fechalanzamiento);
				juegos.add(juego);
			}
		}
		return juegos;
	}
	public boolean agregaJuego(Juego juego) throws SQLException {
		String sql="select * from juegos";
		st=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		rs=st.executeQuery(sql);
		String titulo=juego.getTitulo();
		String estudio=juego.getEstudio();
		java.sql.Date fechalanzamiento=juego.getFechalanzamiento();
		String codigobarras=juego.getCodigobarras();
		int precio=juego.getPrecio();
		String genero=juego.getGenero();
		
		rs.moveToInsertRow();
		rs.updateString("titulo", titulo);
		rs.updateString("estudio", estudio);
		rs.updateDate("fechalanzamiento", fechalanzamiento);
		rs.updateString("codigobarras", codigobarras);
		rs.updateInt("precio", precio);
		rs.updateString("genero", genero);
		rs.insertRow();
		return true;
	}
}
