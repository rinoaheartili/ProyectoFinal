/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.autenticacion;

import controlador.PrincipalController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modelo.app.persistencia.ConexionOracle;
import modelo.dominio.DTOEntidad.Empleado;
import modelo.app.Mensaje;


/**
 * FXML Controller class
 *
 * @author Alberto
 */
public class LoginController implements Initializable 
{
    @FXML
    private TextField lblUsuario;
    @FXML
    private PasswordField lblContraseña;
    @FXML
    private Label lblError;
    @FXML
    private String nombre2;
    private Button btnIngresar;
    private Connection conexion;
    private Statement estado;
    private ResultSet resultado;
    private final Mensaje mensaje = new Mensaje();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    

    /**
    * Metodo que comprueba que el usuario este dado de alta y si no muestra mensaje de error
    * Ademas si es correcto te manda a la ventana de principal
    * Returns an Image object that can then be painted on the screen. 
    * @param event acciona el evento ingresar
    */
    @FXML
    private void ingresar(ActionEvent event) throws ClassNotFoundException, SQLException
    {
        if (logIn().equals("Success")) 
        {
            //Success --> Exito
            try 
            {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Principal.fxml"));
                Scene scene = new Scene(loader.load());                                                                         
                PrincipalController controller = loader.getController();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) 
            {
                this.mensaje.desplegarMensaje("ERROR", "Error al cargar la vista principal", ex, Alert.AlertType.ERROR);
            }
        }
    }
    
    /**
    * Funcion que si un usuario ingreso o no sus credenciales
    * ademas que comprueba si es valido o no
    * @return      Una cadena que si fue success o error el login
    * @see         String
    */
    private String logIn() throws ClassNotFoundException, SQLException 
    {
        String status = "Success";//Bandera
        String username = lblUsuario.getText();//usuario 
        String password = lblContraseña.getText();//contraseña
        if(username.isEmpty() || password.isEmpty()) 
        {
            setLblError(Color.RED, "Credenciales vacias");
            status = "Error";//Bandera
        }else 
        {
            Empleado empleados = obtenerDatosUsuario(username);
            if((empleados)!=null && (username.equals(empleados.getNombre())&& password.equals(empleados.getContrasena())))
            {
                //comparar
                this.nombre2=(empleados.getNombre().toUpperCase());                
            }else 
            {
                setLblError(Color.RED, "Ingrese el usuario / contraseña correctos");
                status = "Error";   
            }            
        }
        return status;
    }
    
    /**
    * Este metodo lo que me hace es buscar un elemento dentro de mi listado
    * en la base de datos de empleados para que sea comparado con los del login
    *
    * @param  usuario  ingresa el usuario para su busqueda en la base de datos
    * @return          retorna los datos de un empleado
    * @see         Empleado
    */
    private Empleado obtenerDatosUsuario(String usuario) throws ClassNotFoundException, SQLException 
    {
        //buscarPerfil
        ArrayList<Empleado> listaEmpleados = new ArrayList<>();
        Empleado empleado;
        Empleado empleadoAxuliar = null;
        String Consulta = "select * from empleados";
        conexion = ConexionOracle.getConnection();
        estado = ConexionOracle.getStatement();
        resultado = ConexionOracle.getResultSet(Consulta);
        while(resultado.next())
        {
            int id_empleado = Integer.parseInt(resultado.getString(1));
            String nombre = resultado.getString(2);
            String apellido_p = resultado.getString(3);
            String contrasena = resultado.getString(4);
            String correo = resultado.getString(5);
            empleado = new Empleado(id_empleado, nombre, apellido_p, contrasena, correo);
            listaEmpleados.add(empleado);
        }
        for(Empleado empleadoAux : listaEmpleados) 
        {
            if (empleadoAux.getNombre().equals(usuario)) 
            {
                empleadoAxuliar = empleadoAux;
                break;
            }
        }
        ConexionOracle.Cerrar(estado);
        ConexionOracle.Cerrar(resultado);
        ConexionOracle.Cerrar(conexion);
        return empleadoAxuliar; 
    }
    
    /**
    * Metodo para poder salir de la aplicacion
    *
    * @param  event  acciona el evento de pulsar el boton salir
    * @see void
    */
    @FXML
    private void salir(ActionEvent event) 
    {
        Platform.exit();
    }
    
    /**
    * Metodo que solo imprime el error y el color en una etiqueta 
    *
    * @param  colorante         pasa el color de la etiqueta para ser pintado
    * @param  empty_credentials imprime el error en la etiqueta del label
    * @see         void
    */
    private void setLblError(Color colorante, String empty_credentials) 
    {
        this.lblError.setText(empty_credentials); //texto
        this.lblError.setTextFill(colorante);//color
    }
    
}
