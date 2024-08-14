package com.prototype.maruja.controllers;

import java.util.ArrayList;
import com.prototype.maruja.database.DBConnection;
import com.prototype.maruja.models.Producto;
import com.prototype.maruja.utils.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductosController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEmpleados;

    @FXML
    private Button btnFacturas;

    @FXML
    private Button btnProductos;

    @FXML
    private Button btnRegistrar;

    @FXML
    private TableColumn<Producto, String> columnaCiudad;

    @FXML
    private TableColumn<Producto, String> columnaCodigo;

    @FXML
    private TableColumn<Producto, String> columnaNombre;

    @FXML
    private TableColumn<Producto, Double> columnaPrecio;

    @FXML
    private TableColumn<Producto, Integer> columnaStock;

    @FXML
    private Label lblCiudad;

    @FXML
    private Label lblCodigo;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblPrecio;

    @FXML
    private Label lblStock;

    @FXML
    private TableView<Producto> tablaProducto;

    @FXML
    private TextField txtBuscar;

    @FXML
    private ComboBox<String> txtCiudad;
    private String[] items = {"Quito", "Guayaquil"};

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    List<String> errors = new ArrayList<>();

    @FXML
    public void initialize(){
        txtCiudad.getItems().setAll(items);
        mostrarListaDatosProducto();
        buscarProducto();
        seleccionarProducto();
    }

    @FXML
    void abrirClientes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/prototype/maruja/scenes/clientes.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) btnClientes.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        }catch (IOException e){
            System.out.println("Error en abrir Clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void abrirEmpleados(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/prototype/maruja/scenes/empleados.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) btnEmpleados.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        }catch (IOException e){
            System.out.println("Error en abrir Empleados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void abrirFacturas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/prototype/maruja/scenes/facturas.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) btnFacturas.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        }catch (IOException e){
            System.out.println("Error en abrir Facturas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void abrirProductos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/prototype/maruja/scenes/productos.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) btnProductos.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        }catch (IOException e){
            System.out.println("Error en abrir Productos: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    void actualizar(ActionEvent event) {
        List<String> errors = validarEntradas();
        String actualizarProducto = "UPDATE Producto SET Precio_producto = ?, Stock_producto = ? WHERE ID_Producto = ?";

        Connection conexion = DBConnection.getConnection();
        try {
            Alert alert;
            if(!errors.isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Llene los campos correctamente");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("MENSAJE DE CONFIRMACION");
                alert.setHeaderText(null);
                alert.setContentText("¿Está seguro de actualizar los datos del producto?");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    // Actualizar en la tabla Producto
                    PreparedStatement prepararProducto = conexion.prepareStatement(actualizarProducto);
                    prepararProducto.setDouble(1, Double.parseDouble(txtPrecio.getText()));
                    prepararProducto.setInt(2, Integer.parseInt(txtStock.getText()));
                    prepararProducto.setString(3, txtCodigo.getText());
                    prepararProducto.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("¡Actualización de producto exitosa!");
                    alert.showAndWait();
                    mostrarListaDatosProducto();
                    resetearDatos();
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No se actualizó el producto");
                    alert.showAndWait();
                }
            }
        }catch (Exception e){
            System.out.println("Error en actualizar producto: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    void cancelar(ActionEvent event) {
        resetearDatos();
    }

    @FXML
    void eliminar(ActionEvent event) {
        String eliminar = "DELETE FROM Producto WHERE ID_Producto = ?";
        Connection conexion = DBConnection.getConnection();
        try {
            Alert alert;
            if(txtCodigo.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Seleccione un producto para eliminar.");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje de Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("¿Está seguro de eliminar este producto?");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    PreparedStatement preparar = conexion.prepareStatement(eliminar);
                    preparar.setString(1, txtCodigo.getText());
                    preparar.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("¡Producto eliminado exitosamente!");
                    alert.showAndWait();
                    mostrarListaDatosProducto();
                    resetearDatos();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en eliminar producto: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    void registrar(ActionEvent event) {
        List<String> errors = validarEntradas();
        if (!errors.isEmpty()) {
            // Mostrar mensajes de error y evitar que el registro proceda
            return;
        }

        String registrarProducto = "INSERT INTO Producto (ID_Producto, Nombre_producto, Precio_producto, Stock_producto) VALUES (?, ?, ?, ?)";

        Connection conexion = DBConnection.getConnection();
        try {
            // Insertar en la tabla Producto
            PreparedStatement prepararProducto = conexion.prepareStatement(registrarProducto);
            prepararProducto.setString(1, txtCodigo.getText());
            prepararProducto.setString(2, txtNombre.getText());
            prepararProducto.setDouble(3, Double.parseDouble(txtPrecio.getText()));
            prepararProducto.setInt(4, Integer.parseInt(txtStock.getText()));
            prepararProducto.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("¡Registro de producto exitoso!");
            alert.showAndWait();

            mostrarListaDatosProducto();
            resetearDatos();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private ObservableList<Producto> anadirListaProducto;
    public void buscarProducto(){
        FilteredList<Producto> filtro = new FilteredList<>(anadirListaProducto, e -> true);
        txtBuscar.textProperty().addListener((Observable, oldValue, newValue) -> {
            filtro.setPredicate(predicadoProducto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String palabraClave = newValue.toLowerCase();
                if (predicadoProducto.getCodigo().toLowerCase().contains(palabraClave)){
                    return true;
                } else if (predicadoProducto.getNombre().toLowerCase().contains(palabraClave)){
                    return true;
                }else if (predicadoProducto.getCiudad().toLowerCase().contains(palabraClave)){
                    return true;
                }else{
                    return false;
                }
            });
        });
        SortedList<Producto> ordenarLista = new SortedList<>(filtro);
        ordenarLista.comparatorProperty().bind(tablaProducto.comparatorProperty());
        tablaProducto.setItems(ordenarLista);
    }

    public ObservableList<Producto> anadirDatosListaProducto() {
        ObservableList<Producto> listaDatos = FXCollections.observableArrayList();
        String sql = "SELECT p.ID_Producto, p.Nombre_producto, p.Precio_producto, p.Stock_producto " +
                "FROM Producto p";
        Connection conexion = DBConnection.getConnection();
        try {
            PreparedStatement preparar = conexion.prepareStatement(sql);
            ResultSet resultado = preparar.executeQuery();
            Producto producto;
            while (resultado.next()) {
                producto = new Producto(
                        resultado.getString("ID_Producto"),
                        resultado.getString("Nombre_producto"),
                        resultado.getDouble("Precio_producto"),
                        resultado.getInt("Stock_producto"), // Aquí obtenemos el valor correcto de stock
                        "");  // Ajusta o elimina según necesites la ciudad
                listaDatos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDatos;
    }


    public void mostrarListaDatosProducto(){
        anadirListaProducto = anadirDatosListaProducto();
        columnaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaStock.setCellValueFactory(new PropertyValueFactory<>("stock")); // Esta línea es importante
        columnaCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        tablaProducto.setItems(anadirListaProducto);
    }


    public void seleccionarProducto() {
        tablaProducto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtCodigo.setText(newValue.getCodigo());
                txtNombre.setText(newValue.getNombre());
                txtPrecio.setText(String.valueOf(newValue.getPrecio()));
                txtStock.setText(String.valueOf(newValue.getStock()));
                txtCiudad.setValue(newValue.getCiudad());
                tablaProducto.refresh();
            }
        });
    }

    public void resetearDatos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtStock.clear();
        txtCiudad.getSelectionModel().clearSelection();
    }

    private List<String> validarEntradas() {
        List<String> errors = new ArrayList<>();

        // Verificar que el código no esté vacío
        if (txtCodigo.getText().isEmpty() || !txtCodigo.getText().matches("\\d+")) {
            lblCodigo.setVisible(true);
            lblCodigo.setText("Código no válido");
            errors.add("Código no válido");
        } else {
            lblCodigo.setVisible(false);
        }

        // Validaciones adicionales...
        Validation.validarNulos(txtNombre, lblNombre, errors);
        Validation.validarPrecio(txtPrecio, lblPrecio, errors);
        Validation.validarStock(txtStock, lblStock, errors);
        Validation.validarComboBox(txtCiudad, lblCiudad, errors);

        return errors;
    }

    private boolean codigoDuplicado(String codigo) {
        String query = "SELECT COUNT(*) FROM Producto WHERE ID_Producto = ?";
        Connection conexion = DBConnection.getConnection();
        try {
            PreparedStatement preparar = conexion.prepareStatement(query);
            preparar.setString(1, codigo);
            ResultSet resultado = preparar.executeQuery();
            if (resultado.next()) {
                return resultado.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
