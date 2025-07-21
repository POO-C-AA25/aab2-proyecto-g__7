/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Diego
 */
public class EntradaEspecial extends EntradaBase {

    public EntradaEspecial(String cedulaCliente, EventoBase evento) {
        super(cedulaCliente, evento, 8.0);
    }

    @Override
    public double calcularPrecioFinal() {
        return precio * 0.85; // 15% de descuento
    }

    @Override
    public String getTipo() {
        return "Especial";
    }
}
