package ficherosaleatorios;

import org.iesalandalus.programacion.utilidades.Entrada;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ModificarEliminarRegistro {
    static int LONGITUD_REGISTRO = 34;
    static File fichero = new File("datos/AleatorioEmpleados.dat");

    public static void main(String[] args)  {

        int identificador,codigoDepartamento;
        long posicionSalario, posicionInicioRegistro;
        double incrementoSalario,antiguoSalario,nuevosalario;
        String respuesta;

        do {
            System.out.print("Introduce el identificador del empleado a modificar: ");
            identificador= Entrada.entero();// id a modificar
        }while (identificador<=0);

        System.out.println("Se procede a mostrar los datos originales del empleado a modificar.");
        verRegistro(identificador);
        //SE VA A MODIFICAR EL SALARIO Y EL CÓDIGO DEL DEPARTAMENTO
        try
        {
            RandomAccessFile file = new RandomAccessFile(fichero, "rw");

            posicionInicioRegistro = (identificador - 1) * LONGITUD_REGISTRO;
            posicionInicioRegistro = posicionInicioRegistro + 4 + 20; // calcular posición del departamento
            file.seek(posicionInicioRegistro);

            do {
                System.out.print("Introduce el nuevo código de departamento al que pertenece el empleado:");
                codigoDepartamento= Entrada.entero();
            }while (codigoDepartamento<=0);

            file.writeShort(codigoDepartamento); // modificar departamento

            do {
                System.out.print("Introduce el incremento de salario del empleado:");
                incrementoSalario= Entrada.entero();
            }while (incrementoSalario<=0);

            antiguoSalario = file.readDouble();
            nuevosalario= incrementoSalario + antiguoSalario;

            //posicionSalario=posicionInicioRegistro+4+20+2;
            posicionSalario = file.getFilePointer()-8;// calcular posicion del salario
            file.seek(posicionSalario);

            file.writeDouble(nuevosalario);// modificar salario
            file.close(); // cerrar fichero

            System.out.println("Los nuevos datos del empleado tras la actualización son:");
            verRegistro(identificador);
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

        do {
            System.out.print("¿Desea borrar los datos de algún empleado (s/n)?");
            respuesta=Entrada.cadena();
        }while (!respuesta.equalsIgnoreCase("s")&&!respuesta.equalsIgnoreCase("n"));

        if (respuesta.equalsIgnoreCase("s")) {
            do {
                System.out.print("Introduce el identificador del empleado a borrar: ");
                identificador= Entrada.entero();// id a modificar
            }while (identificador<=0);
            borrarRegistro(identificador);
        }


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
                int idf = file.readInt(); // obtengo id de empleado

                if (idf == id) {
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

    private static void borrarRegistro(int identificador)
    {
        int id;	//id del empleado a eliminar

        long posicion = (identificador - 1) * LONGITUD_REGISTRO;

        try
        {
            RandomAccessFile file = new RandomAccessFile(fichero, "rw");

            //si dentro del rango del fichero existente
            if (posicion < fichero.length() && posicion >= 0)
            {
                //mueve apuntador del fichero a la posición=(iden-1)*lreg;
                file.seek(posicion);
                id = file.readInt();
                if (id != -1)
                {
                    id = -1; //marcamos como borrado lógico
                    //hay que reescribir ese id
                    file.seek(posicion);
                    file.writeInt(id);
                    System.out.println("Eliminando empleado: " + identificador);
                }
                else {
                    System.out.println("No existe el empleado: " + identificador);
                }
            }
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
