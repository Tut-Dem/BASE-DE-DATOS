package com.prototype.maruja.controllers;

import com.prototype.maruja.database.DBConnection;
import com.prototype.maruja.models.Cliente;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;


public class ClientesController {

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
    private TableColumn<Cliente, String> columnaApellidos;

    @FXML
    private TableColumn<Cliente, String> columnaCedula;

    @FXML
    private TableColumn<Cliente, String> columnaDireccion;

    @FXML
    private TableColumn<Cliente, String> columnaEmail;

    @FXML
    private TableColumn<Cliente, String> columnaNombres;

    @FXML
    private TableColumn<Cliente, String> columnaTelefono;

    @FXML
    private TableColumn<Cliente, String> columnaCiudad;

    @FXML
    private Label lblApellidos;

    @FXML
    private Label lblCedula;

    @FXML
    private Label lblDireccion;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblNombres;

    @FXML
    private Label lblTelefono;

    @FXML
    private Label lblCiudad;

    @FXML
    private TableView<Cliente> tablaCliente;

    @FXML
    private ComboBox<String> txtCiudad;
    private String[] items = {"Quito", "Guayaquil"};

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtTelefono;

    List<String> errors = new ArrayList<>();

    public ObservableList<Cliente> clienteObservableList;

    @FXML
    public void initialize() {
        txtCiudad.getItems().setAll(items);
        txtCedula.setOnAction(e -> {
            Validation.validarExistencia(txtCedula, lblCedula, errors, "cliente");
            txtNombres.requestFocus();
        });
        txtNombres.setOnAction(e -> {
            Validation.validarDosPalabras(txtNombres, lblNombres, errors);
            txtApellidos.requestFocus();
        });
        txtApellidos.setOnAction(e -> {
            Validation.validarDosPalabras(txtApellidos, lblApellidos, errors);
            txtEmail.requestFocus();
        });
        txtEmail.setOnAction(e -> {
            Validation.validarEmail(txtEmail, lblEmail, errors);
            txtDireccion.requestFocus();
        });
        txtDireccion.setOnAction(e -> {
            Validation.validarNulos(txtDireccion, lblDireccion, errors);
            txtTelefono.requestFocus();
        });
        txtTelefono.setOnAction(e -> {
            txtCiudad.requestFocus();
            Validation.validarTelefono(txtTelefono, lblTelefono, errors);
        });
        txtCiudad.setOnAction(e -> {
            Validation.validarComboBox(txtCiudad, lblCiudad, errors);
        });
        // Cargar datos en la tabla
        mostrarListaDatosCliente();
        // Inicializar la búsqueda de cliente
        buscarCliente();
        // Configurar la selección de cliente
        seleccionarCliente();
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
        } catch (IOException e) {
            System.out.println("Error in Log out : " + e.getMessage());
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
        } catch (IOException e) {
            System.out.println("Error in Log out : " + e.getMessage());
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
        } catch (IOException e) {
            System.out.println("Error in Log out : " + e.getMessage());
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
        } catch (IOException e) {
            System.out.println("Error in Log out : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void actualizar(ActionEvent event) {
        Connection conexion = DBConnection.getConnection();
        try {
            Alert alert;
            if (txtCedula.getText().isEmpty() || txtNombres.getText().isEmpty() || txtApellidos.getText().isEmpty() ||
                    txtEmail.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Llena todos los espacios");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje de confirmacion");
                alert.setHeaderText(null);
                alert.setContentText("Estas seguro de actualizar los datos del cliente con cedula: " + txtCedula.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    String actualizarDatos = "SET XACT_ABORT ON; BEGIN DISTRIBUTED TRAN; UPDATE v_cliente SET Direccion_cli = ?, Telefono_cli = ?, Email_cli = ? WHERE ID_Cliente = ?; COMMIT TRANSACTION";
                    PreparedStatement prep = conexion.prepareStatement(actualizarDatos);
                    prep.setString(4, txtCedula.getText());
                    prep.setString(1, txtDireccion.getText());
                    prep.setString(2, txtTelefono.getText());
                    prep.setString(3, txtEmail.getText());
                    prep.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacion");
                    alert.setHeaderText(null);
                    alert.setContentText("Actualizado exitosamente!");
                    alert.showAndWait();
                    mostrarListaDatosCliente();
                    resetearDatos();
                }
            }
        } catch (Exception e) {
            System.out.println("Error en actualizar cliente : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        resetearDatos();
    }

    @FXML
    void eliminar(ActionEvent event) {
        String eliminar = "SET XACT_ABORT ON; BEGIN DISTRIBUTED TRAN; DELETE FROM v_cliente WHERE ID_Cliente = ?; COMMIT TRANSACTION";
        Connection conexion = DBConnection.getConnection();
        try {
            Alert alert;
            if (txtCedula.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Llena todos los espacios");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje de confirmacion");
                alert.setHeaderText(null);
                alert.setContentText("Estas seguro de eliminar los datos del cliente con cedula: " + txtCedula.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    PreparedStatement prep = conexion.prepareStatement(eliminar);
                    prep.setString(1, txtCedula.getText());
                    prep.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacion");
                    alert.setHeaderText(null);
                    alert.setContentText("Cliente eliminado exitosamente!");
                    alert.showAndWait();
                    mostrarListaDatosCliente();
                    resetearDatos();
                }
            }
        } catch (Exception e) {
            System.out.println("Error en eliminar cliente : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void registrar(ActionEvent event) {
        List<String> errors = validarEntradas();
        String registrar = "SET XACT_ABORT ON; BEGIN DISTRIBUTED TRAN; INSERT INTO v_cliente (ID_Cliente, Nombres_cli, Apellidos_cli, Direccion_cli, Telefono_cli, Email_cli, ID_Sucursal) VALUES (?, ?, ?, ?, ?, ?, ?); COMMIT TRANSACTION;";
        Connection conexion = DBConnection.getConnection();
        try {
            Alert alert;
            if (!errors.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Llene los campos correctamente");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("MENSAJE DE CONFIRMACION");
                alert.setHeaderText(null);
                alert.setContentText("Estas seguro de guardar los datos del cliente?");
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().equals(ButtonType.OK)) {
                    PreparedStatement preparar = conexion.prepareStatement(registrar);
                    preparar.setString(1, txtCedula.getText());
                    preparar.setString(2, txtNombres.getText());
                    preparar.setString(3, txtApellidos.getText());
                    preparar.setString(4, txtDireccion.getText());
                    preparar.setString(5, txtTelefono.getText());
                    preparar.setString(6, txtEmail.getText());
                    preparar.setString(7, obtenerIdSucursal(txtCiudad.getValue()));
                    preparar.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacion");
                    alert.setHeaderText(null);
                    alert.setContentText("Registro exitoso!");
                    alert.showAndWait();
                    mostrarListaDatosCliente();
                    resetearDatos();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No se registro al cliente");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            System.out.println("Error en registrar cliente : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private ObservableList<Cliente> anadirListaCliente;

    public void buscarCliente() {
        // Asegurarse de que anadirListaCliente esté inicializada antes de usarla
        FilteredList<Cliente> filtro = new FilteredList<>(anadirListaCliente, e -> true);
        txtBuscar.textProperty().addListener((Observable, oldValue, newValue) -> {
            filtro.setPredicate(predicadoCliente -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String palabraClave = newValue.toLowerCase();
                if (predicadoCliente.getCedula().toLowerCase().contains(palabraClave)) {
                    return true;
                } else if (predicadoCliente.getApellidos().toLowerCase().contains(palabraClave)) {
                    return true;
                } else if (predicadoCliente.getCiudad().toLowerCase().contains(palabraClave)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Cliente> ordenarLista = new SortedList<>(filtro);
        ordenarLista.comparatorProperty().bind(tablaCliente.comparatorProperty());
        tablaCliente.setItems(ordenarLista);
    }

    public ObservableList<Cliente> anadirDatosListaCliente() {
        ObservableList<Cliente> listaDatos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM v_cliente";
        Connection conexion = DBConnection.getConnection();
        try {
            PreparedStatement preparar = conexion.prepareStatement(sql);
            ResultSet resultado = preparar.executeQuery();
            Cliente cliente;
            while (resultado.next()) {
                cliente = new Cliente(
                        resultado.getString("ID_Cliente"),
                        resultado.getString("Nombres_cli"),
                        resultado.getString("Apellidos_cli"),
                        resultado.getString("Direccion_cli"),
                        resultado.getString("Telefono_cli"),
                        resultado.getString("Email_cli"),
                        resultado.getString("ID_Sucursal"));
                listaDatos.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDatos;
    }

    public void mostrarListaDatosCliente() {
        anadirListaCliente = anadirDatosListaCliente();
        columnaCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        columnaNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnaDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Convertir 1/2 a Quito/Guayaquil en la columna de Ciudad
        columnaCiudad.setCellValueFactory(cellData -> {
            String ciudad = cellData.getValue().getCiudad(); // Esto retorna 1 o 2
            String ciudadNombre = obtenerCiudadPorId(ciudad); // Convertimos 1/2 a Quito/Guayaquil
            return new SimpleStringProperty(ciudadNombre);
        });

        tablaCliente.setItems(anadirListaCliente);
    }

    public void seleccionarCliente() {
        tablaCliente.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtCedula.setText(newValue.getCedula());
                txtNombres.setText(newValue.getNombres());
                txtApellidos.setText(newValue.getApellidos());
                txtDireccion.setText(newValue.getDireccion());
                txtTelefono.setText(newValue.getTelefono());
                txtEmail.setText(newValue.getEmail());
                txtCiudad.setValue(obtenerCiudadPorId(newValue.getCiudad()));
                tablaCliente.refresh();
            }
        });
    }

    public void resetearDatos() {
        txtCedula.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCiudad.getSelectionModel().clearSelection();
    }

    private String obtenerIdSucursal(String ciudad) {
        switch (ciudad) {
            case "Quito":
                return "1";
            case "Guayaquil":
                return "2";
            default:
                return null;
        }
    }

    private List<String> validarEntradas() {
        List<String> errors = new ArrayList<>();
        Validation.validarExistencia(txtCedula, lblCedula, errors, "cliente");
        Validation.validarDosPalabras(txtNombres, lblNombres, errors);
        Validation.validarDosPalabras(txtApellidos, lblApellidos, errors);
        Validation.validarEmail(txtEmail, lblEmail, errors);
        Validation.validarNulos(txtDireccion, lblDireccion, errors);
        Validation.validarTelefono(txtTelefono, lblTelefono, errors);
        Validation.validarComboBox(txtCiudad, lblCiudad, errors);
        return errors;
    }

    private String obtenerCiudadPorId(String idSucursal) {
        switch (idSucursal) {
            case "1":
                return "Quito";
            case "2":
                return "Guayaquil";
            default:
                return "";
        }
    }
}
