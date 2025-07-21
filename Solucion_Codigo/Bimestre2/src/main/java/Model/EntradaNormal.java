/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Diego
 */
public class EntradaNormal extends EntradaBase {

    public EntradaNormal(String cedulaCliente, EventoBase evento) {
        super(cedulaCliente, evento, 3.5);
    }

    @Override
    public double calcularPrecioFinal() {
        return precio;
    }

    @Override
    public String getTipo() {
        return "Normal";
    }
}
