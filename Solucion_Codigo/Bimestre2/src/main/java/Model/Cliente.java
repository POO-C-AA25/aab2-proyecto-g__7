/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Diego
 */
public class Cliente {

    protected String nombre;
    protected String cedula;
    protected Factura factura;

    public Cliente(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    /* Se aplica el principio S de Single Responsability porque solo se maneja los datos
    del cliente, no se encarga de imprimir ni de generar facturas 
    La clase tiene una única responsabilidad clara: 
    Representar a un cliente, con sus datos personales y su factura asociada y no genera facturas
    */
}
