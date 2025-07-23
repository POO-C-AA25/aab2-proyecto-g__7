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
    public GestorBoletos gestor = new GestorBoletos();

    public String[] eventosEspeciales = {
        "Don Medardo y Sus Players",
        "Gustavo Cerati",
        "Binomio de Oro de America",
        "Tierra Canela",
        "Hombres G"
    };

    public void menu() {
        int opcion;
        do {
            vista.mostrarMensaje("  MENÚ PRINCIPAL FERIA DE LOJA");
            vista.mostrarMensaje("[1] Registrar cliente");
            vista.mostrarMensaje("[2] Comprar boletos");
            vista.mostrarMensaje("[3] Ver factura");
            vista.mostrarMensaje("[4] Ver asistencia por evento");
            vista.mostrarMensaje("[5] Ver ganancias");
            vista.mostrarMensaje("[6] Salir y obtener reporte");
            opcion = vista.leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> {
                    vista.mostrarMensaje("REGISTRO DE CLIENTE");
                    registrarCliente();
                }
                case 2 -> {
                    vista.mostrarMensaje("COMPRA DE BOLETOS");
                    comprarBoletos();
                }
                case 3 -> {
                    vista.mostrarMensaje("FACTURA DEL CLIENTE");
                    mostrarFactura();
                }
                case 4 -> {
                    vista.mostrarMensaje("ASISTENCIA POR EVENTO");
                    asistenciaPorEvento();
                }
                case 5 -> {
                    vista.mostrarMensaje("GANANCIAS DEL DÍA");
                    gananciasDelDia();
                }
                case 6 -> {
                    gestor.generarReporte("reporteFeriaLoja.txt");
                    vista.mostrarMensaje("Gracias por su tiempo");
                }
                default -> {
                    vista.mostrarMensaje("Opción inválida, intente nuevamente");
                }
            }

        } while (opcion != 6);
    }

    public void registrarCliente() {
        String nombre = vista.leerTexto("Ingrese nombre: ");
        String cedula = vista.leerTexto("Ingrese cédula: ");
        gestor.registrarCliente(nombre, cedula);
        vista.mostrarMensaje("Cliente registrado correctamente.");
    }

    public void comprarBoletos() {
        Cliente cliente = gestor.obtenerUltimoCliente();
        if (cliente == null) {
            vista.mostrarMensaje("Debe registrar un cliente antes.");
            return;
        }
        List<EntradaBase> entradas = new ArrayList<>();

        for (String ev : eventosEspeciales) {
            vista.mostrarMensaje("Evento: " + ev); //recorremos y presentamos los ev
        }
        int opcion = vista.leerEntero("1 para entrada normal, "
                + "2 para especial (conciertos): ");

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
                    vista.mostrarMensaje("Evento no válido.");
                    return;
                }
                evento = new EventoEspecial(eventoNombre, null); // (fecha no definida por ahora)
                int cantidadEspeciales = vista.leerEntero("Cantidad: ");
                for (int i = 0; i < cantidadEspeciales; i++) {
                    entradas.add(new EntradaEspecial(cliente.getCedula(), evento));
                }
                break;

            default:
                vista.mostrarMensaje("Opción inválida.");
                return;
        }

        gestor.generarFactura(cliente, entradas);
        vista.mostrarMensaje("Boletos comprados con éxito."); // y factura generada
    }

    protected void mostrarFactura() {
        Cliente cliente = gestor.obtenerUltimoCliente();
        if (cliente == null || cliente.getFactura() == null) {
            vista.mostrarMensaje("No hay factura registrada.");
            return;
        }

        Factura fac = cliente.getFactura();
        vista.mostrarMensaje("Factura de: " + cliente.getNombre());

        // Variables auxiliares locales solo para conteo
        int normales = 0;
        int especiales = 0;
        double totalNormales = 0;
        double totalEspeciales = 0;

        // Recorrer las entradas compradas
        for (EntradaBase e : fac.getBoletos()) {
            vista.mostrarMensaje(String.format("Tipo: %s - Evento:"
                    + " %s - Precio: %.2f\n",
                    e.getTipo(), e.getEvento().getNombre(), e.calcularPrecioFinal()));

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
        vista.mostrarMensaje(String.format("Entradas normales: %d - Subtotal: %.2f\n", normales, totalNormales));
        vista.mostrarMensaje(String.format("Entradas especiales: %d - Subtotal: %.2f", especiales, totalEspeciales));
        vista.mostrarMensaje(String.format("Descuento aplicado: %.2f", descuento));
        vista.mostrarMensaje(String.format("TOTAL A PAGAR: %.2f", totalConDescuento));
    }

    public void asistenciaPorEvento() {
        int[] asistencia = gestor.calcularAsistenciaPorEvento(eventosEspeciales);
        for (int i = 0; i < eventosEspeciales.length; i++) {
            vista.mostrarMensaje(String.format("%s: %d personas", eventosEspeciales[i], asistencia[i]));
        }
    }

    public void gananciasDelDia() {
        double total = gestor.calcularGanancias();
        vista.mostrarMensaje(String.format("Ganancia total: %.2f", total));
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
