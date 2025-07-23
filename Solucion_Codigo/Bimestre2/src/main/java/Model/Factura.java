/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.EntradaBase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diego
 */
public class Factura {

    protected String nombreCliente;

    protected List<EntradaBase> boletos;
    // Lista de boletos comprados por el cliente (pueden ser normales o especiales)

    // Constructor: inicializa la factura con el nombre del cliente y crea la lista vacía de boletos
    public Factura(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        this.boletos = new ArrayList<>();
    }

    // Método para agregar un boleto a la factura
    public void agregarBoleto(EntradaBase boleto) {
        boletos.add(boleto);
    }

    // Devuelve la lista completa de boletos registrados en la factura
    public List<EntradaBase> getBoletos() {
        return boletos;
    }

    // Calcula el total sin descuentos, sumando el precio base de cada boleto
    public double calcularTotal() {
        double total = 0;
        for (EntradaBase boleto : boletos) {
            total += boleto.getPrecioBase(); // Precio sin descuento
        }
        return total;
    }

    // Calcula el total con descuentos, utilizando el método específico de cada tipo de entrada
    public double calcularTotalConDescuento() {
        double total = 0;
        for (EntradaBase boleto : boletos) {
            total += boleto.calcularPrecioFinal(); // Precio con descuento aplicado (si aplica)
        }
        return total;
    }

    // Retorna la cantidad total de entradas compradas (es decir, la afluencia de personas)
    public int calcularAfluencia() {
        return boletos.size();
    }
}
