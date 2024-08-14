package com.prototype.maruja.utils;

import com.prototype.maruja.database.DBConnection;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Validation {
    public static boolean chequearCedula(String identityCard){
        if (identityCard.matches("^[0-9]{10}$")){
            return true;
        } else {
            return false;
        }
        /*
        int digitos;
        int digitoValidador;
        int sumaDigitos = 0;
        int resultadoDigitos;
        if (identityCard.length() == 10) {
            int provincia = Integer.parseInt(identityCard.substring(0, 2));
            if (provincia < 1 || (provincia > 24 && provincia != 30)) {
                return false;
            }
        }
        if (identityCard.length() == 10){
            for (int i = 0; i < identityCard.length() - 1; i++){
                digitos = Integer.parseInt(String.valueOf(identityCard.charAt(i)));
                if (i % 2 == 0) {
                    digitos = digitos * 2;
                    if(digitos > 9){
                        digitos = digitos - 9;
                        sumaDigitos = sumaDigitos + digitos;
                    } else {
                        sumaDigitos = sumaDigitos + digitos;
                    }
                } else {
                    sumaDigitos = sumaDigitos + digitos;
                }
            }
            if(sumaDigitos % 10 == 0){
                resultadoDigitos = 0;
            }else{
                resultadoDigitos = 10 - (sumaDigitos % 10);
            }
            digitoValidador = Integer.parseInt(String.valueOf(identityCard.charAt(9)));
            if (resultadoDigitos == digitoValidador) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
         */
    }
    public static boolean chequearDosPalabras(String palabras){
        if(palabras.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+\\s[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+$")){
            return true;
        }else {
            return false;
        }
    }
    public static boolean chequearTelefono(String telefono){
        if (telefono.matches("^09[0-9]{8}$")){
            return true;
        } else {
            return false;
        }
    }
    public static boolean chequearEmail (String email){
        if (email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}(\\.[a-zA-Z]{2,4}){0,2}$")){
            return true;
        }else {
            return false;
        }
    }
    public static boolean chequearExistencia(String cedula, String tabla) {
        String query = "SELECT CASE WHEN EXISTS (SELECT 1 FROM " + tabla + " WHERE cedula = ?) THEN 1 ELSE 0 END";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, cedula);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 1;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en el chequeo de existencias: " + e.getMessage());
        }
        return false;
    }
    public static boolean chequearExistenciaFactura(String cedula, String tabla) {
        String query = "SELECT CASE WHEN EXISTS (SELECT 1 FROM " + tabla + " WHERE id = ?) THEN 1 ELSE 0 END";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, cedula);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 1;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en el chequeo de existencias: " + e.getMessage());
        }
        return false;
    }
    public static boolean chequearNoExistencia(String cedula, String tabla) {
        String query = "SELECT CASE WHEN EXISTS (SELECT 1 FROM " + tabla + " WHERE cedula = ?) THEN 1 ELSE 0 END";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, cedula);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 1;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en el chequeo de existencias: " + e.getMessage());
        }
        return false;
    }
    public static boolean chequearExistenciaProducto(String codigo, String tabla) {
        String query = "SELECT CASE WHEN EXISTS (SELECT 1 FROM " + tabla + " WHERE codigo = ?) THEN 1 ELSE 0 END";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, codigo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 1;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en el chequeo de existencias: " + e.getMessage());
        }
        return false;
    }
    public static boolean chequearCodigoProducto(String codigo) {
        if (codigo.matches("^[0-9]{3}$")) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean chequearPrecio(String precio) {
        if (precio.matches("^[0-9]{1,4}.[0-9]{1,2}$")) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean chequearStock(String stock) {
        if (stock.matches("^[0-9]{1,4}$")) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean chequearFecha(String fecha) {
        return fecha.matches("\\d{4}-(0[1-9]|1[0-2])-(0[1-9]{1,2}|[12][0-9]|3[01])");
    }


    public static List<String> validarComboBox (ComboBox<String> txt, Label lbl, List<String> errors){
        if (txt.getValue() == null) {
            lbl.setVisible(true);
            txt.setStyle("-fx-border-color: transparent red red red;");
            errors.add("Error: Invalid Combo Box");
        }
        txt.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txt.setStyle("-fx-border-color: #3F84B7;");
                lbl.setVisible(false);
            } else {
                if (txt.getValue() == null) {
                    lbl.setVisible(true);
                    txt.setStyle("-fx-border-color: transparent red red red;");
                }
            }
        });
        return errors;
    }
    public static List<String> validarExistencia (TextField txtIdentityCard, Label lblIdentityCard, List<String> errors, String module){
        boolean isValid = Validation.chequearCedula(txtIdentityCard.getText());

        if (isValid) {
            if (Validation.chequearExistencia(txtIdentityCard.getText(), module)) {
                lblIdentityCard.setVisible(true);
                lblIdentityCard.setText("El número de cédula ya existe");
                txtIdentityCard.setStyle("-fx-border-color: transparent red red red;");
                errors.add("Error: El número de cédula ya existe");
            }
        } else {
            Validation.validarCedula(txtIdentityCard, lblIdentityCard, errors);
        }
        txtIdentityCard.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txtIdentityCard.setStyle("-fx-border-color: #3F84B7;");
                lblIdentityCard.setVisible(false);
            } else {
                if (!Validation.chequearCedula(txtIdentityCard.getText())) {
                    lblIdentityCard.setVisible(true);
                    lblIdentityCard.setText("Número de cédula no válido");
                    txtIdentityCard.setStyle("-fx-border-color: transparent red red red;");
                } else if (Validation.chequearExistencia(txtIdentityCard.getText(), module)) {
                    lblIdentityCard.setVisible(true);
                    lblIdentityCard.setText("El número de cédula ya existe");
                    txtIdentityCard.setStyle("-fx-border-color: transparent red red red;");
                } else {
                    txtIdentityCard.setStyle("-fx-border-color: #3F84B7;");
                    lblIdentityCard.setVisible(false);
                }
            }
        });
        return errors;
    }
    public static List<String> validarNoExistenciaFactura (TextField txtIdentityCard, Label lblIdentityCard, List<String> errors, String module){
        boolean isValid = Validation.chequearCedula(txtIdentityCard.getText());

        if (isValid) {
            if (!Validation.chequearExistenciaFactura(txtIdentityCard.getText(), module)) {
                lblIdentityCard.setVisible(true);
                lblIdentityCard.setText("El número ya existe");
                txtIdentityCard.setStyle("-fx-border-color: transparent red red red;");
                errors.add("Error: El número ya existe");
            }
        } else {
            Validation.validarCedula(txtIdentityCard, lblIdentityCard, errors);
        }
        txtIdentityCard.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txtIdentityCard.setStyle("-fx-border-color: #3F84B7;");
                lblIdentityCard.setVisible(false);
            } else {
                if (!Validation.chequearCedula(txtIdentityCard.getText())) {
                    lblIdentityCard.setVisible(true);
                    lblIdentityCard.setText("Número ya válido");
                    txtIdentityCard.setStyle("-fx-border-color: transparent red red red;");
                } else if (Validation.chequearExistenciaFactura(txtIdentityCard.getText(), module)) {
                    lblIdentityCard.setVisible(true);
                    lblIdentityCard.setText("El número ya existe");
                    txtIdentityCard.setStyle("-fx-border-color: transparent red red red;");
                } else {
                    txtIdentityCard.setStyle("-fx-border-color: #3F84B7;");
                    lblIdentityCard.setVisible(false);
                }
            }
        });
        return errors;
    }
    public static List<String> validarNoExistencia (TextField txtIdentityCard, Label lblIdentityCard, List<String> errors, String module){
        boolean isValid = Validation.chequearCedula(txtIdentityCard.getText());

        if (isValid) {
            if (!Validation.chequearExistencia(txtIdentityCard.getText(), module)) {
                lblIdentityCard.setVisible(true);
                lblIdentityCard.setText("El número de cédula no existe");
                txtIdentityCard.setStyle("-fx-border-color: transparent red red red;");
                errors.add("Error: Ya existe");
            }
        } else {
            Validation.validarCedula(txtIdentityCard, lblIdentityCard, errors);
        }
        txtIdentityCard.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txtIdentityCard.setStyle("-fx-border-color: #3F84B7;");
                lblIdentityCard.setVisible(false);
            } else {
                if (!Validation.chequearCedula(txtIdentityCard.getText())) {
                    lblIdentityCard.setVisible(true);
                    lblIdentityCard.setText("Número de cédula no válido");
                    txtIdentityCard.setStyle("-fx-border-color: transparent red red red;");
                } else if (!Validation.chequearExistencia(txtIdentityCard.getText(), module)) {
                    lblIdentityCard.setVisible(true);
                    lblIdentityCard.setText("El número de cédula no existe");
                    txtIdentityCard.setStyle("-fx-border-color: transparent red red red;");
                } else {
                    txtIdentityCard.setStyle("-fx-border-color: #3F84B7;");
                    lblIdentityCard.setVisible(false);
                }
            }
        });
        return errors;
    }
    public static List<String> validarExistenciaProducto (TextField txtIdentityCard, Label lblIdentityCard, List<String> errors, String module){
        boolean isValid = Validation.chequearCodigoProducto(txtIdentityCard.getText());

        if (isValid) {
            if (Validation.chequearExistenciaProducto(txtIdentityCard.getText(), module)) {
                lblIdentityCard.setVisible(true);
                lblIdentityCard.setText("El número de cédula ya existe");
                txtIdentityCard.setStyle("-fx-border-color: transparent red red red;");
                errors.add("Error: El número de cédula ya existe");
            }
        } else {
            Validation.validarProducto(txtIdentityCard, lblIdentityCard, errors);
        }
        txtIdentityCard.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txtIdentityCard.setStyle("-fx-border-color: #3F84B7;");
                lblIdentityCard.setVisible(false);
            } else {
                if (!Validation.chequearCedula(txtIdentityCard.getText())) {
                    lblIdentityCard.setVisible(true);
                    lblIdentityCard.setText("Codigo no válido");
                    txtIdentityCard.setStyle("-fx-border-color: transparent red red red;");
                } else if (Validation.chequearExistenciaProducto(txtIdentityCard.getText(), module)) {
                    lblIdentityCard.setVisible(true);
                    lblIdentityCard.setText("El codigo del producto ya existe");
                    txtIdentityCard.setStyle("-fx-border-color: transparent red red red;");
                } else {
                    txtIdentityCard.setStyle("-fx-border-color: #3F84B7;");
                    lblIdentityCard.setVisible(false);
                }
            }
        });
        return errors;
    }
    public static List<String> validarDosPalabras (TextField txt, Label lbl, List<String> errors){
        if (!Validation.chequearDosPalabras(txt.getText())) {
            lbl.setVisible(true);
            txt.setStyle("-fx-border-color: transparent red red red;");
            errors.add("Error: Invalid Two Words");
        }
        txt.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txt.setStyle("-fx-border-color: #3F84B7;");
                lbl.setVisible(false);
            } else {
                if (!Validation.chequearDosPalabras(txt.getText())) {
                    lbl.setVisible(true);
                    txt.setStyle("-fx-border-color: transparent red red red;");
                }
            }
        });
        return errors;
    }
    public static List<String> validarEmail (TextField txt, Label lbl, List<String> errors){
        if (!Validation.chequearEmail(txt.getText())) {
            lbl.setVisible(true);
            txt.setStyle("-fx-border-color: transparent red red red;");
            errors.add("Error: Invalid Two Words");
        }
        txt.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txt.setStyle("-fx-border-color: #3F84B7;");
                lbl.setVisible(false);
            } else {
                if (!Validation.chequearEmail(txt.getText())) {
                    lbl.setVisible(true);
                    txt.setStyle("-fx-border-color: transparent red red red;");
                }
            }
        });
        return errors;
    }
    public static List<String> validarFecha (TextField txt, Label lbl, List<String> errors){
        if (!Validation.chequearFecha(txt.getText())) {
            lbl.setVisible(true);
            txt.setStyle("-fx-border-color: transparent red red red;");
            errors.add("Error: Invalid Two Words");
        }
        txt.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txt.setStyle("-fx-border-color: #3F84B7;");
                lbl.setVisible(false);
            } else {
                if (!Validation.chequearFecha(txt.getText())) {
                    lbl.setVisible(true);
                    txt.setStyle("-fx-border-color: transparent red red red;");
                }
            }
        });
        return errors;
    }
    public static List<String> validarCedula (TextField txt, Label lbl, List<String> errors){
        if (!Validation.chequearCedula(txt.getText())) {
            lbl.setVisible(true);
            txt.setStyle("-fx-border-color: transparent red red red;");
            errors.add("Error: Invalid Two Words");
        }
        txt.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txt.setStyle("-fx-border-color: linear-gradient(to bottom right, #6b6868, #62bd9d);");
                lbl.setVisible(false);
            } else {
                if (!Validation.chequearCedula(txt.getText())) {
                    lbl.setVisible(true);
                    txt.setStyle("-fx-border-color: transparent red red red;");
                }
            }
        });
        return errors;
    }
    public static List<String> validarProducto (TextField txt, Label lbl, List<String> errors){
        if (!Validation.chequearCodigoProducto(txt.getText())) {
            lbl.setVisible(true);
            txt.setStyle("-fx-border-color: transparent red red red;");
            errors.add("Error: Invalid Two Words");
        }
        txt.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txt.setStyle("-fx-border-color: linear-gradient(to bottom right, #6b6868, #62bd9d);");
                lbl.setVisible(false);
            } else {
                if (!Validation.chequearCodigoProducto(txt.getText())) {
                    lbl.setVisible(true);
                    txt.setStyle("-fx-border-color: transparent red red red;");
                }
            }
        });
        return errors;
    }
    public static List<String> validarTelefono (TextField txt, Label lbl, List<String> errors){
        if (!Validation.chequearTelefono(txt.getText())) {
            lbl.setVisible(true);
            txt.setStyle("-fx-border-color: transparent red red red;");
            errors.add("Error: Invalid Two Words");
        }
        txt.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txt.setStyle("-fx-border-color: #3F84B7;");
                lbl.setVisible(false);
            } else {
                if (!Validation.chequearTelefono(txt.getText())) {
                    lbl.setVisible(true);
                    txt.setStyle("-fx-border-color: transparent red red red;");
                }
            }
        });
        return errors;
    }
    public static List<String> validarPrecio (TextField txt, Label lbl, List<String> errors){
        if (!Validation.chequearPrecio(txt.getText())) {
            lbl.setVisible(true);
            txt.setStyle("-fx-border-color: transparent red red red;");
            errors.add("Error: Invalid Two Words");
        }
        txt.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txt.setStyle("-fx-border-color: #3F84B7;");
                lbl.setVisible(false);
            } else {
                if (!Validation.chequearPrecio(txt.getText())) {
                    lbl.setVisible(true);
                    txt.setStyle("-fx-border-color: transparent red red red;");
                }
            }
        });
        return errors;
    }
    public static List<String> validarStock (TextField txt, Label lbl, List<String> errors){
        if (!Validation.chequearStock(txt.getText())) {
            lbl.setVisible(true);
            txt.setStyle("-fx-border-color: transparent red red red;");
            errors.add("Error: Invalid Two Words");
        }
        txt.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txt.setStyle("-fx-border-color: #3F84B7;");
                lbl.setVisible(false);
            } else {
                if (!Validation.chequearStock(txt.getText())) {
                    lbl.setVisible(true);
                    txt.setStyle("-fx-border-color: transparent red red red;");
                }
            }
        });
        return errors;
    }
    public static List<String> validarNulos (TextField txt, Label lbl, List<String> errors){
        if (txt.getText().isEmpty() && txt.getText().isBlank()) {
            lbl.setVisible(true);
            txt.setStyle("-fx-border-color: transparent red red red;");
            errors.add("Error: Invalid Two Words");
        }
        txt.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                txt.setStyle("-fx-border-color: #3F84B7;");
                lbl.setVisible(false);
            } else {
                if (txt.getText().isEmpty() && txt.getText().isBlank()) {
                    lbl.setVisible(true);
                    txt.setStyle("-fx-border-color: transparent red red red;");
                }
            }
        });
        return errors;
    }
}
