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
		String tableName = "municipios";
		String columnName = "municipio";
		
		Connection conn = myAll.connectToBD(dataBaseName);
		ResultSet result = myAll.readOnBD(conn, tableName);
		int maxLenghtDataColumn = myAll.readOnTableMaxLenghtDataOfColumn(conn, tableName, columnName);
		myAll.showResultSetDatas(result);
		System.out.println("FIN"); // Tengo un break point para ver los valores de todas las variables antes de salir de la ejecución 
	}

}
