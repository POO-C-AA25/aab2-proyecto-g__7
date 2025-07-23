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
    /* En cualquier clase hija de entrada, se cumple el principio L porque puede reemplazar 
    a entradaBase sin romper nada del codigo
    EntradaNormal hereda de EntradaBase. Implementa todos los métodos abstractos correctamente: 
    calcularPrecioFinal() y getTipo(). No cambia el contrato, no rompe nada que el código 
    cliente espere de una EntradaBase.
    */
}
