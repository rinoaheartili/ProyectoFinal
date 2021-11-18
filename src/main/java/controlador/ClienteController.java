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
import modelo.dominio.DAOEntidad.ClienteDAO;
import modelo.dominio.DTOEntidad.Cliente;

/**
 * FXML Controller class
 *
 * @author Alberto
 */
public class ClienteController implements Initializable, ValidarCampos  
{
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCalle;
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtTelefono;
    @FXML
    private Label lblError;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<Cliente> mostrarDatos;
    @FXML
    private TableColumn<Cliente, Integer> id;
    @FXML
    private TableColumn<Cliente, String> nombre;
    @FXML
    private TableColumn<Cliente, String> calle;
    @FXML
    private TableColumn<Cliente, Integer> numero;
    @FXML
    private TableColumn<Cliente, String> telefono;
    private ClienteDAO listaClientes;
    private Cliente cliente;
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
        this.listaClientes = new ClienteDAO(); 
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
            String calle2 = this.txtCalle.getText();
            int numero2 = Integer.parseInt(this.txtNumero.getText());
            String telefono2 = this.txtTelefono.getText();
            this.cliente = new Cliente(id2, nombre2, calle2, numero2, telefono2);
            this.mostrarDatos.getItems().add(cliente);
            this.mostrarDatos.refresh();
            this.listaClientes.Agregar(cliente);
            this.limpiar();
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    //Metodo para modificar un cliente
    @FXML
    private void modificar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            int id2 = Integer.parseInt(this.txtId.getText());
            String nombre2 = this.txtNombre.getText();
            String calle2 = this.txtCalle.getText();
            int numero2 = Integer.parseInt(this.txtNumero.getText());
            String telefonos = this.txtTelefono.getText();
            this.cliente = new Cliente(id2, nombre2, calle2, numero2, telefonos);
            this.mostrarDatos.getItems().set(id2-1, this.cliente);
            this.mostrarDatos.refresh();
            this.listaClientes.Modificar(id2, this.cliente);
            this.limpiar();
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    //Metodo para buscar un cliente
    @FXML
    private void buscar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            int id2 = Integer.parseInt(this.txtId.getText());
            this.cliente = this.listaClientes.Buscar(id2);
            this.mensaje.desplegarBusqueda("Busqueda", "Cliente encontrado", this.cliente.toString(), Alert.AlertType.INFORMATION);
            this.limpiar();
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    //Metodo para actualizar los datos de la tabla de los clientes
    public void ActualizarTabla()
    {
        this.id.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        this.nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.calle.setCellValueFactory(new PropertyValueFactory<>("calle"));
        this.numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        this.telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        this.mostrarDatos.getItems().addAll(this.listaClientes.getListaCliente());
        this.limpiar();
    }
    
    //Metodo para cerrar la ventana de clientes
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
        this.txtCalle.clear();
        this.txtNumero.clear();
        this.txtTelefono.clear();
    }
    
    //Metodo para validar que no esten vacios los campos
    @Override
    public String ValidarCampos() 
    {
        String datos = "Exitoso";
        if(this.txtId.getText().isEmpty() && this.txtNombre.getText().isEmpty() && this.txtCalle.getText().isEmpty() && this.txtNumero.getText().isEmpty() && this.txtTelefono.getText().isEmpty())
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
