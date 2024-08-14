package com.prototype.maruja.models;

public class Detalle {
    private int linea;
    private int cantidad;
    private String nombre;
    private double precioUnitario;
    private double precioTotal;

    public Detalle(){}

    public Detalle(int linea, int cantidad, String nombre, double precioUnitario, double precioTotal) {
        this.linea = linea;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.precioTotal = precioTotal;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
}
