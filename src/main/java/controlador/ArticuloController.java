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
import modelo.app.ValidarCampos;
import modelo.app.Mensaje;
import modelo.app.persistencia.ConexionOracle;
import modelo.dominio.DAOEntidad.ArticuloDAO;
import modelo.dominio.DTOEntidad.Articulo;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Alberto
 */
public class ArticuloController implements Initializable, ValidarCampos
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
    private TextField txtPrecio;
    @FXML
    private TableView<Articulo> mostrarDatos;
    @FXML
    private TableColumn<Articulo, Integer> id;
    @FXML
    private TableColumn<Articulo, String> nombre;
    @FXML
    private TableColumn<Articulo, Integer> precio;
    private ArticuloDAO listaArticulos;
    private Articulo articulo;
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
        // TODO
        this.listaArticulos = new ArticuloDAO();
        this.ActualizarTabla();
        this.mensaje = new Mensaje();
    }    

    @FXML
    private void agregar(ActionEvent event) 
    {
        if(ValidarCampos().equals("Exitoso"))
        {
            int id2 = Integer.parseInt(this.txtId.getText());
            String nombre2 = this.txtNombre.getText();
            int precio2 = Integer.parseInt(this.txtPrecio.getText());
            this.articulo = new Articulo(id2, nombre2, precio2);
            this.mostrarDatos.getItems().add(this.articulo);
            this.mostrarDatos.refresh();
            this.listaArticulos.Agregar(this.articulo);
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
            int id2 = Integer.parseInt(this.txtId.getText());
            String nombre2 = this.txtNombre.getText();
            int precio2 = Integer.parseInt(this.txtPrecio.getText());
            this.articulo = new Articulo(id2, nombre2, precio2);
            this.mostrarDatos.getItems().set(id2-1, this.articulo);
            this.mostrarDatos.refresh();
            this.listaArticulos.Modificar(id2, this.articulo);
            this.limpiar();
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
            this.articulo = this.listaArticulos.Buscar(id2);
            this.mensaje.desplegarBusqueda("Busqueda", "Articulo encontrado", this.articulo.toString(), Alert.AlertType.INFORMATION);
            this.limpiar();
        }else
        {
            this.setLblError(Color.RED, "Ingrese los campos requeridos");
        }
    }
    
    //Metodo para actualizar los datos de la tabla de los locales
    public void ActualizarTabla()
    {
        this.id.setCellValueFactory(new PropertyValueFactory<>("id_articulos"));
        this.nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        this.mostrarDatos.getItems().addAll(this.listaArticulos.getListaArticulos());
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
        this.txtPrecio.clear();
    }
    
    //Metodo para validar que no esten vacios los campos
    @Override
    public String ValidarCampos() 
    {
        String datos = "Exitoso";
        if(this.txtId.getText().isEmpty() && this.txtNombre.getText().isEmpty() && this.txtPrecio.getText().isEmpty())
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
            String rutaReporte = "src/main/resources/reportes/reporteArticulos.jasper";
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
