/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.DTOEntidad;

import java.sql.Date;

/**
 *
 * @author Alberto
 */
public class Venta 
{
    /*
    Aqui se muestran los atributos de la clase y que son privados
    */
    private int id_secuencia;
    private String nombre;
    private int cantidad;
    private Date fecha;
    private int total;

    public Venta(int id_secuencia, String nombre, int cantidad, Date fecha, int total) 
    {
        this.id_secuencia = id_secuencia;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.total = total;
    }

    public int getId_secuencia() 
    {
        return id_secuencia;
    }

    public void setId_secuencia(int id_secuencia) 
    {
        this.id_secuencia = id_secuencia;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    public int getCantidad() 
    {
        return cantidad;
    }

    public void setCantidad(int cantidad) 
    {
        this.cantidad = cantidad;
    }

    public Date getFecha() 
    {
        return fecha;
    }

    public void setFecha(Date fecha) 
    {
        this.fecha = fecha;
    }

    public int getTotal() 
    {
        return total;
    }

    public void setTotal(int total) 
    {
        this.total = total;
    }

    @Override
    public String toString() 
    {
        return "\nId Secuencia: " + id_secuencia + "\nNombre: " + nombre + "\nCantidad: " + cantidad + "\nFecha: " + fecha + "\nTotal: " + total;
    }
    
    /*
    Este metodo es similar al toString e imprime los datos del usuario
    */
    public void ImprimirDatos()
    {
        System.out.println(toString());
    }

}
