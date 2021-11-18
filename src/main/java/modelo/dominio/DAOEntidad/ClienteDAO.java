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
import modelo.dominio.DAOEntidadInterfaces.IDAOCliente;
import modelo.dominio.DTOEntidad.Cliente;

/**
 *
 * @author Alberto
 */
public class ClienteDAO implements IDAOCliente
{
    private ArrayList<Cliente> listaCliente = null;
    private Cliente cliente;
    private Mensaje mensaje = new Mensaje();
    private Connection conexion;
    private Statement estado;
    private ResultSet resultado;
    private PreparedStatement insertar, eliminar, modificar;

    public ClienteDAO() 
    {
        this.listaCliente = new ArrayList();
        this.cliente = null;
        this.ObtenerDatos();
    }

    public ArrayList<Cliente> getListaCliente() 
    {
        return listaCliente;
    }
    
    @Override
    public void Agregar(Cliente cliente)
    {
        try
        {
            String guardar = "insert into clientes values (?, ?, ?, ?, ?)";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.insertar = ConexionOracle.getPreparedStatement(guardar);
            this.insertar.setInt(1, cliente.getIdCliente());
            this.insertar.setString(2, cliente.getNombre());
            this.insertar.setString(3, cliente.getCalle());
            this.insertar.setInt(4, cliente.getNumero());
            this.insertar.setString(5, cliente.getTelefono());
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
            String eliminarQuery = "delete from clientes where id_clientes = ?";
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
            System.out.println("" + ex);
            //this.mensaje.desplegarMensaje("ERROR", "Error al eliminar", ex, Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public void Modificar(int id, Cliente cliente)
    {
        try
        {
            String modificarQuery = "update clientes set nombre = ?, calle = ?, numero = ?, telefono = ? where id_clientes = ?";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.modificar = ConexionOracle.getPreparedStatement(modificarQuery);
            this.modificar.setString(1, cliente.getNombre());
            this.modificar.setString(2, cliente.getCalle());
            this.modificar.setInt(3, cliente.getNumero());
            this.modificar.setString(4, cliente.getTelefono());
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
    public Cliente Buscar(int id)
    {
        String nombre;
        String calle;
        int numero;
        String telefono;
        try
        {
            String consultar = "select nombre, calle, numero, telefono from clientes where id_clientes = "+ id +"";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.resultado = ConexionOracle.getResultSet(consultar);
            while(this.resultado.next())
            {
                nombre = this.resultado.getString(1);
                calle = this.resultado.getString(2);
                numero = Integer.parseInt(this.resultado.getString(3));
                telefono = this.resultado.getString(4);
                this.cliente = new Cliente(id, nombre, calle, numero, telefono);
            }
            ConexionOracle.Cerrar(this.estado);
            ConexionOracle.Cerrar(this.resultado);
            ConexionOracle.Cerrar(this.conexion);
        }catch (SQLException | ClassNotFoundException ex) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al buscar", ex, Alert.AlertType.ERROR);
        }
        return this.cliente;
    }
    
    @Override
    public void ObtenerDatos()
    {
        try
        {
            String consulta = "select * from clientes order by 1";
            this.conexion = ConexionOracle.getConnection();
            this.estado = ConexionOracle.getStatement();
            this.resultado = ConexionOracle.getResultSet(consulta);
            String filas[] = new String[5];
            while(this.resultado.next())
            {
                filas[0] = String.valueOf(this.resultado.getInt(1));
                filas[1] = this.resultado.getString(2);
                filas[2] = this.resultado.getString(3);
                filas[3] = String.valueOf(this.resultado.getInt(4));
                filas[4] = this.resultado.getString(5);
                this.cliente = new Cliente(Integer.parseInt(filas[0]), filas[1], filas[2], Integer.parseInt(filas[3]), filas[4]);
                this.listaCliente.add(cliente);
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
