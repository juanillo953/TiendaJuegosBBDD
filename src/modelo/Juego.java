package modelo;

import java.sql.Date;

import excepciones.CampoVacioException;
import excepciones.CodigoDeBarrasException;
import excepciones.PrecioException;

public class Juego {
	private String titulo,estudio,codigobarras,genero;
	private int precio;
	private Date fechalanzamiento;
	
	public Juego() {
		super();
	}
	public Juego(String titulo, String estudio, String codigobarras, String genero, int precio, Date fechalanzamiento) throws CampoVacioException, CodigoDeBarrasException, PrecioException {
		super();
		this.setTitulo(titulo);
		this.setEstudio(estudio);
		this.setCodigobarras(codigobarras);
		this.setGenero(genero);
		this.setPrecio(precio);
		this.setFechalanzamiento(fechalanzamiento);
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) throws CampoVacioException {
		if(titulo.length()!=0) {
			this.titulo = titulo;
		}
		else {
			throw new CampoVacioException();
		}
		
	}
	public String getEstudio() {
		return estudio;
	}
	public void setEstudio(String estudio) throws CampoVacioException {
		if(estudio.length()!=0) {
			this.estudio = estudio;
		}
		else {
			throw new CampoVacioException();
		}
		
	}
	public String getCodigobarras() {
		return codigobarras;
	}
	public void setCodigobarras(String codigobarras) throws CodigoDeBarrasException {
		if(codigoDeBarrasCorrecto(codigobarras)) {
			this.codigobarras = codigobarras;
		}
		else {
			throw new CodigoDeBarrasException();
		}
		
	}

	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) throws CampoVacioException {
		if(genero.length()!=0) {
			this.genero=genero;
		}
		else {
			throw new CampoVacioException();
		}
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) throws PrecioException {
		if(precio<1 || precio>80) {
			throw new PrecioException();
		}
		else {
			this.precio = precio;
		}
		
	}
	public Date getFechalanzamiento() {
		return fechalanzamiento;
	}
	public void setFechalanzamiento(Date fechalanzamiento) {
		this.fechalanzamiento = fechalanzamiento;
	}
	
	private boolean codigoDeBarrasCorrecto(String codigobarras) {//Comprueba si el codigo de barras es correcto
		boolean impar=true;
		int acumulador=0;
		String numeroAComprobar=codigobarras.substring(0, codigobarras.length()-1);
		String digitoControl=codigobarras.substring(codigobarras.length()-1,codigobarras.length());
		for(int contador=0;contador<numeroAComprobar.length();contador++) {
			
			if(impar) {
				acumulador+=Integer.parseInt(Character.toString(numeroAComprobar.charAt(contador)));
			}
			else {
				acumulador+=(Integer.parseInt(Character.toString(numeroAComprobar.charAt(contador))))*3;
			}
			impar=!impar;
		}
		acumulador=acumulador%10;
		acumulador=10-acumulador;
		if(acumulador==Integer.parseInt(digitoControl)) {
			return true;
		}
		else {
			return false;
		}
	}
}

