package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import all.All;
import model.Persona;

public class Main {

	public static void main(String[] args) {

		All myAll = new All();
		
		/*
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
		*/
		
		String dataBaseName = "paro";
		String tableName = "islas";
		String columnName = "isla";
		
		//Conectamos con la base de datos deseada;
		Connection conn = myAll.connectToBD(dataBaseName);
		
		//hacemos una consulta sobre una tabla en concreto;
		ResultSet result = myAll.readOnBD(conn, tableName);
		
		// mostramos los datos de la consulta anterior
		myAll.showResultSetDatas(result);
		
		// asignamos datos de pruena para posterior mente usarios en un insert
		String[] columnNames = {"CISLA","ISLA"};
		Object[] valuesOfColumns = {9999,"BORRAMEISLA"};

		// hacemos un insert y recogemos el entero que nos devuelve siendo el valor 1 que todo fue correcto y 0 que no se insertó;
		int errorNumber = myAll.insertOnBD(conn, tableName, columnNames, valuesOfColumns);
		System.out.println(errorNumber);
		
		
		System.out.println("FIN"); // Tengo un break point para ver los valores de todas las variables antes de salir de la ejecución 
	}

}
