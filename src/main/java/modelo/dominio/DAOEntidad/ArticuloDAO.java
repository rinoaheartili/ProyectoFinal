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
import modelo.dominio.DAOEntidadInterfaces.IDAOArticulo;
import modelo.dominio.DTOEntidad.Articulo;

/**
 *
 * @author Alberto
 */
public class ArticuloDAO implements IDAOArticulo
{
    private ArrayList<Articulo> listaArticulos = null;
    private Articulo articulo;
    private Mensaje mensaje = new Mensaje();
    private Connection conexion;
    private Statement estado;
    private ResultSet resultado;
    private PreparedStatement insertar, eliminar, modificar;

    public ArticuloDAO() 
    {
        this.listaArticulos = new ArrayList();
        this.articulo = null;
        this.ObtenerDatos();
    }

    public ArrayList<Articulo> getListaArticulos() 
    {
        return this.listaArticulos;
    }
    
    @Override
    public void Agregar(Articulo articulo) 
    {
        try
        {
            String guardar = "insert into articulos values (?, ?, ?)";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.insertar = ConexionOracle.getPreparedStatement(guardar);
            this.insertar.setInt(1, articulo.getId_articulos());
            this.insertar.setString(2, articulo.getNombre());
            this.insertar.setInt(3, articulo.getPrecio());
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
        String eliminarQuery = "delete from articulos where id_locales = ?";
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
        }catch (SQLException | ClassNotFoundException ex) 
        {
            mensaje.desplegarMensaje("ERROR", "Error al eliminar", ex, Alert.AlertType.ERROR);
        }
    }

    @Override
    public void Modificar(int id, Articulo articulo) 
    {
        try
        {
            String modificarQuery = "update articulos set nombre = ?, precio = ? where id_articulos = ?";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.modificar = ConexionOracle.getPreparedStatement(modificarQuery);
            this.modificar.setString(1, this.articulo.getNombre());
            this.modificar.setInt(2, this.articulo.getPrecio());
            this.modificar.setInt(3, id);
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
    public Articulo Buscar(int id) 
    {
        String nombre;
        int precio;
        try
        {
            String consultar = "select nombre, precio from articulos where id_articulos = "+ id +"";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.resultado = ConexionOracle.getResultSet(consultar);
            while(this.resultado.next())
            {
                nombre = this.resultado.getString(1);
                precio = this.resultado.getInt(2);
                this.articulo = new Articulo(id, nombre, precio);
            }
            ConexionOracle.Cerrar(this.estado);
            ConexionOracle.Cerrar(this.resultado);
            ConexionOracle.Cerrar(this.conexion);
        }catch (SQLException | ClassNotFoundException ex) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al buscar", ex, Alert.AlertType.ERROR);
        }
        return this.articulo;
    }

    @Override
    public void ObtenerDatos() 
    {
        try
        {
            String consulta = "select * from articulos order by 1";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.resultado = ConexionOracle.getResultSet(consulta);
            String filas[] = new String[3];
            while(this.resultado.next())
            {
                filas[0] = String.valueOf(this.resultado.getInt(1));
                filas[1] = this.resultado.getString(2);
                filas[2] = String.valueOf(this.resultado.getInt(3));
                this.articulo = new Articulo(Integer.parseInt(filas[0]), filas[1], Integer.parseInt(filas[2]));
                this.listaArticulos.add(articulo);
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
