package com.prototype.maruja.models;

public class MostrarFacturaController {
    private String id;
    private String ciCliente;
    private String ciEmpleado;
    private String fecha;
    private double monto;
    private String ciudad;

    public MostrarFacturaController() {}

    public MostrarFacturaController(String id, String ciCliente, String ciEmpleado, String fecha, double monto, String ciudad) {
        this.id = id;
        this.ciCliente = ciCliente;
        this.ciEmpleado = ciEmpleado;
        this.fecha = fecha;
        this.monto = monto;
        this.ciudad = ciudad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCiCliente() {
        return ciCliente;
    }

    public void setCiCliente(String ciCliente) {
        this.ciCliente = ciCliente;
    }

    public String getCiEmpleado() {
        return ciEmpleado;
    }

    public void setCiEmpleado(String ciEmpleado) {
        this.ciEmpleado = ciEmpleado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
