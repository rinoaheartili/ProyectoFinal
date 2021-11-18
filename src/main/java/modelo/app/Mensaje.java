/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.app;

import javafx.scene.control.Alert;

/**
 *
 * @author Alberto
 */
public class Mensaje 
{
    /*
    Metodo general para mandar mensajes informativos o de error
    */
    public void desplegarMensaje(String titulo, String cabecera, Exception informacion, Alert.AlertType tipoAlerta)
    {
        Alert alerta = new Alert(tipoAlerta); // Alerta de error            
        alerta.setTitle(titulo); //Titulo
        alerta.setHeaderText(cabecera);//Cabecera                
        alerta.setContentText(informacion.getMessage());//Información
        alerta.showAndWait();
    }
    
    public void desplegarBusqueda(String titulo, String cabecera, Object busqueda, Alert.AlertType tipoAlerta)
    {
        Alert alerta = new Alert(tipoAlerta); // Alerta de error            
        alerta.setTitle(titulo); //Titulo
        alerta.setHeaderText(cabecera);//Cabecera                
        alerta.setContentText(busqueda.toString());//Información
        alerta.showAndWait();
    }
    
}
