package com.prototype.maruja.controllers;

import com.prototype.maruja.database.DBConnection;
import com.prototype.maruja.models.Detalle;
import com.prototype.maruja.models.Factura;
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
import java.time.LocalDate; // Importación para trabajar con fechas
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FacturasController {

    @FXML
    private TableColumn<Factura, String> ColumnaCiEmpleado;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnQuitar;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEmpleados;

    @FXML
    private Button btnFacturas;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnProductos;

    @FXML
    private Label lblCantidad;

    @FXML
    private Label lblCiCliente;

    @FXML
    private Label lblCiEmpleado;

    @FXML
    private Label lblNumFactura;

    @FXML
    private Label lblIndiicio;

    @FXML
    private TableColumn<Detalle, Integer> columnaCantidad;

    @FXML
    private TableColumn<Factura, String> columnaCiCliente;

    @FXML
    private TableColumn<Factura, String> columnaCiudad;

    @FXML
    private TableColumn<Factura, String> columnaFechaEmision;

    @FXML
    private TableColumn<Factura, String> columnaIdFactura;

    @FXML
    private TableColumn<Detalle, Integer> columnaLinea;

    @FXML
    private TableColumn<Factura, String> columnaMonto;

    @FXML
    private TableColumn<Detalle, String> columnaNombre;

    @FXML
    private TableColumn<Detalle, Double> columnaPrecio;

    @FXML
    private TableColumn<Detalle, Double> columnaPrecioTotal;

    @FXML
    private TextField fechaEmision;

    @FXML
    private TableView<Detalle> generarFactura;

    @FXML
    private TextField monto;

    @FXML
    private TableView<Factura> mostrarFactura;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtCedCliente;

    @FXML
    private TextField txtCedEmpleado;

    @FXML
    private ComboBox<String> txtCiuSucursal;
    private String[] items = {"1", "2"};  // 1 = Quito, 2 = Guayaquil

    @FXML
    private TextField txtDirSucursal;

    @FXML
    private TextField txtIdSucursal;

    @FXML
    private TextField txtNumFactura;

    @FXML
    private TextField txtProducto;

    @FXML
    public void initialize() {
        // Llenar automáticamente la fecha con la fecha actual del sistema
        fechaEmision.setText(LocalDate.now().toString());

        txtCiuSucursal.getItems().setAll(items);
        txtCiuSucursal.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedID = newValue;
                String sql = "SELECT * FROM sucursal WHERE ID_Sucursal = ?";
                Connection conn = DBConnection.getConnection();
                try {
                    PreparedStatement preparar = conn.prepareStatement(sql);
                    preparar.setString(1, selectedID);
                    ResultSet resultado = preparar.executeQuery();
                    if (resultado.next()) {
                        txtIdSucursal.setText(resultado.getString("ID_Sucursal"));
                        txtDirSucursal.setText(resultado.getString("Direccion_Sucursal"));
                    } else {
                        System.out.println("No sucursal found for ID: " + selectedID);
                    }
                    resultado.close();
                    preparar.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        seleccionarFactura();
        mostrarListaDatosFactura();
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
            System.out.println("Error in Log out: " + e.getMessage());
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
            System.out.println("Error in Log out: " + e.getMessage());
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
            System.out.println("Error in Log out: " + e.getMessage());
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
            System.out.println("Error in Log out: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void actualizar(ActionEvent event) {
        // Implementar la lógica de actualización si es necesario
    }

    private ObservableList<Detalle> listaDetalles = FXCollections.observableArrayList();
    private int lineaActual = 1;

    @FXML
    void addProducto(ActionEvent event) {
        String consulta = "SELECT Precio_producto FROM Producto WHERE Nombre_producto = ?";
        Connection conexion = DBConnection.getConnection();
        try {
            PreparedStatement preparar = conexion.prepareStatement(consulta);
            preparar.setString(1, txtProducto.getText());
            ResultSet resultado = preparar.executeQuery();
            Detalle detalle;
            while (resultado.next()) {
                String nombre = txtProducto.getText();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                double precio = resultado.getDouble("Precio_producto");  // Asegurarse de que el valor sea numérico
                double precioTotal = cantidad * precio;
                detalle = new Detalle(lineaActual++, cantidad, nombre, precio, precioTotal);
                listaDetalles.add(detalle);
                mostrarDetalleProducto();
                actualizarMontoTotal();
                txtProducto.clear();
                txtCantidad.clear();
                System.out.println("Producto: " + nombre);
            }
        } catch (SQLException e) {
            System.out.println("Error en añadir producto - Factura " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void mostrarDetalleProducto() {
        columnaLinea.setCellValueFactory(new PropertyValueFactory<>("linea"));
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        columnaPrecioTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        generarFactura.setItems(listaDetalles);
    }

    private void actualizarMontoTotal() {
        double total = listaDetalles.stream().mapToDouble(Detalle::getPrecioTotal).sum();
        monto.setText(String.format("%.2f", total));
    }

    @FXML
    void quitar(ActionEvent event) {
        Detalle seleccionado = generarFactura.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaDetalles.remove(seleccionado);
            recalcularLineas();
            actualizarMontoTotal();
        }
    }

    private void recalcularLineas() {
        lineaActual = 1;
        for (Detalle detalle : listaDetalles) {
            detalle.setLinea(lineaActual++);
        }
        generarFactura.refresh();
    }

    @FXML
    void cancelar(ActionEvent event) {
        resetearDatos();
    }

    @FXML
    void eliminar(ActionEvent event) {
        String eliminarFactura = "SET XACT_ABORT ON; BEGIN DISTRIBUTED TRAN; DELETE FROM v_factura WHERE ID_Factura = ?; COMMIT TRANSACTION";
        String obtenerDetallesFactura = "SELECT ID_Producto, Unidades FROM v_detalle WHERE ID_Factura = ?";
        Connection conexion = DBConnection.getConnection();
        Alert alert;

        if (txtNumFactura.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Selecciona la factura a eliminar");
            alert.showAndWait();
        } else {
            try {
                List<Detalle> detallesFactura = new ArrayList<>();
                PreparedStatement preparedStatementDetalles = conexion.prepareStatement(obtenerDetallesFactura);
                preparedStatementDetalles.setString(1, txtNumFactura.getText());
                ResultSet resultSetDetalles = preparedStatementDetalles.executeQuery();
                while (resultSetDetalles.next()) {
                    String idProducto = resultSetDetalles.getString("ID_Producto");
                    int unidades = resultSetDetalles.getInt("Unidades");
                    detallesFactura.add(new Detalle(0, unidades, idProducto, 0, 0));
                }
                resultSetDetalles.close();
                preparedStatementDetalles.close();
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje de confirmacion");
                alert.setHeaderText(null);
                alert.setContentText("Estas seguro de eliminar la factura?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    PreparedStatement preparedStatementFactura = conexion.prepareStatement(eliminarFactura);
                    preparedStatementFactura.setString(1, txtNumFactura.getText());
                    preparedStatementFactura.executeUpdate();
                    String actualizarProducto = "UPDATE Producto SET Stock_producto = Stock_producto + ? WHERE ID_Producto = ?";
                    PreparedStatement preparedStatementActualizarProducto = conexion.prepareStatement(actualizarProducto);
                    for (Detalle detalle : detallesFactura) {
                        preparedStatementActualizarProducto.setInt(1, detalle.getCantidad());
                        preparedStatementActualizarProducto.setString(2, detalle.getNombre());
                        preparedStatementActualizarProducto.addBatch();
                    }
                    preparedStatementActualizarProducto.executeBatch();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacion");
                    alert.setHeaderText(null);
                    alert.setContentText("Factura eliminada exitosamente!");
                    alert.showAndWait();
                    mostrarListaDatosFactura();
                    resetearDatos();
                }
            } catch (SQLException e) {
                System.out.println("Error en eliminar factura: " + e.getMessage());
            }
        }
    }

    @FXML
    void guardar(ActionEvent event) {
        Connection conexion = DBConnection.getConnection();
        Alert alert;
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje de confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Estás seguro de guardar la factura?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                // Verificar la existencia de ID_Empleado y ID_Cliente
                if (!verificarExistenciaID("v_empleado", "ID_Empleado", txtCedEmpleado.getText())) {
                    mostrarAlertaError("El ID de empleado no existe");
                    return;
                }
                if (!verificarExistenciaID("v_cliente", "ID_Cliente", txtCedCliente.getText())) {
                    mostrarAlertaError("El ID de cliente no existe");
                    return;
                }

                conexion.setAutoCommit(false);
                String insertarFactura = "SET XACT_ABORT ON; BEGIN DISTRIBUTED TRAN; INSERT INTO v_factura (ID_Factura, ID_Cliente, ID_Empleado, ID_Sucursal, Fecha, Monto) VALUES (?, ?, ?, ?, ?, ?); COMMIT TRANSACTION";
                PreparedStatement prepararFactura = conexion.prepareStatement(insertarFactura);
                prepararFactura.setString(1, txtNumFactura.getText());
                prepararFactura.setString(2, txtCedCliente.getText());
                prepararFactura.setString(3, txtCedEmpleado.getText());
                prepararFactura.setString(4, txtIdSucursal.getText());
                prepararFactura.setDate(5, java.sql.Date.valueOf(fechaEmision.getText()));
                prepararFactura.setDouble(6, Double.parseDouble(monto.getText().replace(",", ".")));  // Conversión a tipo numérico
                prepararFactura.executeUpdate();

                String insertarDetalle = "SET XACT_ABORT ON; BEGIN DISTRIBUTED TRAN; INSERT INTO v_detalle (Linea, ID_Factura, ID_Producto, Unidades, Precio_Venta) VALUES (?, ?, ?, ?, ?); COMMIT TRANSACTION";
                PreparedStatement prepararDetalle = conexion.prepareStatement(insertarDetalle);
                String actualizarProducto = "UPDATE Producto SET Stock_producto = Stock_producto - ? WHERE ID_Producto = ?";
                PreparedStatement prepararActualizarProducto = conexion.prepareStatement(actualizarProducto);
                for (Detalle detalle : listaDetalles) {
                    String obtenerCodigoProducto = "SELECT ID_Producto FROM Producto WHERE Nombre_producto = ?";
                    PreparedStatement prepararObtenerProducto = conexion.prepareStatement(obtenerCodigoProducto);
                    prepararObtenerProducto.setString(1, detalle.getNombre());
                    ResultSet resultado = prepararObtenerProducto.executeQuery();
                    if (resultado.next()) {
                        String idProducto = resultado.getString("ID_Producto");
                        double precioTotalDetalle = detalle.getCantidad() * detalle.getPrecioUnitario();
                        prepararDetalle.setInt(1, detalle.getLinea());
                        prepararDetalle.setString(2, txtNumFactura.getText());
                        prepararDetalle.setString(3, idProducto);
                        prepararDetalle.setInt(4, detalle.getCantidad());
                        prepararDetalle.setDouble(5, precioTotalDetalle);
                        prepararDetalle.addBatch();
                        prepararActualizarProducto.setInt(1, detalle.getCantidad());
                        prepararActualizarProducto.setString(2, idProducto);
                        prepararActualizarProducto.addBatch();
                    } else {
                        throw new SQLException("No se encontró el ID del producto para " + detalle.getNombre());
                    }
                }
                prepararDetalle.executeBatch();
                prepararActualizarProducto.executeBatch();
                conexion.commit();
                mostrarListaDatosFactura();
                resetearDatos();
                buscaFactura();
                System.out.println("Factura y detalles guardados exitosamente, y el stock de los productos ha sido actualizado.");
            }
        } catch (SQLException e) {
            try {
                if (conexion != null) {
                    conexion.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.out.println("Error en rollback: " + rollbackEx.getMessage());
                rollbackEx.printStackTrace();
            }
            System.out.println("Error en guardar factura: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null) {
                    conexion.setAutoCommit(true);
                    conexion.close();
                }
            } catch (SQLException closeEx) {
                System.out.println("Error al cerrar conexión: " + closeEx.getMessage());
                closeEx.printStackTrace();
            }
        }
    }

    private boolean verificarExistenciaID(String tabla, String campo, String valor) {
        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE " + campo + " = ?";
        Connection conexion = DBConnection.getConnection();
        try {
            PreparedStatement preparar = conexion.prepareStatement(sql);
            preparar.setString(1, valor);
            ResultSet resultado = preparar.executeQuery();
            if (resultado.next()) {
                return resultado.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void mostrarAlertaError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private ObservableList<Factura> anadirListaFactura;

    public void buscaFactura() {
        FilteredList<Factura> filtro = new FilteredList<>(anadirListaFactura, e -> true);
        txtBuscar.textProperty().addListener((Observable, oldValue, newValue) -> {
            filtro.setPredicate(predicadoFactura -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String palabraClave = newValue.toLowerCase();
                if (predicadoFactura.getId().toLowerCase().contains(palabraClave)) {
                    return true;
                } else if (predicadoFactura.getCiCliente().toLowerCase().contains(palabraClave)) {
                    return true;
                } else if (predicadoFactura.getCiudad().toLowerCase().contains(palabraClave)) {
                    return true;
                } else if (predicadoFactura.getCiEmpleado().toLowerCase().contains(palabraClave)) {
                    return true;
                } else if (predicadoFactura.getFecha().toLowerCase().contains(palabraClave)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Factura> ordenarLista = new SortedList<>(filtro);
        ordenarLista.comparatorProperty().bind(mostrarFactura.comparatorProperty());
        mostrarFactura.setItems(ordenarLista);
    }

    public ObservableList<Factura> anadirDatosListaFactura() {
        ObservableList<Factura> listaDatos = FXCollections.observableArrayList();
        String sql = "SELECT ID_Factura, ID_Cliente, ID_Empleado, ID_Sucursal, Fecha, Monto FROM v_factura";
        Connection conexion = DBConnection.getConnection();
        try {
            PreparedStatement preparar = conexion.prepareStatement(sql);
            ResultSet resultado = preparar.executeQuery();
            Factura factura;
            while (resultado.next()) {
                factura = new Factura(
                        resultado.getString("ID_Factura"),
                        resultado.getString("ID_Cliente"),
                        resultado.getString("ID_Empleado"),
                        resultado.getString("ID_Sucursal"),
                        resultado.getString("Fecha"),
                        resultado.getDouble("Monto")
                );
                listaDatos.add(factura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDatos;
    }

    public void mostrarListaDatosFactura() {
        anadirListaFactura = anadirDatosListaFactura();
        columnaIdFactura.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaCiCliente.setCellValueFactory(new PropertyValueFactory<>("ciCliente"));
        ColumnaCiEmpleado.setCellValueFactory(new PropertyValueFactory<>("ciEmpleado"));
        columnaFechaEmision.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        columnaCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        mostrarFactura.setItems(anadirListaFactura);
    }

    public void seleccionarFactura() {
        mostrarFactura.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtNumFactura.setText(newValue.getId());
                mostrarFactura.refresh();
            }
        });
    }

    public void resetearDatos() {
        txtNumFactura.clear();
        txtCedEmpleado.clear();
        txtCedCliente.clear();
        txtProducto.clear();
        txtCantidad.clear();
        monto.clear();
        fechaEmision.setText(java.time.LocalDate.now().toString());  // Fecha actual
        listaDetalles.clear();
        txtCiuSucursal.getSelectionModel().clearSelection();
    }
}