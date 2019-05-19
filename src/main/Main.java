package main;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controlador.JuegosController;
import excepciones.CampoVacioException;
import excepciones.CodigoDeBarrasException;
import excepciones.PrecioException;
import modelo.Juego;
import vista.FrmJuegos;

public class Main {

	public static void main(String[] args) {
		/*List<Juego> juegos2=new ArrayList<Juego>();
		Juego juego=null;
		boolean agregado=false;
		Date fecha=null;
		java.sql.Date fechasql=null;
		String fechaCadena="17/12/2009";
		SimpleDateFormat formateador=new SimpleDateFormat("dd/MM/yyyy");
		formateador.setLenient(false);
		try {
			fecha=formateador.parse(fechaCadena);
		} catch (ParseException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
		fechasql=new java.sql.Date(fecha.getTime());
		try {
			juego=new Juego("FFXIII", "Square Enix", "8445612378901", "Rol,RPG,Aventuras", 40, fechasql);
		} catch (CampoVacioException | CodigoDeBarrasException | PrecioException e) {
			System.out.println(e.getMessage());
		}
		JuegosController juegos=new JuegosController();

			try {
				juegos.abrirConexion();
				agregado=juegos.borraJuego(juego);
				juegos.cerrarConexion();

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			
		if(agregado) {
			System.out.println("Borrado");
		}
		else {
			System.out.println("No borrado");
		}
	}*/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmJuegos frame = new FrmJuegos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
