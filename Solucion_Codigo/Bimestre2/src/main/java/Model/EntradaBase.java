/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Diego
 */
public abstract class EntradaBase {

    protected String cedulaCliente;
    protected EventoBase evento;
    protected double precio;

    public EntradaBase(String cedulaCliente, EventoBase evento, double precio) {
        this.cedulaCliente = cedulaCliente;
        this.evento = evento;
        this.precio = precio;
    }

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public EventoBase getEvento() {
        return evento;
    }

    public double getPrecioBase() {
        return precio;
    }

    public abstract double calcularPrecioFinal();

    public abstract String getTipo();
}
