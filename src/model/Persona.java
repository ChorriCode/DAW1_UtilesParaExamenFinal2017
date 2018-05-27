package model;

import java.io.Serializable;
import java.time.LocalDate;



public class Persona implements Serializable, Comparable<Persona> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nif;
	private String nombre;
	private LocalDate fecha;
	private int altura;
	
	private static int contador = 0; // Al ser static de clase se incrementará cada vez que instanciemos un objeto sabien cuantas personas se ha creado


	public Persona() { // constructor sin par�metros
		
		this.nif = "44882229Y";
		this.nombre="Anonimo";
		this.fecha = LocalDate.now();
		this.altura = 180;
		contador++;
	}

	public  Persona(String nif,String nombre, LocalDate fecha, int altura) {

		this.nif = nif;
		this.nombre = nombre;
		this.fecha = fecha;
		this.altura = altura;
		contador++;
		
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static void soyEstatico() {

	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}


	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	@Override
	public String toString() {
		return "Persona [nif=" + nif + ", nombre=" + nombre + ", fecha=" + fecha + ", altura=" + altura + "]";
	}


	public int compareTo(Persona unaPersona) {
		return this.getNif().compareTo(unaPersona.getNif());
		
	}
	

}
