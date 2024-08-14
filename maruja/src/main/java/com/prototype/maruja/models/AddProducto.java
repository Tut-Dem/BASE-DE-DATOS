package com.prototype.maruja.models;

public class AddProducto {
    private int cantidad;
    private String nombre;
    private double precioUnitario;
    private double precioTotal;

    public AddProducto() {}

    public AddProducto(int cantidad, String nombre, double precioUnitario, double precioTotal) {
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.precioTotal = precioTotal;
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
