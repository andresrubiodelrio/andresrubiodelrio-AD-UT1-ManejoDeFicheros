package ficherosaleatorios;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class VisualizarUnRegistro {
    static final int LONGITUD_REGISTRO = 34;
    static File fichero = new File("datos/AleatorioEmpleados.dat");

    public static void main(String[] args)
    {
        verRegistro(1);
        verRegistro(20);
        verRegistro(19);
        verRegistro(5);
        verRegistro(40);
    }

    private static void verRegistro(int id)
    {
        try
        {
            RandomAccessFile file = new RandomAccessFile(fichero, "rw");

            long posicion = (id - 1) * LONGITUD_REGISTRO;

            if (posicion >= file.length())
                System.out.printf("ID: %d, NO EXISTE.%n", id);
            else {
                file.seek(posicion); // nos posicionamos
                int idEmpleado = file.readInt(); // obtengo id de empleado

                if (idEmpleado == id) {
                    // obtener apellido
                    char apellido[] = new char[10], aux;
                    for (int i = 0; i < apellido.length; i++) {
                        aux = file.readChar();//
                        apellido[i] = aux;
                    }
                    String apellidoS = new String(apellido).trim();// convierto a String el array

                    short dep = file.readShort(); // obtener departamento
                    double salario = file.readDouble(); // obtener salario

                    System.out.printf("ID: %d, Apellido: %s, Departamento: %d, Salario: %,9.2f%n", id, apellidoS, dep,
                            salario);
                } else {
                    System.out.printf("ID: %d, NO EXISTE, ES UN HUECO.%n", id);
                }
            }

            file.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("El fichero " + fichero.getName() + " no ha sido encontrado.");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("El modo de lectura escritura del fichero no es correcto.");
        }
        catch (IOException e)
        {
            System.out.println("Se ha producido un error de E/S en el fichero o la posición indicada no es la correcta.");
        }

    }

}
