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
public class Empleado extends Persona
{
    /*
    Aqui se muestran los atributos de la clase y que son privados
    */
    private int id_empleado;
    private String apellido_p;
    private String contrasena;
    private String correo;
    
    /*
    Constructor de la clase que recibe los parametros nombre, apellidos y contraseña
    */
    public Empleado(int id_empleado, String nombre, String apellido_p, String contrasena, String correo) 
    {
        super(nombre);
        this.id_empleado = id_empleado;
        this.apellido_p = apellido_p;
        this.contrasena = contrasena;
        this.correo = correo;
    }
    
    /*
    Aqui se ponen los set y get de los atributos de la clase
    */
    public int getId_empleado() 
    {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) 
    {
        this.id_empleado = id_empleado;
    }

    public String getApellido_p() 
    {
        return apellido_p;
    }

    public void setApellido_p(String apellido_p) 
    {
        this.apellido_p = apellido_p;
    }

    public String getContrasena() 
    {
        return contrasena;
    }

    public void setConstraseña(String contrasena) 
    {
        this.contrasena = contrasena;
    }

    public String getCorreo() 
    {
        return correo;
    }

    public void setCorreo(String correo) 
    {
        this.correo = correo;
    }
    
    /*
    Este metodo lo que hace es imprimir los datos del usuario
    */
    @Override
    public String toString() 
    {
        return "\nId Empleado: " + this.id_empleado + "\nNombre: " + super.getNombre() + "\nApellido Paterno: " + this.apellido_p + "\nConstrasena: " + this.contrasena + "\nCorreo: " + this.correo;
    }

    /**
     *Este metodo es similar al toString e imprime los datos del usuario
     */
    @Override
    public void ImprimirDatos()
    {
        System.out.println(toString());
    }
    
}
