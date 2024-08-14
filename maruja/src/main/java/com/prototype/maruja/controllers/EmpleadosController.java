package com.prototype.maruja.controllers;

import com.prototype.maruja.database.DBConnection;
import com.prototype.maruja.models.Empleado;
import com.prototype.maruja.utils.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpleadosController {

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
    private Button btnRegistrarTelefono;

    @FXML
    private TableColumn<Empleado, String> columnaCedula;

    @FXML
    private TableColumn<Empleado, String> columnaApellidos;

    @FXML
    private TableColumn<Empleado, String> columnaEmail;

    @FXML
    private TableColumn<Empleado, String> columnaNombres;

    @FXML
    private TableColumn<Empleado, String> columnaCiudad;

    @FXML
    private TableColumn<Empleado, String> columnaTelefonos;

    @FXML
    private Label lblApellidos;

    @FXML
    private Label lblCedula;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblNombres;

    @FXML
    private Label lblTelefonoPrincipal;

    @FXML
    private Label lblTelefonoAdicional;

    @FXML
    private Label lblCiudad;

    @FXML
    private ComboBox<String> txtCiudad;
    private String[] items = {"Quito", "Guayaquil"};

    @FXML
    private TableView<Empleado> tablaEmpleado;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtTelefonoPrincipal;

    @FXML
    private TextField txtTelefonoAdicional;

    List<String> errors = new ArrayList<>();

    @FXML
    public void initialize() {
        txtCiudad.getItems().setAll(items);
        txtCedula.requestFocus();
        txtCedula.setOnAction(e -> {
            Validation.validarExistencia(txtCedula, lblCedula, errors, "empleado");
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
            txtTelefonoPrincipal.requestFocus();
        });
        txtTelefonoPrincipal.setOnAction(e -> {
            txtTelefonoAdicional.requestFocus();
        });
        txtTelefonoAdicional.setOnAction(e -> {
            btnRegistrar.fire();
        });
        mostrarListaDatosEmpleado();
        buscarEmpleado();
        seleccionarEmpleado();
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
            if (txtCedula.getText().isEmpty() || txtEmail.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Llena todos los espacios");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje de confirmacion");
                alert.setHeaderText(null);
                alert.setContentText("Estas seguro de actualizar los datos del empleado con ID: " + txtCedula.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    String actualizarDatos = "UPDATE v_empleado SET Email_empleado = ?, ID_Sucursal = ? WHERE ID_Empleado = ?";
                    // String actualizarDatos = "SET XACT_ABORT ON; BEGIN DISTRIBUTED TRAN; UPDATE v_empleado SET Email_empleado = ?, ID_Sucursal = ? WHERE ID_Empleado = ?; COMMIT TRANSACTION";
                    PreparedStatement prep = conexion.prepareStatement(actualizarDatos);
                    prep.setString(1, txtEmail.getText());
                    prep.setInt(2, txtCiudad.getValue().equals("Quito") ? 1 : 2);
                    prep.setString(3, txtCedula.getText());
                    prep.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacion");
                    alert.setHeaderText(null);
                    alert.setContentText("Actualizado exitosamente!");
                    alert.showAndWait();
                    mostrarListaDatosEmpleado();
                    resetearDatos();
                }
            }
        } catch (Exception e) {
            System.out.println("Error en actualizar empleado : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        resetearDatos();
    }

    @FXML
    void eliminar(ActionEvent event) {

        String eliminar = "SET XACT_ABORT ON; BEGIN DISTRIBUTED TRAN; DELETE FROM v_empleado WHERE ID_Empleado = ?; COMMIT TRANSACTION";
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
                alert.setContentText("Estas seguro de eliminar los datos del empleado con ID: " + txtCedula.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    PreparedStatement prep = conexion.prepareStatement(eliminar);
                    prep.setString(1, txtCedula.getText());
                    prep.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacion");
                    alert.setHeaderText(null);
                    alert.setContentText("Empleado eliminado exitosamente!");
                    alert.showAndWait();
                    mostrarListaDatosEmpleado();
                    resetearDatos();
                }
            }
        } catch (Exception e) {
            System.out.println("Error en eliminar empleado : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void registrar(ActionEvent event) {
        List<String> errors = validarEntradas();
        String registrar = "SET XACT_ABORT ON; BEGIN DISTRIBUTED TRAN; INSERT INTO v_empleado (ID_Empleado, Nombre_empleado, Apellido_empleado, Email_empleado, ID_Sucursal) VALUES (?, ?, ?, ?, ?); COMMIT TRANSACTION";
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
                PreparedStatement preparar = conexion.prepareStatement(registrar);
                preparar.setString(1, txtCedula.getText());
                preparar.setString(2, txtNombres.getText());
                preparar.setString(3, txtApellidos.getText());
                preparar.setString(4, txtEmail.getText());
                preparar.setInt(5, txtCiudad.getValue().equals("Quito") ? 1 : 2);
                preparar.executeUpdate();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setHeaderText(null);
                alert.setContentText("Registro exitoso!");
                alert.showAndWait();
                mostrarListaDatosEmpleado();
                resetearDatos();
            }
        } catch (Exception e) {
            System.out.println("Error en registrar empleado : " + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    void registrarTelefono(ActionEvent event) {
        Alert alert;
        if (!txtCedula.getText().isEmpty() && !txtTelefonoPrincipal.getText().isEmpty()) {
            String registrarTelefono = "SET XACT_ABORT ON; BEGIN DISTRIBUTED TRAN; INSERT INTO v_telefono (ID_Empleado, Telefono_empleado, ID_Sucursal) VALUES (?, ?, ?); COMMIT TRANSACTION";
            Connection conexion = DBConnection.getConnection();
            try (PreparedStatement preparar = conexion.prepareStatement(registrarTelefono)) {
                preparar.setString(1, txtCedula.getText());
                preparar.setString(2, txtTelefonoPrincipal.getText());
                preparar.setInt(3, txtCiudad.getValue().equals("Quito") ? 1 : 2);
                preparar.executeUpdate();
                resetearDatos();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setHeaderText(null);
                alert.setContentText("Telefono registrado correctamente");
                alert.showAndWait();
            } catch (SQLException e) {
                System.out.println("Error en registrar telefono : " + e.getMessage());
            }
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Llene los campos correctamente");
            alert.showAndWait();
        }
    }

    private ObservableList<Empleado> anadirListaEmpleado;

    public void buscarEmpleado() {
        FilteredList<Empleado> filtro = new FilteredList<>(anadirListaEmpleado, e -> true);
        txtBuscar.textProperty().addListener((Observable, oldValue, newValue) -> {
            filtro.setPredicate(predicadoEmpleado -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String palabraClave = newValue.toLowerCase();
                if (predicadoEmpleado.getCedula().contains(palabraClave)) {
                    return true;
                } else if (predicadoEmpleado.getApellidos().toLowerCase().contains(palabraClave)) {
                    return true;
                } else if (predicadoEmpleado.getCiudad().toLowerCase().contains(palabraClave)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Empleado> ordenarLista = new SortedList<>(filtro);
        ordenarLista.comparatorProperty().bind(tablaEmpleado.comparatorProperty());
        tablaEmpleado.setItems(ordenarLista);
    }

    public ObservableList<Empleado> anadirDatosListaEmpleado() {
        ObservableList<Empleado> listaDatos = FXCollections.observableArrayList();
        String sqlEmpleado = "SELECT * FROM v_empleado";
        String sqlTelefonos = "SELECT Telefono_empleado FROM v_telefono WHERE ID_Empleado = ? AND ID_Sucursal = ?";
        Connection conexion = DBConnection.getConnection();

        try {
            PreparedStatement prepararEmpleado = conexion.prepareStatement(sqlEmpleado);
            ResultSet resultadoEmpleado = prepararEmpleado.executeQuery();

            while (resultadoEmpleado.next()) {
                String idEmpleado = resultadoEmpleado.getString("ID_Empleado");
                int idSucursal = resultadoEmpleado.getInt("ID_Sucursal");

                // Consultar los teléfonos para el empleado actual en la sucursal específica
                PreparedStatement prepararTelefonos = conexion.prepareStatement(sqlTelefonos);
                prepararTelefonos.setString(1, idEmpleado);
                prepararTelefonos.setInt(2, idSucursal);
                ResultSet resultadoTelefonos = prepararTelefonos.executeQuery();

                List<String> telefonos = new ArrayList<>();
                while (resultadoTelefonos.next()) {
                    telefonos.add(resultadoTelefonos.getString("Telefono_empleado"));
                }

                Empleado empleado = new Empleado(
                        idEmpleado,
                        resultadoEmpleado.getString("Nombre_empleado"),
                        resultadoEmpleado.getString("Apellido_empleado"),
                        resultadoEmpleado.getString("Email_empleado"),
                        idSucursal == 1 ? "Quito" : "Guayaquil",
                        telefonos
                );
                listaDatos.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDatos;
    }

    public void mostrarListaDatosEmpleado() {
        anadirListaEmpleado = anadirDatosListaEmpleado();
        columnaCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        columnaNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnaCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        columnaTelefonos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefonosFormatted()));
        tablaEmpleado.setItems(anadirListaEmpleado);
    }

    public void seleccionarEmpleado() {
        tablaEmpleado.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtCedula.setText(newValue.getCedula());
                txtNombres.setText(newValue.getNombres());
                txtApellidos.setText(newValue.getApellidos());
                txtEmail.setText(newValue.getEmail());
                txtCiudad.setValue(newValue.getCiudad());
                tablaEmpleado.refresh();
            }
        });
    }

    public void resetearDatos() {
        txtCedula.clear();
        txtNombres.clear();
        txtApellidos.clear();
        txtEmail.clear();
        txtTelefonoPrincipal.clear();
        txtTelefonoAdicional.clear();
        txtCiudad.getSelectionModel().clearSelection();
    }

    private List<String> validarEntradas() {
        List<String> errors = new ArrayList<>();
        Validation.validarExistencia(txtCedula, lblCedula, errors, "empleado");
        Validation.validarDosPalabras(txtNombres, lblNombres, errors);
        Validation.validarDosPalabras(txtApellidos, lblApellidos, errors);
        Validation.validarEmail(txtEmail, lblEmail, errors);
        return errors;
    }
}
