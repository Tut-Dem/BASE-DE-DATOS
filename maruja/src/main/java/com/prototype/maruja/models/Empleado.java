package com.prototype.maruja.models;

import java.util.List;

public class Empleado {
    private String cedula;
    private String nombres;
    private String apellidos;
    private String email;
    private String ciudad;
    private List<String> telefonos;

    public Empleado(String cedula, String nombres, String apellidos, String email, String ciudad, List<String> telefonos) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.ciudad = ciudad;
        this.telefonos = telefonos;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getCiudad() {
        return ciudad;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }

    public String getTelefonosFormatted() {
        return String.join(" - ", telefonos);
    }
}
