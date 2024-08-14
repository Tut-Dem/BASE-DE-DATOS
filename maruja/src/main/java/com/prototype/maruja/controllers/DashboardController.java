package com.prototype.maruja.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnEmpleados;

    @FXML
    private Button btnFacturas;

    @FXML
    private Button btnProductos;

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
        }catch (IOException e){
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
        }catch (IOException e){
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
        }catch (IOException e){
            System.out.println("Error in Log out : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
