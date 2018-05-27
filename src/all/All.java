package all;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

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


public class All {
	
	
	// MUY IMPORTANTE: los métodos de leectura siguientes son void, por lo que no devuelven lo que van a cargar al fichero porque se supone
	// que donde los vas a usar hay un atributo o variable como en este caso listado o listadoConColecion que van a ir llenandose desde los métodos de lectura.
	// Pero si por ejemplo necesitamos que devuelvan los dato que leen del fichero tan solo debemos cambiar el void por el tipo de HashMap que estás llenando dentro del método
	// y antes de salir del metodo hacer un return de la variable que es el HashMap que estás llenando de datos
	
	// ---------------- LEER UN FICHERO DE TEXTO A UN HASHMAP QUE EL VALOR NO ES UNA COLECCION -------------
	
	private HashMap<Integer, Float> listado = new HashMap<Integer, Float>();   // cambiar los tipos clave valor a los que se necesite
	
	public void leerFicheroTextoAlmacenarEnColeccion(String rutaFicheroTexto) {		
		try {
			FileReader fr = new FileReader(rutaFicheroTexto);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while ( ( linea = br.readLine() ) != null) {				
				String[] datosLinea = linea.split("&&");
				// a continuacion deberemos dejar solo las lineas de datos que nos interesen segun sea tu caso
				int dato0 		= Integer.parseInt(datosLinea[0]);
				float dato1		= Float.parseFloat(datosLinea[1]);
				double dato2	= Double.parseDouble(datosLinea[2]);
				LocalDate dato3	= LocalDate.parse(datosLinea[3], DateTimeFormatter.ofPattern("yyyy/MM/dd"));
				
				listado.put(dato0, dato1);		// añadimos datos al HashMap. Cambiar aquí las variables que son parámetros dependiendo de que dato es la clave y cual es el valor					
			}

			br.close();
			fr.close();		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	// ---------------- GRABAR UN FICHERO DE TEXTO DE DATOS QUE ESTAN EN UN HASHMAP DONDE EL VALOR NO ES UNA COLECCION -------------
	
	
	// El método que graba necesita informacion y son los dos parámentros que recibe:
	// El listado de datos están en el HashMap, cambiar los tipos clave valor a los que se necesite
	// y la ruta del fichero más el nombre que llevará el fichero, por ejemplo:  "ficheros/ficheroGrabado.txt" 
	// esos parámetros se los pasaremos cuando llamemos al método desde main o desde donde sea
	
	public void grabarColeccionEnFicheroTexto(HashMap<Integer, Float> listadoParaGrabar, String rutaFicheroTexto) {		
		try {
			FileWriter fw = new FileWriter(rutaFicheroTexto);
			BufferedWriter bw = new BufferedWriter(fw);
			
			// recorremos el HashMap con un ForEach pero tenemos que llamar al método keySet() para que nos devuelva solos las claves que tiene
			// el HashMap, asi asignando cada clave en la variable -clave- podemos ir extrayendo uno por uno los valores
			for ( Integer clave : listadoParaGrabar.keySet()) {
				String linea = ""; // inicializamos la variable linea que vamos a usar para grabar la cadena en el fichero de texto para que esté vacía antes de meterle datos por cada vuelta
				// como linea es de tipo String le asignamos una cadena que contenga la clave del HashMap seguido de un separador "&&" y por último con get optenemos
				// el dato o valor que le corresponde a la clave y que está en el HashMap que nos vino por parámentro en el método.
				// nos quedará una cadena algo sí "clave&&valor". por ejemplo "1234&&54.66f"
				linea = clave + "&&" +  listadoParaGrabar.get((clave)) + "\n";  // recuerda que cuando grabamos en fichero de texto a cada linea añadimos al final "\n" para que las añada una debajo de otra
				bw.write(linea);
			}
			
			bw.close();
			fw.close();		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	// =================================================================================================================
	
	
	// ---------------- LEER UN FICHERO DE TEXTO A UN HASHMAP QUE EL VALOR ES UNA COLECCION -------------
	
	private HashMap<Integer, ArrayList<Float>> listadoConColecion = new HashMap<Integer, ArrayList<Float>>();
	
	public void leerFicheroTextoRetornarColecion(String rutaFicheroTexto) {
		ArrayList<Float> listadoDeCadaClaveHashMap = null;
		try {
			FileReader fr = new FileReader(rutaFicheroTexto);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while ( ( linea = br.readLine() ) != null) {				
				String[] datosLinea = linea.split("&&");
				int dato0 		= Integer.parseInt(datosLinea[0]);
				float dato1		= Float.parseFloat(datosLinea[1]);
				//double dato2	= Double.parseDouble(datosLinea[2]);
				//LocalDate dato3	= LocalDate.parse(datosLinea[3], DateTimeFormatter.ofPattern("yyyy/MM/dd"));	
				
				//este if es el que pregunta si el ArrayList que pertenece a la clave que estamos recorriendo en este momento está null o creado
				//si ese ArrayList no está creado, dentro del if creamos el ArrayList y aprovechamos para añadirle el primer dato
				if (listadoConColecion.get(dato0) == null) {   // la variable dato0 representa la clave que es integer, cambiar por la que sea la correpondiente
					listadoDeCadaClaveHashMap = new ArrayList<Float>();
					listadoDeCadaClaveHashMap.add(dato1);		// aquí dato1 son los floats que van dentro del ArrayList que está dentro del HashMap, cambiarlo segun sea el tipo de tu ArrayList
					listadoConColecion.put(dato0, listadoDeCadaClaveHashMap);
				} 
				//en caso de que el ArrayList que pernece a la clave en la que estamos ahora posicionados ya esté creado, simplemente añadimos a dicho ArrayList un dato con el add(), 
				//haciendo referentecia con el get() a que ese dato se añade al ArrayList correspondiente a la clave del HashMap
				else {
					listadoConColecion.get(dato0).add(dato1);	
				}				
			}

			br.close();
			fr.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ---------------- GRABAR UN FICHERO DE TEXTO DE DATOS QUE ESTAN EN UN HASHMAP DONDE EL VALOR ES UNA COLECCION -------------
	
	public void grabarColeccionDosDimensionesEnFicheroTexto(HashMap<Integer, ArrayList<Float>> listadoConColecionParaGrabar, String rutaFicheroTexto) {
		ArrayList<Float> listadoDeCadaClaveHashMap = null;
		try {
			FileWriter fw = new FileWriter(rutaFicheroTexto);
			BufferedWriter bw = new BufferedWriter(fw);
			
			// recorremos el HashMap con un ForEach pero tenemos que llamar al método keySet() para que nos devuelva solos las claves que tiene
			// el HashMap, asi asignando cada clave en la variable -clave- podemos ir extrayendo uno por uno los valores
			for ( Integer clave : listadoConColecionParaGrabar.keySet()) {
				
				listadoDeCadaClaveHashMap = listadoConColecionParaGrabar.get(clave); // asignamos a la variable el ArrayList que le corresponde a la clave actual
				for (Float datoFloat : listadoDeCadaClaveHashMap) {
					// inicializamos la variable linea que vamos a usar para grabar la cadena en el fichero de texto para que esté vacía antes de meterle datos por cada vuelta
					String linea = ""; 
					// como linea es de tipo String le asignamos una cadena que contenga la clave del HashMap seguido de un separador "&&" y 
					// el datoFloat que tiene en el ArrayList, de esta manera recorremos todos los floats que están en el ArrayLista que le corresponde
					// a la clave y que está en el HashMap que nos vino por parámentro en el método.
					// Cuándo terminemos con ese for que recorre el ArrayList de Floats, regresaremos al For que hay fuera y que irá a una nueva clave del HashMap
					// para recorrer un nuevo ArrayList.
					linea = clave + "&&" +  datoFloat + "\n";
					bw.write(linea);
				}

			}
			bw.close();
			fw.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// =================================================================================================================
	
	// ---------------- LEER UN FICHERO DE OBJETOS -------------
	
	
	public ArrayList<Persona> leerFicheroDeObjetos(String rutaFichero) {
	
		ArrayList<Persona> result = new ArrayList<Persona>();
		try {
			FileInputStream fis = new FileInputStream(rutaFichero);
			ObjectInputStream ois = new ObjectInputStream(fis);
			while (fis.available() > 0) {
				
				
				Persona obj = (Persona) ois.readObject(); // Cambiar Persona por la Clase que necesites que lea del fichero
				// una vez vamos recogiendo los diferentes objetos en la variable obj vemos si el ejercicio
				// nos pide que los metamos en alguna coleccion ArrayList o HashMap, recordando que para meter en un ArrayList usamos generalmente
				// add(), y para añadirlos a un HashMap usamos put, por lo que si es algo más complicado como un HashMap dentro de un ArrayList o
				// viceversa tendremos que usar una combinacion de add() y put().
				result.add(obj);

				
			}
			fis.close();
			//ois.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error de entrada");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFound");
		}
		return result;
	}
	
	
	// ---------------- GRABAR EN UN FICHERO DE OBJETOS UN SOLO OBJETO POR LLAMADA,  PERMITIENDO AÑADIR MAS POR CADA LLAMADA AL MÉTODO SIN BORRAR LOS QUE YA TENIA EL FICHERO  -------------
	
	public void grabarFicheroDeObjetosConObjeto(Persona miPersona, String rutaFichero) {
		
		try {
			FileOutputStream fos = new FileOutputStream(rutaFichero); // añadiendo , true conseguimos que el fichero añada más objetos a los que ya tiene guardados
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(miPersona); 
			fos.close();
			oos.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error salida");
		}
	}


	// ---------------- GRABAR EN UN FICHERO DE OBJETOS MUCHOS OBJETOS QUE NOS LLEGAN DESDE UNA COLECCION, NO GRABAMOS LA COLECCION SINO CADA OBJETO QUE CONTIENE  -------------
	// ---------------- EL FICHERO TIENE QUE ESTAR PREPARADO PARA AÑADIR NUEVOS OBJETOS SI VOLVEMOS A LLAMAR AL METODO SIN QUE LOS QUE YA TENIA SE BORREN  -------------
	
	public void grabarFicheroDeObjetosDesdeColecionDeObjetos(ArrayList<Persona> listadoPersonas, String rutaFichero) {
		
		try {
			FileOutputStream fos = new FileOutputStream(rutaFichero); // añadiendo , true conseguimos que el fichero añada más objetos a los que ya tiene guardados
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			for (Persona persona : listadoPersonas) {			
				oos.writeObject(persona); 
			}
			
			fos.close();
			oos.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error salida");
		}
	}
	
	// =================================================================================================================
	
	// ---------------- GRABAR OBJETOS DESDE COLECCION PERO CON LA POSIBILIDAD DE AÑADIR PASANDOLE TRUE EN EL PARAMETRO APPEND -------------
	
    public void writeOrAddCollectionToBinaryFile(ArrayList<Persona> listadoPersonas, String rutaFichero, boolean append){
    	
        File file = new File (rutaFichero);
        
        ObjectOutputStream out = null;

        try{
            if (!file.exists () || !append) out = new ObjectOutputStream (new FileOutputStream (rutaFichero));
            else out = new AppendableObjectOutputStream (new FileOutputStream (rutaFichero, append));
			for (Persona persona : listadoPersonas) {			
				out.writeObject(persona);
				out.flush ();
			}
            
            
        }catch (Exception e){
            e.printStackTrace ();
        }finally{
            try{
                if (out != null) out.close ();
            }catch (Exception e){
                e.printStackTrace ();
            }
        }
    }
    
 // ---------------- GRABAR OBJETOS UNO A UNO PERO CON LA POSIBILIDAD DE AÑADIR PASANDOLE TRUE EN EL PARAMETRO APPEND -------------
    
    public void writeOrAddObjectToBinaryFile(Persona unaPersona, String rutaFichero, boolean append){
    	
        File file = new File (rutaFichero);
        
        ObjectOutputStream out = null;

        try{
            if (!file.exists () || !append) out = new ObjectOutputStream (new FileOutputStream (rutaFichero));
            else out = new AppendableObjectOutputStream (new FileOutputStream (rutaFichero, append));
					
				out.writeObject(unaPersona);
				out.flush ();
			
            
            
        }catch (Exception e){
            e.printStackTrace ();
        }finally{
            try{
                if (out != null) out.close ();
            }catch (Exception e){
                e.printStackTrace ();
            }
        }
    }
    
    private static class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
          super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {}
  }
	
	// =================================================================================================================
	
	// ---------------- ORDENAR OBJETOS POR ALGUNO DE SUS ATRIBUTOS -------------
	
	public void ordenaObjeto(Object[] myObject) {
		// Implementamos la interfaz Comparable en la clase que queremos que el método compareTo haga la comparacion, en el ejemplo hemos puesto Object pero lo cambiaremos por la que queramos usar
		// como sería la implementacion del método compareTo() en la clase que sea. por ejemplo en una clase Estudiante:
				//public int compareTo(Estudiante estudiante) {
					//return this.getFecha().compareTo(estudiante.getFecha());
		// Recuerda que la cabecera de la clase debe ser así:
					//public class Estudiante implements Comparable<Estudiante> { }
			
		
		// recuerda que compareTo() comparará uno de los atributos de la no todos. por ejemplo que compare la edad con otro objeto de la misma clase

		/* COMENTAMOS EL CODIGO HASTA QUE EL METODO COMPARETO() ESTA EN LA CLASE QUE SUSITUYE A OBJECT EN TU CODIGO
		for (int i = 0; i < myObject.length - 1; i++)
			for (int j = i + 1; j < myObject.length; j++)
				if (myObject[i].compareTo(myObject[j]) > 0) {   
					Object aux = myObject[i];
					myObject[i] = myObject[j];
					myObject[j] = aux;
				}
		*/
	}
	
	
	
	// =================================================================================================================
	
	// ---------------- OBTENER LA CANTIDAD DE FILAS DE UNA CONSULTA A TRAVES DE SU RESULT SET-------------
	
	private int getRowCount(ResultSet resultSet) {
	    if (resultSet == null) {
	        return 0;
	    }
	    try {
	        resultSet.last();
	        return resultSet.getRow();
	    } catch (SQLException exp) {
	        exp.printStackTrace();
	    } finally {
	        try {
	            resultSet.beforeFirst();
	        } catch (SQLException exp) {
	            exp.printStackTrace();
	        }
	    }
	    return 0;
	}
	
	
	// =================================================================================================================

	// ---------------- METODO PARA CONECTAR A MYSQL, EN ESTE EJEMPLO LA BASE DE DATOS SE LLAMA "PARO" CAMBIALA A LA QUE NECESITES CONECTAR -------------
	
	
	public Connection connectToBD(String dataBase) {	
		Connection conn = null;
		try {
			
			//Registro el driver JDBC
			Class.forName("com.mysql.jdbc.Driver").newInstance();;		
			//Abrimos una coneccion a la Base de Datos
			//System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dataBase,"root","");	 // cadena de conexion mysql + localhost y puerto / nombre la base de datos + usurio y contraseña	
			//System.out.println("Database connection ok...");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		return conn;
	}
	
	
	// ---------- METODO HECHO EN CLASE, NO ES MIO. -------------- 
	//OPTIENE LOS DATOS DE UNA TABLA SIENDO:  EL HASHMAP INTERNO LOS DATOS DE UNA FILA DONDE LA CLAVE - STRING ES EL NOMBRE DE LA COLUMNA Y EL OBJECT SU VALOR
	//EL ARRAYLIST EXTERNO SERIA CADA UNA DE LAS FILAS DE LA TABLA 
	
	public ArrayList<HashMap<String, Object>> getAllRecords(String dataBaseName, String tableName) {

		try {
			ArrayList<HashMap<String, Object>> registros = new ArrayList<HashMap<String, Object>>();
			Connection conexion = connectToBD(dataBaseName);
			String sql = "SELECT * FROM " + tableName;
			// + " where 1=2"
			Statement stm = conexion.createStatement();
			ResultSet rs = stm.executeQuery(sql);

			ResultSetMetaData metaData = rs.getMetaData();
			rs.first();
			if (rs.getRow() == 0) {
				System.out.println("NO HAY REGISTROS");
				stm.close();
				rs.close();
				return null;
			} else
				rs.beforeFirst();
			while (rs.next()) {

				HashMap<String, Object> registro = new HashMap<String, Object>();
				registros.add(registro);
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					registro.put(metaData.getColumnName(i), rs.getString(i));
					System.out.print(metaData.getColumnName(i) + " => " + rs.getString(i) + "\t");
				}

				System.out.println();
			}

			stm.close();
			rs.close();
			return registros;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	// ---------- METODO HECHO EN CLASE, NO ES MIO. -------------- 
	//OPTIENE LOS DATOS DE UNA TABLA SIENDO:  EL ARRAYLIST INTERNO LOS DATOS DE UNA FILA AL SER UN OBJETO SE ENTIENDE CON LOS ATRIBUTOS DE ESE OBJETOS SON LAS COLUMNAS
	//EL ARRAYLIST EXTERNO SERIA CADA UNA DE LAS FILAS DE LA TABLA 
	
	
	public ArrayList<ArrayList<Object>> getAllRecords2(String dataBaseName, String tableName) {

		try {
			ArrayList<ArrayList<Object>> registros = new ArrayList<ArrayList<Object>>();
			Connection conexion = connectToBD(dataBaseName);
			String sql = "SELECT * FROM " + tableName;
			Statement stm = conexion.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			// primera fila: nombres de campos
			ArrayList<Object> registro = new ArrayList<Object>();
			registros.add(registro);
			for (int i = 1; i <= metaData.getColumnCount(); i++)
				registro.add(metaData.getColumnName(i));
			// resto filas: valores de los campos
			while (rs.next()) {
				registro = new ArrayList<Object>();
				registros.add(registro);
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					registro.add(rs.getString(i));
					System.out.print(metaData.getColumnName(i) + " => " + rs.getString(i) + "\t");
				}
				System.out.println();
			}
			stm.close();
			rs.close();
			return registros;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	// ---------------- METODO PARA HACER UNA CONSULTA SELECT DE LECTURA A LA BASE DE DATOS QUE HAY EN EL METODO CONNECT TO DB Y LA TABLA DADA EN EL PARAMETRO -------------
	
	public ResultSet readOnBD(Connection conn, String table) {
		ResultSet myResultSet = null;
		
		String sql ="SELECT * FROM " + table; // Cadena de consulta a la tabla islas y en este caso recordemos que la conexion es a la base de datos paro
		try {
			
			Statement myStatement = conn.createStatement();
			myStatement.execute("use "+ conn.getCatalog());
			myResultSet = myStatement.executeQuery(sql);	
			
		} catch (SQLException e) {;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return myResultSet;
	}
	
	// =================================================================================================================
	
	// ---------------- METODO PARA HACER UN INSERT LA BASE DE DATOS QUE HAY EN EL METODO CONNECT TO DB Y LA TABLA DADA EN EL PARAMETRO -------------
	
	public int insertOnBD(Connection conn, String table, String[] columnNames, Object[] valuesOfColumns) {
		int insertReturnCode = 0;
		String insertSimpleQuotes = ""; // la usaremos dentro del String SQL para con un condicional preguntar si el valor es de la clase String querremos rodearlo de comillas simples

		// A partir de aquí vamos a preparar el insert SQL concatenando el Array de String que nos llega con los nombres de columnas y el Array de Objetos con los valores
		// El de valores es de objetos para que al menos admita String e int. no está preparado aún para otros tipos de datos como LocalDate
		String sql ="INSERT INTO " + table + "(";
		for (int i = 0; i < columnNames.length; i++) {
			
			if (i != columnNames.length-1) // no queremos que el último dato termina con una ","
				sql += columnNames[i] + ",";
			else
				sql += columnNames[i];
		}
		sql += ") VALUES (";
		for (int i = 0; i < valuesOfColumns.length; i++) {
			
			if (valuesOfColumns[i].getClass() == String.class) 
				insertSimpleQuotes = "'"; // hemos preguntado en el if anterior si la clase del dato valor que nos llega es de tipo String para rodearlo de comillas simples
			else
				insertSimpleQuotes = ""; // en caso de que no sea un String no lo rodeará de comillas simples en este caso valer para datos de tipo numericos
			
			if (i != valuesOfColumns.length-1)
				sql += insertSimpleQuotes + valuesOfColumns[i] + insertSimpleQuotes + ",";
			else
				sql += insertSimpleQuotes + valuesOfColumns[i] + insertSimpleQuotes;
		}
		sql += ")"; // Cadena de consulta a la tabla islas y en este caso recordemos que la conexion es a la base de datos paro
		
		
		try {
			
			Statement myStatement = conn.createStatement();
			insertReturnCode = myStatement.executeUpdate(sql); // Si la insercción fue correcta insertReturnCode =  1  sino será 0
			//conn.close();	

		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("***** INSERTAR DUPLICADO *****");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return insertReturnCode;
	}
	
	// ---------------- METODO PARA HACER UN INSERT LA BASE DE DATOS SE DEBE RELLENAR DENTRO DEL METODO LOS NOMBRES DE COLUMNAS Y LAS VARIABLES DE LAS COLUMNAS -------------
	
	public int insertOnBD(Connection conn, String sql) {
		int insertReturnCode = 0;
		
		try {
			
			Statement myStatement = conn.createStatement();
			insertReturnCode = myStatement.executeUpdate(sql); // Si la insercción fue correcta insertReturnCode =  1  sino será 0
			//conn.close();	

		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("***** INSERTAR DUPLICADO *****");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return insertReturnCode;
	}
	// =================================================================================================================
	
	// ---------------- METODO PARA HACER UN DELETE LA BASE QUE ESTÁ DEFINIDDA EN EL METODO connectToBD() Y LA TABLA DADA EN EL PARAMETRO -------------
	
	
	public int deleteOnDB(Connection conn, String sql) {
		int insertReturnCode = 0;
		try {
			Statement myStatement = conn.createStatement();
			insertReturnCode = myStatement.executeUpdate(sql);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return insertReturnCode;
		
	}
	
	
	// =================================================================================================================
	
	// ---------------- METODO PARA HACER UNA CONSULTA SELECT DE LECTURA A LA BASE DE DATOS QUE ME DEVUELVA QUE LONGITUD TIENE EL DATO MAS LARGO DE UNA COLUMNA ESPECIFICADA -------------
	
	public int readOnTableMaxLenghtDataOfColumn(Connection conn, String table, String columnName) {
		ResultSet myResultSet = null;
		int  result = 0;
		
		String sql = "SELECT MAX(LENGTH(" + columnName + ")) FROM " + table; // Cadena de consulta cual es el dato de mayor longitud de la columna dada
		try {
			
			Statement myStatement = conn.createStatement();
			myStatement.execute("use "+ conn.getCatalog());
			myResultSet = myStatement.executeQuery(sql);
			myResultSet.next();
			result = myResultSet.getInt(1);
		} catch (SQLException e) {;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	// =================================================================================================================

	// ---------------- ESTE METODO RELLENA LINAS CON EL CARACTER QUE LE PASES EN filler Y TANTOS ESPACIOS COMO PONGAS EN spaces -------------
	
	public String hyphenatedFill(int spaces, String filler) {
		String fill = "";
		
		for (int i = 0; i < spaces; i++) {
			fill += filler;
		}

		return fill;
	}
	
	
	// =================================================================================================================

	// ---------------- ESTE METODO MUESTRA LOS DATOS DE UN RESULT SET Y SE ADAPTA AL NUMERO DE COLUMNAS QUE TENGA EL RESULSET -------------
	
	public  void showResultSetDatas(ResultSet resultDatas ) {
		ResultSetMetaData metaDatas = null;
		String dataBaseName = null;
		String tableName = null;
		int columnsCount = 0;
		String[] columnNames = null;
		int[] maxLengthByColumns = null;
		

		try {
			
			//comprobamos si el resultDatas que nos envían es null nos salimos del método, no hay nada que mostrar
			if (resultDatas != null) {
				resultDatas.first();
				
				// en caso de que no sea nulo, todavía puede pasar que la tabla no tenga datos y por tanto tampoco hay nada que mostrar;
				if (resultDatas.getRow() == 0) {
					System.out.println("NO HAY REGISTROS");		
					resultDatas.close();
					return;
				} else
					resultDatas.beforeFirst();
			} else {
				System.out.println("ResulSet NULL");
				return;
			}

			
			metaDatas = resultDatas.getMetaData();
			dataBaseName = metaDatas.getCatalogName(1);
			tableName = metaDatas.getTableName(1).toUpperCase();
			columnsCount = metaDatas.getColumnCount();
			columnNames = new String[columnsCount];
			maxLengthByColumns = new int[columnsCount];

			for (int i = 0; i < columnsCount; i++) {
				columnNames[i] = metaDatas.getColumnLabel(i+1).toUpperCase();  // como el array empieza en cero el índice es  i y las columnas comienzan en 1 el índice es  i+1
			}
			
			
			System.out.println("\r\r**************************************************");
			// la siguiente linea tan solo rellena de asteriscos el nombre de la tabla a ambos lados por eso hace unos calculos
			// sambiendo que la linea sin texto tiene 50 asteriscos pues segun sea la longitud del nombre de la tabla
			// dividimos entre 2 partes y le restamo lo que mida el nombre de la tabla para que quede en el centro
			System.out.println(hyphenatedFill((50-tableName.length())/2,"*") + tableName + hyphenatedFill((50-tableName.length())/2+1,"*")); 
			System.out.println("**************************************************\r");
			
			
			for (int i = 0; i < columnsCount; i++) {
				// en la siguiente variable almacenamos la logitud max que va a tener el ancho de la columna despues de consultar a la BD con el método readOnTableMaxLenghtDataOfColumn()
				maxLengthByColumns[i] = readOnTableMaxLenghtDataOfColumn(connectToBD(dataBaseName), tableName, columnNames[i]);
				// ya sabiendo la logitud del dato más ancho de cada columna separamos las columnas nos el método hyphenatedFill();
				System.out.print(columnNames[i] + hyphenatedFill(maxLengthByColumns[i],""));
			}
			System.out.println();
			while (resultDatas.next()) {
				
			for (int i = 0; i < columnsCount; i++) {
				// repguntamos si estamos leyendo la última columna de datos, si es así al final de ella no añadimos más ".", si son las columnas anteriores separamos los datos con "."
				// el resto del código es un cálculo para que todas las columnas estén alineadas
				if (i == columnsCount-1) {
					System.out.print(resultDatas.getObject(i+1) + hyphenatedFill(maxLengthByColumns[i] + columnNames[i].length()-resultDatas.getObject(i+1).toString().length(), ""));
				} else {
					System.out.print(resultDatas.getObject(i+1) + hyphenatedFill(maxLengthByColumns[i] + columnNames[i].length()-resultDatas.getObject(i+1).toString().length(), "."));
				}
				
				
			}
			System.out.println();
				
				
				
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		
	}
	
}
