/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.DTOEntidad;

/**
 *
 * @author Alberto
 */
public class Articulo 
{
    /*
    Aqui se muestran los atributos de la clase y que son privados
    */
    private int idArticulos;
    private String nombre;
    private int precio;
    
    /*
    Constructor de la clase que recibe los parametros nombre, apellidos y contraseña
    */
    public Articulo(int id_articulos, String nombre, int precio) 
    {
        this.idArticulos = id_articulos;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    /*
    Aqui se ponen los set y get de los atributos de la clase
    */
    public int getId_articulos() 
    {
        return idArticulos;
    }

    public void setId_articulos(int idArticulos) 
    {
        this.idArticulos = idArticulos;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    public int getPrecio() 
    {
        return precio;
    }

    public void setPrecio(int precio) 
    {
        this.precio = precio;
    }
    
    /*
    Este metodo lo que hace es imprimir los datos del usuario
    */
    @Override
    public String toString() 
    {
        return "\nId Articulos: " + idArticulos + "\nNombre: " + nombre + "\nPrecio: " + precio;
    }
    
    /*
    Este metodo es similar al toString e imprime los datos del usuario
    */
    public void ImprimirDatos()
    {
        System.out.println(toString());
    }

}
