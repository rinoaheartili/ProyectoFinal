/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.DAOEntidad;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import modelo.app.Mensaje;
import modelo.app.persistencia.ConexionOracle;
import modelo.dominio.DAOEntidadInterfaces.IDAOVenta;
import modelo.dominio.DTOEntidad.Venta;


/**
 *
 * @author Alberto
 */
public class VentaDAO implements IDAOVenta
{
    private ArrayList<Venta> listaVenta = null;
    private Venta venta;
    private Mensaje mensaje = new Mensaje();
    private Connection conexion;
    private Statement estado;
    private ResultSet resultado;
    private PreparedStatement insertar, eliminar, modificar;

    public VentaDAO() 
    {
        this.listaVenta = new ArrayList();
        this.venta = null;
        this.ObtenerDatos();
    }

    public ArrayList<Venta> getListaVenta() 
    {
        return listaVenta;
    }

    @Override
    public void Agregar(Venta venta) 
    {
        try
        {
            String guardar = "insert into secuencia values ('?', '?', '?', '?', '?')";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.insertar = ConexionOracle.getPreparedStatement(guardar);
            this.insertar.setInt(1, this.venta.getId_secuencia());
            this.insertar.setString(2, this.venta.getNombre());
            this.insertar.setInt(3, this.venta.getCantidad());
            this.insertar.setDate(4, this.venta.getFecha());
            this.insertar.setInt(5, this.venta.getTotal());
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
        String eliminarQuery = "delete from ventas where id_ventas = ?";
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
    public void Modificar(int id, Venta venta) 
    {
        try
        {
            String modificarQuery = "update secuencia set nombre = ?, cantidad = ?, fecha = ?, precio*cantidad = ? " +
            "from secuencia\n" +
            "inner join ventas\n"+
            "on secuencia.fk_ventas = ventas.id_ventas\n" +
            "inner join articulos\n" +
            "on secuencia.fk_articulos = articulos.id_articulos\n" +
            "where id_articulos = ?";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.modificar = ConexionOracle.getPreparedStatement(modificarQuery);
            this.modificar.setString(1, this.venta.getNombre());
            this.modificar.setInt(2, this.venta.getCantidad());
            this.modificar.setDate(3, this.venta.getFecha());
            this.modificar.setInt(4, this.venta.getTotal());
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
    public Venta Buscar(int id) 
    {
        String nombre;
        int cantidad;
        Date fecha;
        int total;
        try
        {
            String consulta = "select nombre, cantidad, fecha, precio*cantidad\n" +
            "from secuencia\n" +
            "inner join ventas\n"+
            "on secuencia.fk_ventas = ventas.id_ventas\n" +
            "inner join articulos\n" +
            "on secuencia.fk_articulos = articulos.id_articulos\n" +
            "where id_secuencia = " + id +
            "order by 1";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.resultado = ConexionOracle.getResultSet(consulta);
            while(this.resultado.next())
            {
                nombre = this.resultado.getString(1);
                cantidad = this.resultado.getInt(2);
                fecha = this.resultado.getDate(3);
                total = this.resultado.getInt(4);
                this.venta = new Venta(id, nombre, cantidad, fecha, total);
            }
            ConexionOracle.Cerrar(this.estado);
            ConexionOracle.Cerrar(this.resultado);
            ConexionOracle.Cerrar(this.conexion);
        }catch (SQLException | ClassNotFoundException ex) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al buscar", ex, Alert.AlertType.ERROR);
        }
        return this.venta;
    }

    @Override
    public void ObtenerDatos() 
    {
        try
        {
            String consulta = "select id_secuencia, nombre, cantidad, fecha, precio*cantidad\n" +
            "from secuencia\n" +
            "inner join ventas\n"+
            "on secuencia.fk_ventas = ventas.id_ventas\n" +
            "inner join articulos\n" +
            "on secuencia.fk_articulos = articulos.id_articulos\n" +
            "order by 1";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.resultado = ConexionOracle.getResultSet(consulta);
            while(this.resultado.next())
            {
                this.venta = new Venta(this.resultado.getInt(1), this.resultado.getString(2), this.resultado.getInt(3), this.resultado.getDate(4), this.resultado.getInt(5));
                this.listaVenta.add(venta);
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
