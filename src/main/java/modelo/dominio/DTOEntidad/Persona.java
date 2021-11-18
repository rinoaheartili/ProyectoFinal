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
public abstract class Persona 
{
    /*
    Aqui se muestran los atributos de la clase y que son privados
    */
    private String nombre;
    
    /*
    Este metodo constructor de la clase Persona
    */
    public Persona(String nombre) 
    {
        this.nombre = nombre;
    }
    
    /*
    Aqui se ponen los set y get de los atributos de la clase
    */
    public String getNombre() 
    {
        return nombre;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    @Override
    public String toString() 
    {
        return "\nPersona: "+ "\nNombre: " + nombre;
    }
    
    /*
    Este metodo es similar al toString e imprime los datos del usuario
    */
    public void ImprimirDatos()
    {
        System.out.println(toString());
    }
}
