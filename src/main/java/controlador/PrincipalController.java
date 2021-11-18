/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import modelo.app.Mensaje;

/**
 * FXML Controller class
 *
 * @author Alberto
 */
public class PrincipalController implements Initializable 
{
    @FXML
    private Button btnEmpleados;
    @FXML
    private Button btnProductos;
    @FXML
    private Button btnSalidas;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnLocales;
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

    @FXML
    private void Salir(ActionEvent event) 
    {
        Platform.exit();
    }


    @FXML
    private void productos(ActionEvent event) 
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Articulo.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());                                                                         
            ArticuloController controller = loader.getController();
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> controller.cerrarVentana());
            Stage myStage = (Stage) this.btnProductos.getScene().getWindow();
            myStage.close();
        } catch (IOException e) 
        {
            System.out.println("" + e.getMessage());
            this.mensaje.desplegarMensaje("ERROR", "Error al cargar la vista de Articulos", e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void salidas(ActionEvent event) 
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Venta.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());                                                                         
            VentaController controller = loader.getController();
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> controller.cerrarVentana());
            Stage myStage = (Stage) this.btnSalidas.getScene().getWindow();
            myStage.close();
        } catch (IOException e) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al cargar la vista de Ventas", e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void empleados(ActionEvent event)
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Empleado.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());                                                                         
            EmpleadoController controller = loader.getController();
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> controller.cerrarVentana());
            Stage myStage = (Stage) this.btnEmpleados.getScene().getWindow();
            myStage.close();
        } catch (IOException e) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al cargar la vista de Empleados", e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clientes(ActionEvent event) 
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Cliente.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());                                                                         
            ClienteController controller = loader.getController();
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> controller.cerrarVentana());
            Stage myStage = (Stage) this.btnEmpleados.getScene().getWindow();
            myStage.close();
        } catch (IOException e) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al cargar la vista de Clientes", e, Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void locales(ActionEvent event) 
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Local.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());                                                                         
            LocalController controller = loader.getController();
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> controller.cerrarVentana());
            Stage myStage = (Stage) this.btnEmpleados.getScene().getWindow();
            myStage.close();
        } catch (IOException e) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al cargar la vista de Locales", e, Alert.AlertType.ERROR);
        }
    }
    
}
