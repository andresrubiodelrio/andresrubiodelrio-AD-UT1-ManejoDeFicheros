
package ficherosbinarios.ficherosobjeto;

import java.io.*;
public class LeerFicheroObjectos
{
	public static void main(String[] args) 
	{
		Persona persona; //define la variable persona
		
		File fichero = new File("datos/FichPersona.dat"); // fichero
		
		try 
		{
			// Creamos el flujo de entrada
			FileInputStream filein = new FileInputStream(fichero);

			// Indicamos que se trata de un flujo de entrada para leer objetos
			ObjectInputStream oIS = new ObjectInputStream(filein);
		
			//lectura del fichero
			do
			{
				persona= (Persona) oIS.readObject(); //leer una persona
				System.out.println("Nombre: "+ persona.getNombre() + ", edad: " + persona.getEdad());
			}while (persona != null);
															
			oIS.close(); //cierra flujo de entrada
			filein.close();
		}
		catch (EOFException eo) 
		{
			System.out.println("El fichero " + fichero.getName() + " se ha leído correctamente.");
		}
		catch (FileNotFoundException ex) 
		{
			System.out.println("No se ha encontrado el fichero " + fichero.getName());
			System.out.println(ex.getMessage());
		} 
		catch (IOException ex) 
		{
			System.out.println("Error de I/O en el fichero " +  fichero.getName());
			System.out.println(ex.getMessage());
		}
		catch (ClassNotFoundException e) 
		{
			System.out.println("No puedo encontrar la clase que tengo que leer desde el fichero " +  fichero.getName());
			System.out.println(e.getMessage());
		} 
	}
}