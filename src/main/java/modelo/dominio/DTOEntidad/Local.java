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
public class Local 
{
    /*
    Aqui se muestran los atributos de la clase y que son privados
    */
    private int id_locales;
    private String nombre;
    private String direccion;
    private String municipio;
    
    /*
    Constructor de la clase que recibe los parametros nombre, apellidos y contraseña
    */

    public Local(int id_locales, String nombre, String direccion, String municipio) 
    {
        this.id_locales = id_locales;
        this.nombre = nombre;
        this.direccion = direccion;
        this.municipio = municipio;
    }

    public int getId_locales() 
    {
        return id_locales;
    }

    public void setId_locales(int id_locales) 
    {
        this.id_locales = id_locales;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    public String getDireccion() 
    {
        return direccion;
    }

    public void setDireccion(String direccion) 
    {
        this.direccion = direccion;
    }

    public String getMunicipio() 
    {
        return municipio;
    }

    public void setMunicipio(String municipio) 
    {
        this.municipio = municipio;
    }

    @Override
    public String toString() 
    {
        return "\nId Local: " + id_locales + "\nNombre: " + nombre + "\nDireccion: " + direccion + "\nMunicipio: " + municipio;
    }
    
    public void ImprimirDatos()
    {
        System.out.println(toString());
    }
    
}
