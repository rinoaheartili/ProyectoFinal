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
import modelo.dominio.DAOEntidadInterfaces.IDAOLocal;
import modelo.dominio.DTOEntidad.Local;

/**
 *
 * @author Alberto
 */
public class LocalDAO implements IDAOLocal
{
    private ArrayList<Local> listaLocal = null;
    private Local local;
    private Mensaje mensaje = new Mensaje();
    private Connection conexion;
    private Statement estado;
    private ResultSet resultado;
    private PreparedStatement insertar, eliminar, modificar;

    public LocalDAO() 
    {
        this.listaLocal = new ArrayList();
        this.local = null;
        this.ObtenerDatos();
    }
    
    public ArrayList<Local> getListaLocal() 
    {
        return listaLocal;
    }
    
    @Override
    public void Agregar(Local local)
    {
        try
        {
            String guardar = "insert into locales values (?, ?, ?, ?)";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.insertar = ConexionOracle.getPreparedStatement(guardar);
            this.insertar.setInt(1, local.getId_locales());
            this.insertar.setString(2, local.getNombre());
            this.insertar.setString(3, local.getDireccion());
            this.insertar.setString(4, local.getMunicipio());
            this.insertar.executeUpdate();
            ConexionOracle.Cerrar(this.estado);
            ConexionOracle.Cerrar(this.insertar);
            ConexionOracle.Cerrar(this.conexion);
        }catch (SQLException | ClassNotFoundException | NullPointerException ex) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al guardar", ex, Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public void Eliminar(int id)
    {
        String eliminarQuery = "delete from locales where id_locales = ?";
        try
        {
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.eliminar = ConexionOracle.getPreparedStatement(eliminarQuery);
            this.eliminar.setInt(1, id);
            this.eliminar.executeQuery();
            ConexionOracle.Cerrar(this.estado);
            ConexionOracle.Cerrar(this.eliminar);
            ConexionOracle.Cerrar(this.conexion);
        }catch (SQLException | ClassNotFoundException | NullPointerException ex) 
        {
            System.out.println("" + ex);
            //mensaje.desplegarMensaje("ERROR", "Error al eliminar", ex, Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public void Modificar(int id, Local local)
    {
        try
        {
            String modificarQuery = "update locales set nombre = ?, direccion = ?, municipio = ? where id_locales = ?";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.modificar = ConexionOracle.getPreparedStatement(modificarQuery);
            this.modificar.setString(1, local.getNombre());
            this.modificar.setString(2, local.getDireccion());
            this.modificar.setString(3, local.getMunicipio());
            this.modificar.setInt(4, id);
            this.modificar.executeUpdate();
            ConexionOracle.Cerrar(this.estado);
            ConexionOracle.Cerrar(this.modificar);
            ConexionOracle.Cerrar(this.conexion);
        }catch (SQLException | ClassNotFoundException | NullPointerException ex) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al modificar", ex, Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public Local Buscar(int id)
    {
        String nombre;
        String direccion;
        String municipio;
        try
        {
            String consultar = "select nombre, direccion, municipio from locales where id_locales = "+ id +"";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.resultado = ConexionOracle.getResultSet(consultar);
            while(this.resultado.next())
            {
                nombre = this.resultado.getString(1);
                direccion = this.resultado.getString(2);
                municipio = this.resultado.getString(3);
                this.local = new Local(id, nombre, direccion, municipio);
            }
            ConexionOracle.Cerrar(this.estado);
            ConexionOracle.Cerrar(this.resultado);
            ConexionOracle.Cerrar(this.conexion);
        }catch (SQLException | ClassNotFoundException | NullPointerException ex) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al buscar", ex, Alert.AlertType.ERROR);
        }
        return this.local;
    }
    
    @Override
    public void ObtenerDatos()
    {
        try
        {
            String consulta = "select * from locales order by 1";
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
                this.local = new Local(Integer.parseInt(filas[0]), filas[1], filas[2], filas[3]);
                this.listaLocal.add(local);
            }
            ConexionOracle.Cerrar(this.estado);
            ConexionOracle.Cerrar(this.resultado);
            ConexionOracle.Cerrar(this.conexion);
        }catch (SQLException | ClassNotFoundException | NullPointerException ex) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al cargar los datos", ex, Alert.AlertType.ERROR);
        }
    }
    
}
