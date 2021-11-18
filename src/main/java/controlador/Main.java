/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.app.Mensaje;

/**
 * JavaFX Main
 */
public class Main extends Application 
{
    Mensaje mensaje = new Mensaje();
    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException, SQLException
    {
        try 
        {
            //Cargo la vista
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(controlador.Main.class.getResource("/vista/Login.fxml"));
            // Cargo la ventana
            Pane ventana = (Pane) loader.load();
            // Cargo el scene
            Scene scene = new Scene(ventana);
            // Seteo la scene y la muestro
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) 
        {
            mensaje.desplegarMensaje("ERROR", "Error al cargar la vista del Login", e, Alert.AlertType.ERROR);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch();
    }

}