/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import modelo.app.persistencia.ConexionOracle;
import modelo.dominio.DTOEntidad.Empleado;
import modelo.dominio.DAOEntidad.EmpleadoDAO;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Alberto
 */
public class EmpleadoController implements Initializable, ValidarCampos
{
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApePaterno;
    @FXML
    private TextField txtContrasena;
    @FXML
    private TextField txtCorreo;
    @FXML
    private Label lblError;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<Empleado> mostrarDatos;
    @FXML
    private TableColumn<Empleado, Integer> id;
    @FXML
    private TableColumn<Empleado, String> nombre;
    @FXML
    private TableColumn<Empleado, String> apellidoP;
    @FXML
    private TableColumn<Empleado, String> contrasena;
    @FXML
    private TableColumn<Empleado, String> correo;
    private EmpleadoDAO listaEmpleado;
    private Empleado empleado;
    private Mensaje mensaje;
    private Connection conexion;
    private Statement estado;
    private ResultSet resultado;
    private PreparedStatement insertar, eliminar, modificar;
    @FXML
    private Button btnReporte;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.listaEmpleado = new EmpleadoDAO();
        this.ActualizarTabla();
        this.mensaje = new Mensaje();
    }
    
    //Metodo para agregar un empleado
    @FXML
    private void btnAgregar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            int id2 = Integer.parseInt(this.txtId.getText());
            String nombre2 = this.txtNombre.getText();
            String apellidoP2 = this.txtApePaterno.getText();
            String contrasena2 = this.txtContrasena.getText();
            String correo2 = this.txtCorreo.getText();
            empleado = new Empleado(id2, nombre2, apellidoP2, contrasena2, correo2);
            this.mostrarDatos.getItems().add(empleado);
            this.mostrarDatos.refresh();
            this.listaEmpleado.Agregar(empleado);
            this.limpiar();
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    //Metodo para eliminar un empleado
    @FXML
    private void btnEliminar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            int id2 = Integer.parseInt(this.txtId.getText());
            this.listaEmpleado.Eliminar(id2);
            this.mostrarDatos.getItems().remove(id2 - 1);
            this.mostrarDatos.refresh();
            this.limpiar();
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    //Metodo para modificar un empleado
    @FXML
    private void btnModificar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            int id2 = Integer.parseInt(this.txtId.getText());
            String nombre2 = this.txtNombre.getText();
            String apellidoP2 = this.txtApePaterno.getText();
            String contrasena2 = this.txtContrasena.getText();
            String correo2 = this.txtCorreo.getText();
            this.empleado = new Empleado(id2, nombre2, apellidoP2, contrasena2, correo2);
            this.mostrarDatos.getItems().set(id2-1, this.empleado);
            this.mostrarDatos.refresh();
            this.listaEmpleado.Modificar(id2, empleado);
            this.limpiar();
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    //Metodo para buscar un empleado
    @FXML
    private void btnBuscar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            int id2 = Integer.parseInt(this.txtId.getText());
            this.empleado = this.listaEmpleado.Buscar(id2);
            this.mensaje.desplegarBusqueda("Busqueda", "Empleado encontrado", this.empleado.toString(), Alert.AlertType.INFORMATION);
            this.limpiar();
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    //Metodo para actualizar los datos de la tabla de los empleados
    public void ActualizarTabla()
    {
        this.id.setCellValueFactory(new PropertyValueFactory<>("id_empleado"));
        this.nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.apellidoP.setCellValueFactory(new PropertyValueFactory<>("apellido_p"));
        this.contrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        this.correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        this.mostrarDatos.getItems().addAll(this.listaEmpleado.getListaEmpleado());
    }
    
    //Metodo para cerrar la ventana de empleados
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
        this.txtApePaterno.clear();
        this.txtContrasena.clear();
        this.txtCorreo.clear();
    }
    
    //Metodo para validar que no esten vacios los campos
    @Override
    public String ValidarCampos() 
    {
        String datos = "Exitoso";
        if(this.txtId.getText().isEmpty() && this.txtNombre.getText().isEmpty() && this.txtApePaterno.getText().isEmpty()&& this.txtContrasena.getText().isEmpty() && this.txtCorreo.getText().isEmpty())
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


    @FXML
    private void mostrarReporte(ActionEvent event) throws ClassNotFoundException, SQLException 
    {
        this.conexion = ConexionOracle.getConnection();
        this.estado = ConexionOracle.getStatement();
        try
        {
            String rutaReporte = "src/main/resources/reportes/reporteEmpleados.jasper";
            JasperPrint rptLibrosPDF = JasperFillManager.fillReport(rutaReporte, null, this.conexion);
            JasperViewer ventanaVisor = new JasperViewer(rptLibrosPDF, false);
            ventanaVisor.setTitle("Informe de Articulos Sistema Cocina Delfin");
            ventanaVisor.setVisible(true);
        }
        catch(Exception e)
        {
            this.mensaje.desplegarMensaje("ERROR", "Error en BD en informe. Verifica\n\n", e, Alert.AlertType.ERROR);
        }
    }
}
