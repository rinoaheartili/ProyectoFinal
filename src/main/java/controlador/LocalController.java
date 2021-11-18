/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modelo.app.Mensaje;
import modelo.app.ValidarCampos;
import modelo.dominio.DAOEntidad.LocalDAO;
import modelo.dominio.DTOEntidad.Local;

/**
 * FXML Controller class
 *
 * @author Alberto
 */
public class LocalController implements Initializable, ValidarCampos 
{
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtMunicipio;
    @FXML
    private Label lblError;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<Local> mostrarDatos;
    @FXML
    private TableColumn<Local, Integer> id;
    @FXML
    private TableColumn<Local, String> nombre;
    @FXML
    private TableColumn<Local, String> direccion;
    @FXML
    private TableColumn<Local, String> municipio;
    private LocalDAO listaLocales;
    private Local local;
    private Mensaje mensaje;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        this.listaLocales = new LocalDAO();
        this.ActualizarTabla();
        this.mensaje = new Mensaje();
    }    
    
    //Metodo para agregar un empleado
    @FXML
    private void agregar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            int id2 = Integer.parseInt(this.txtId.getText());
            String nombre2 = this.txtNombre.getText();
            String direccion2 = this.txtDireccion.getText();
            String municipio2 = this.txtMunicipio.getText();
            this.local = new Local(id2, nombre2, direccion2, municipio2);
            this.mostrarDatos.getItems().add(local);
            this.mostrarDatos.refresh();
            this.listaLocales.Agregar(local);
            this.limpiar();
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    //Metodo para modificar un empleado
    @FXML
    private void modificar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            int id2 = Integer.parseInt(this.txtId.getText());
            String nombre2 = this.txtNombre.getText();
            String direccion2 = this.txtDireccion.getText();
            String municipio2 = this.txtMunicipio.getText();
            this.local = new Local(id2, nombre2, direccion2, municipio2);
            this.mostrarDatos.getItems().set(id2-1, this.local);
            this.mostrarDatos.refresh();
            this.listaLocales.Modificar(id2, this.local);
            this.limpiar();
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    //Metodo para buscar un empleado
    @FXML
    private void buscar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            int id2 = Integer.parseInt(this.txtId.getText());
            this.local = this.listaLocales.Buscar(id2);
            this.mensaje.desplegarBusqueda("Busqueda", "Local encontrado", this.local.toString(), Alert.AlertType.INFORMATION);
            this.limpiar();
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    //Metodo para actualizar los datos de la tabla de los locales
    public void ActualizarTabla()
    {
        this.id.setCellValueFactory(new PropertyValueFactory<>("id_locales"));
        this.nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        this.municipio.setCellValueFactory(new PropertyValueFactory<>("municipio"));
        this.mostrarDatos.getItems().addAll(this.listaLocales.getListaLocal());
    }
    
    //Metodo para cerrar la ventana de locales
    public void cerrarVentana()
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Principal.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());                                                                         
            PrincipalController controller = loader.getController();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) this.btnBuscar.getScene().getWindow();
            myStage.close();
        } catch (IOException e) 
        {
            this.mensaje.desplegarMensaje("ERROR", "Error al cargar la vista Principal", e, Alert.AlertType.ERROR);
        }
    }
    
    //Metodo para limpiar campos
    public void limpiar()
    {
        this.txtId.clear();
        this.txtNombre.clear();
        this.txtDireccion.clear();
        this.txtMunicipio.clear();
    }
    
    //Metodo para validar que no esten vacios los campos
    @Override
    public String ValidarCampos() 
    {
        String datos = "Exitoso";
        if(this.txtId.getText().isEmpty() && this.txtNombre.getText().isEmpty() && this.txtDireccion.getText().isEmpty() && this.txtMunicipio.getText().isEmpty())
        {
            datos = "Error";
        }
        return datos;
    }
    
    /*
    Metodo que solo imprime el error y el color en una etiqueta
    */
    private void setLblError(Color colorante, String empty_credentials) 
    {
        this.lblError.setText(empty_credentials); //texto
        this.lblError.setTextFill(colorante);//color
    }
    
}
