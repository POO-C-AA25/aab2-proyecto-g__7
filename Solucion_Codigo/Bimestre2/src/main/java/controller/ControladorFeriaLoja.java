/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Model.Cliente;
import Model.EntradaBase;
import Model.EntradaEspecial;
import Model.EntradaNormal;
import Model.EventoBase;
import Model.EventoEspecial;
import Model.EventoNormal;
import Model.Factura;
import View.VistaConsola;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diego
 */
public class ControladorFeriaLoja {
    public VistaConsola vista = new VistaConsola();
    //para poder interactuar con el usuario
    
    public GestorBoletos gestor = new GestorBoletos();
    
    public String[] eventosEspeciales = {
        "Don Merardo y Sus Players",
        "Gustavo Cerati",
        "Binomio de Oro de America",
        "Tierra Canela",
        "Hombres G"
    };
    public void menu() {
        int opcion;
        do {
            System.out.println("  MENÚ PRINCIPAL FERIA DE LOJA");
            System.out.println("[1] Registrar cliente");
            System.out.println("[2] Comprar boletos");
            System.out.println("[3] Ver factura");
            System.out.println("[4] Ver asistencia por evento");
            System.out.println("[5] Ver ganancias");
            System.out.println("[6] Salir y obtener reporte");
            opcion = vista.leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> {
                    System.out.println("REGISTRO DE CLIENTE");
                    registrarCliente();
                }
                case 2 -> {
                    System.out.println("COMPRA DE BOLETOS");
                    comprarBoletos();
                }
                case 3 -> {
                    System.out.println("FACTURA DEL CLIENTE");
                    mostrarFactura();
                }
                case 4 -> {
                    System.out.println("ASISTENCIA POR EVENTO");
                    asistenciaPorEvento();
                }
                case 5 -> {
                    System.out.println("GANANCIAS DEL DÍA");
                    gananciasDelDia();
                }
                case 6 -> {
                    gestor.generarReporte("reporteFeriaLoja.txt");
                    System.out.println("Gracias por su tiempo");
                }
                default -> {
                    System.out.println("Opción inválida, intente nuevamente");
                }
            }

        } while (opcion != 6);
    }
    public void registrarCliente() {
        String nombre = vista.leerTexto("Ingrese nombre: ");
        String cedula = vista.leerTexto("Ingrese cédula: ");
        gestor.registrarCliente(nombre, cedula);
        System.out.println("Cliente registrado correctamente.");
    }

    public void comprarBoletos() {
        Cliente cliente = gestor.obtenerUltimoCliente();
        if (cliente == null) {
            System.out.println("Debe registrar un cliente antes.");
            return;
        }
        List<EntradaBase> entradas = new ArrayList<>();

        for (String ev : eventosEspeciales) {
            System.out.println("Evento: " + ev); //recorremos y presentamos los ev
        }
        int opcion = vista.leerEntero("1 para entrada normal, 2 para especial (conciertos): ");
        String eventoNombre;
        EventoBase evento;

        switch (opcion) {
            case 1:
                eventoNombre = "Entrada Normal";
                evento = new EventoNormal(eventoNombre, null); // (fecha no definida por ahora)
                int cantidadNormales = vista.leerEntero("Cantidad: ");
                for (int i = 0; i < cantidadNormales; i++) {
                    entradas.add(new EntradaNormal(cliente.getCedula(), evento));
                }
                break;
            case 2:
                eventoNombre = vista.leerTexto("Nombre del evento: ");
                if (!validarEvento(eventoNombre)) {
                    System.out.println("Evento no válido.");
                    return;
                }
                evento = new EventoEspecial(eventoNombre, null); // (fecha no definida por ahora)
                int cantidadEspeciales = vista.leerEntero("Cantidad: ");
                for (int i = 0; i < cantidadEspeciales; i++) {
                    entradas.add(new EntradaEspecial(cliente.getCedula(), evento));
                }
                break;

            
            default:
                System.out.println("Opción inválida.");
                return;
        }

        gestor.generarFactura(cliente, entradas);
        System.out.println("Boletos comprados con éxito."); // y factura generada
    } 

    protected void mostrarFactura() {
        Cliente cliente = gestor.obtenerUltimoCliente();
        if (cliente == null || cliente.getFactura() == null) {
            System.out.println("No hay factura registrada.");
            return;
        }

        Factura fac = cliente.getFactura();
        System.out.println("Factura de: " + cliente.getNombre());

        // Variables auxiliares locales solo para conteo
        int normales = 0;
        int especiales = 0;
        double totalNormales = 0;
        double totalEspeciales = 0;

        // Recorrer las entradas compradas
        for (EntradaBase e : fac.getBoletos()) {
            System.out.printf("Tipo: %s - Evento: %s - Precio: %.2f\n",
                    e.getTipo(), e.getEvento().getNombre(), e.calcularPrecioFinal());

         
            if (e.getTipo().equalsIgnoreCase("Normal")) {
                normales++;
                totalNormales += e.calcularPrecioFinal();
            } else if (e.getTipo().equalsIgnoreCase("Especial")) {
                especiales++;
                totalEspeciales += e.calcularPrecioFinal();
            }
        }

        // Calcular totales y descuentos
        double total = fac.calcularTotal();
        double totalConDescuento = fac.calcularTotalConDescuento();
        double descuento = total - totalConDescuento;
        System.out.printf("Entradas normales: %d - Subtotal: %.2f\n", normales, totalNormales);
        System.out.printf("Entradas especiales: %d - Subtotal: %.2f\n", especiales, totalEspeciales);
        System.out.printf("Descuento aplicado: %.2f\n", descuento);
        System.out.printf("TOTAL A PAGAR: %.2f\n", totalConDescuento);
    }

    public void asistenciaPorEvento() {
        int[] asistencia = gestor.calcularAsistenciaPorEvento(eventosEspeciales);
        for (int i = 0; i < eventosEspeciales.length; i++) {
            System.out.printf("%s: %d personas\n", eventosEspeciales[i], asistencia[i]);
        }
    }
    public void gananciasDelDia() {
        double total = gestor.calcularGanancias();
        System.out.printf("Ganancia total: %.2f\n", total);
    }

    // Valida que el evento ingresado exista en la lista de eventos especiales
    public boolean validarEvento(String nombre) {
        for (String e : eventosEspeciales) {
            if (e.equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }
}
