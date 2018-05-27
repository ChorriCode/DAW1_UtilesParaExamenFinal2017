package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import all.All;

import model.Persona;


/* Proyecto realizado por: Francisco Javier Hernández Sosa
 * 						   I.E.S. El Rincón
 * 						   Las Palmas de Gran Canaria
 * 						   Mayo 2018
 * 						   como herramienta para fines educativos.
 * 						   No se usa ningún patrón como pudiera ser MVC
 * 						   pues es un proyecto para copiar los métodos realizados y usarlos en tu proyecto
 * 						   POR FAVOR, si usas algo de mi proyecto te pido me menciones en los comentarios de tu proyecto.
 */

public class Main {

	public static void main(String[] args) {

		All myAll = new All();
		
		
		Persona myObject = new Persona();
		
		Persona myObject1 = new Persona("31232229Z", "Pedro", LocalDate.of(1980, 1, 1), 170);
		
		myAll.grabarFicheroDeObjetosConObjeto(myObject, "files/ficheroObjetos.obj");
		
		myAll.writeOrAddObjectToBinaryFile(myObject1, "files/ficheroObjetos.obj", true);
		
		ArrayList<Persona> listadoPersonas = new ArrayList<Persona>();
		listadoPersonas.add(myObject);
		listadoPersonas.add(myObject1);
		listadoPersonas.addAll(Arrays.asList(myObject,myObject1));
		
		myAll.grabarFicheroDeObjetosDesdeColecionDeObjetos(listadoPersonas, "files/ficheroObjetos.obj");
		myAll.writeOrAddCollectionToBinaryFile(listadoPersonas, "files/ficheroObjetos.obj", false);
		
		
		ArrayList<Persona> objetosLeidos = myAll.leerFicheroDeObjetos("files/ficheroObjetos.obj");
		
		
		String dataBaseName = "paro";
		String tableName = "provincias";
		String columnName = "provincia";
		
		//Conectamos con la base de datos deseada;
		Connection conn = myAll.connectToBD(dataBaseName);
		
		//hacemos una consulta sobre una tabla en concreto;
		ResultSet result = myAll.readOnBD(conn, tableName);
		
		// mostramos los datos de la consulta anterior
		myAll.showResultSetDatas(result);
		
		// asignamos datos de prueba para posteriormente usarlos en un insert
		String[] columnNames = {"CISLA","ISLA"};
		Object[] valuesOfColumns = {9998,"BORRAMEISLA"};

		
		/*
		// hacemos un insert y recogemos el entero que nos devuelve siendo el valor 1 que todo fue correcto y 0 que no se insertó;
		int errorNumber = myAll.insertOnBD(conn, tableName, columnNames, valuesOfColumns);
		System.out.println(errorNumber);
		
		// para el caso de la tabla islas tenemos la columna CISLA que es su código y la columna ISLA que es su nombre
		int cisla = 9999;
		String isla = "BOORAME";
		String sql = "INSERT INTO " + tableName + "(CISLA,ISLA) VALUES(" + cisla + ",'"+ isla +"')"; // recordar poner comillas simples rodeando a las variables String
		// hacemos un insert pero con otro método que le pasamos la cadena sql
		errorNumber = myAll.insertOnBD(conn, sql);
		System.out.println(errorNumber);
		*/
		
		/*
		// ahora borramos el último dato insertado
		sql = "DELETE FROM " + tableName + " WHERE cisla = " + cisla; // como es una variable numerica no rodeamos el sql con comillas simples
		errorNumber = myAll.deleteOnDB(conn, sql);
		System.out.println(errorNumber + " - " + sql);
		sql = "DELETE FROM " + tableName + " WHERE ISLA = '" + valuesOfColumns[1] + "'"; // recordar poner comillas simples rodeando a las variables String
		errorNumber = myAll.deleteOnDB(conn, sql);
		System.out.println(errorNumber + " - " + sql);
		*/
		
		
		
		/*
		ALGORITMO PARA AÑADIR DATOS A UN ARRAYLIST QUE ESTNA DENTRO DE UN HASHMAP ALGO COMO ESTO HashMap<String, ArrayList<String>>
		mientras haya datos
		si el arraylist esta null en esa clave del hashmap entonces crea un arralist nuevo
		en cualquier caso siempre mete el dato en el arraylist y este en el hashmap
		*/
		
		/*
		PARA RECORRER EL CASO ANTERIOR POR EJEMPLO HashMap<String, ArrayList<String>> datosListado
		necesitamos un for que recorra las claves del hashmap
		y por cada clave que va recorriendo dentro otro for que recorra el ArrayList
		 */
		System.out.println("FIN"); // Tengo un break point para ver los valores de todas las variables antes de salir de la ejecución 
	}

}
