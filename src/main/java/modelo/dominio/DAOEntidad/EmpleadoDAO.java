/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.DAOEntidad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import modelo.app.Mensaje;
import modelo.app.persistencia.ConexionOracle;
import modelo.dominio.DAOEntidadInterfaces.IDAOEmpleado;
import modelo.dominio.DTOEntidad.Empleado;

/**
 *
 * @author Alberto
 */
public class EmpleadoDAO implements IDAOEmpleado
{
    private ArrayList<Empleado> listaEmpleado = null;
    private Empleado empleado;
    private Mensaje mensaje = new Mensaje();
    private Connection conexion;
    private Statement estado;
    private ResultSet resultado;
    private PreparedStatement insertar, eliminar, modificar;
    
    public EmpleadoDAO() 
    {
        this.listaEmpleado = new ArrayList();
        this.empleado = null;
        this.ObtenerDatos();
    }

    public ArrayList<Empleado> getListaEmpleado() 
    {
        return listaEmpleado;
    }
    
    @Override
    public void Agregar(Empleado empleado)
    {
        try
        {
            String guardar = "insert into empleados values (?, ?, ?, ?, ?)";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.insertar = ConexionOracle.getPreparedStatement(guardar);
            this.insertar.setInt(1, empleado.getId_empleado());
            this.insertar.setString(2, empleado.getNombre());
            this.insertar.setString(3, empleado.getApellido_p());
            this.insertar.setString(4, empleado.getContrasena());
            this.insertar.setString(5, empleado.getCorreo());
            this.insertar.executeUpdate();
            ConexionOracle.Cerrar(this.estado);
            ConexionOracle.Cerrar(this.insertar);
            ConexionOracle.Cerrar(this.conexion);
        }catch (SQLException | ClassNotFoundException ex) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al guardar", ex, Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public void Eliminar(int id)
    {
        try
        {
            String eliminarQuery = "delete from empleados where id_empleado = ?";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.eliminar = ConexionOracle.getPreparedStatement(eliminarQuery);
            this.eliminar.setInt(1, id);
            this.eliminar.executeUpdate();
            ConexionOracle.Cerrar(this.estado);
            ConexionOracle.Cerrar(this.eliminar);
            ConexionOracle.Cerrar(this.conexion);
        }catch (SQLException | ClassNotFoundException ex) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al eliminar", ex, Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public void Modificar(int id, Empleado empleado)
    {
        try
        {
            String modificarQuery = "update empleados set nombre = ?, apellido_p = ?, contrasena = ?, correo = ? where id_empleado = ?";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.modificar = ConexionOracle.getPreparedStatement(modificarQuery);
            this.modificar.setString(1, empleado.getNombre());
            this.modificar.setString(2, empleado.getApellido_p());
            this.modificar.setString(3, empleado.getContrasena());
            this.modificar.setString(4, empleado.getCorreo());
            this.modificar.setInt(5, id);
            this.modificar.executeUpdate();
            ConexionOracle.Cerrar(this.estado);
            ConexionOracle.Cerrar(this.modificar);
            ConexionOracle.Cerrar(this.conexion);
        }catch (SQLException | ClassNotFoundException ex) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al modificar", ex, Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public Empleado Buscar(int id)
    {
        String nombre;
        String apellidoP;
        String contrasena;
        String correo;
        try
        {
            String consultar = "select nombre, apellido_p, contrasena, correo from empleados where id_empleado = "+ id +"";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.resultado = ConexionOracle.getResultSet(consultar);
            while(this.resultado.next())
            {
                nombre = this.resultado.getString(1);
                apellidoP = this.resultado.getString(2);
                contrasena = this.resultado.getString(3);
                correo = this.resultado.getString(4);
                this.empleado = new Empleado(id, nombre, apellidoP, contrasena, correo);
            }
            ConexionOracle.Cerrar(this.estado);
            ConexionOracle.Cerrar(this.resultado);
            ConexionOracle.Cerrar(this.conexion);
        }catch (SQLException | ClassNotFoundException ex) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al buscar", ex, Alert.AlertType.ERROR);
        }
        return this.empleado;
    }
    
    @Override
    public void ObtenerDatos()
    {
        try
        {
            String consulta = "select * from empleados order by 1";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.resultado = ConexionOracle.getResultSet(consulta);
            String filas[] = new String[5];
            while(this.resultado.next())
            {
                filas[0] = String.valueOf(this.resultado.getInt(1));
                filas[1] = this.resultado.getString(2);
                filas[2] = this.resultado.getString(3);
                filas[3] = this.resultado.getString(4);
                filas[4] = this.resultado.getString(5);
                this.empleado = new Empleado(Integer.parseInt(filas[0]), filas[1], filas[2], filas[3], filas[4]);
                this.listaEmpleado.add(empleado);
            }
            ConexionOracle.Cerrar(this.estado);
            ConexionOracle.Cerrar(this.resultado);
            ConexionOracle.Cerrar(this.conexion);
        }catch (SQLException | ClassNotFoundException ex) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al cargar los datos", ex, Alert.AlertType.ERROR);
        }
    }
    
}
