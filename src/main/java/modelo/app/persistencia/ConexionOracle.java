/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.app.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Alberto
 */
public class ConexionOracle 
{
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException
    {
        Connection conexion = null;
        Class.forName("oracle.jdbc.OracleDriver");
        conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "proyecto", "proyec01");
        return conexion;
    }

    public static Statement getStatement() throws ClassNotFoundException, SQLException
    {
        Statement estado = null;
        estado = ConexionOracle.getConnection().createStatement();
        return estado;
    }
    
    public static PreparedStatement getPreparedStatement(String sql) throws ClassNotFoundException, SQLException
    {
        PreparedStatement estado = ConexionOracle.getConnection().prepareStatement(sql);
        return estado;
    }
    
    public static ResultSet getResultSet(String consulta) throws ClassNotFoundException, SQLException
    {
        ResultSet query = null;
        query = ConexionOracle.getStatement().executeQuery(consulta);
        return query;
    }
    
    public static void Cerrar(Connection conexion) throws SQLException
    {
        conexion.close();
    }
    
    public static void Cerrar(Statement estado) throws SQLException
    {
        estado.close();
    }
    
    public static void Cerrar(PreparedStatement estado) throws SQLException
    {
        estado.close();
    }
    
    public static void Cerrar(ResultSet resultado) throws SQLException
    {
        resultado.close();
    }
    
}
