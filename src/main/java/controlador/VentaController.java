/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import modelo.dominio.DAOEntidad.VentaDAO;
import modelo.dominio.DTOEntidad.Venta;

/**
 * FXML Controller class
 *
 * @author Alberto
 */
public class VentaController implements Initializable, ValidarCampos
{
    @FXML
    private Button btnBuscar;
    @FXML
    private Label lblError;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCantidad;
    @FXML
    private TextField txtFecha;
    @FXML
    private TextField txtPrecioTotal;
    @FXML
    private TableView<Venta> mostrarDatos;
    @FXML
    private TableColumn<Venta, Integer> id;
    @FXML
    private TableColumn<Venta, String> nombre;
    @FXML
    private TableColumn<Venta, Integer> cantidad;
    @FXML
    private TableColumn<Venta, Date> fecha;
    @FXML
    private TableColumn<Venta, Integer> total;
    private VentaDAO listaVentas;
    private Venta venta;
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
        this.listaVentas = new VentaDAO();
        this.ActualizarTabla();
        this.mensaje = new Mensaje();
    }    

    @FXML
    private void agregar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }

    @FXML
    private void eliminar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            int id2 = Integer.parseInt(this.txtId.getText());
            String nombre2 = this.txtNombre.getText();
            int cantidad2 = Integer.parseInt(this.txtCantidad.getText());
            Date fecha2 = Date.valueOf(this.txtFecha.getText());
            int total2 = Integer.parseInt(this.txtPrecioTotal.getText());
            this.venta = new Venta(id2, nombre2, cantidad2, fecha2, total2);
            this.mostrarDatos.getItems().set(id2-1, this.venta);
            this.mostrarDatos.refresh();
            this.listaVentas.Modificar(id2, this.venta);
            this.limpiar();
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    @FXML
    private void modificar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    @FXML
    private void buscar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            int id2 = Integer.parseInt(this.txtId.getText());
            this.venta = this.listaVentas.Buscar(id2);
            this.mensaje.desplegarBusqueda("Busqueda", "Venta", this.venta.toString(), Alert.AlertType.INFORMATION);
            this.limpiar();
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    //Metodo para actualizar los datos de la tabla de los locales
    public void ActualizarTabla()
    {
        this.id.setCellValueFactory(new PropertyValueFactory<>("id_secuencia"));
        this.nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        this.fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        this.total.setCellValueFactory(new PropertyValueFactory<>("total"));
        this.mostrarDatos.getItems().addAll(this.listaVentas.getListaVenta());
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
        this.txtCantidad.clear();
        this.txtFecha.clear();
        this.txtPrecioTotal.clear();
    }
    
    @Override
    public String ValidarCampos() 
    {
        String datos = "Exitoso";
        if(this.txtId.getText().isEmpty() && this.txtNombre.getText().isEmpty() && this.txtCantidad.getText().isEmpty() && this.txtFecha.getText().isEmpty() && this.txtPrecioTotal.getText().isEmpty())
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
