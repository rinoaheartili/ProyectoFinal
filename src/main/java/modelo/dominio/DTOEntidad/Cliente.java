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
public class Cliente extends Persona
{
    /*
    Aqui se muestran los atributos de la clase y que son privados
    */
    private int idCliente;
    private String calle;
    private int numero;
    private String telefono;
    
    /*
    Constructor de la clase que recibe los parametros nombre, apellidos y contraseña
    */
    public Cliente(int idCliente, String nombre, String calle, int numero, String telefono) 
    {
        super(nombre);
        this.idCliente = idCliente;
        this.calle = calle;
        this.numero = numero;
        this.telefono = telefono;
    }
    
    /*
    Aqui se ponen los set y get de los atributos de la clase
    */
    public int getIdCliente() 
    {
        return idCliente;
    }

    public void setIdCliente(int idCliente) 
    {
        this.idCliente = idCliente;
    }

    public String getCalle() 
    {
        return calle;
    }

    public void setCalle(String calle) 
    {
        this.calle = calle;
    }

    public int getNumero() 
    {
        return numero;
    }

    public void setNumero(int numero) 
    {
        this.numero = numero;
    }

    public String getTelefono() 
    {
        return telefono;
    }

    public void setTelefono(String telefono) 
    {
        this.telefono = telefono;
    }
    
    /*
    Este metodo lo que hace es imprimir los datos del usuario
    */
    @Override
    public String toString() 
    {
        return "\nId Usuario: " + idCliente + "\nNombre: " + super.getNombre() + "\nCalle: " + calle + "\nNumero: " + numero + "\nTelefono: " + telefono;
    }
    
    /*
    Este metodo es similar al toString e imprime los datos del usuario
    */
    @Override
    public void ImprimirDatos()
    {
        System.out.println(toString());
    }
    
}
