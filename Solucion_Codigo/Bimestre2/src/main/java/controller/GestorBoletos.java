/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Model.Cliente;
import Model.EntradaBase;
import Model.Factura;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diego
 */
public class GestorBoletos {

    // Lista que almacena todos los clientes registrados durante la feria
    public List<Cliente> clientes;

    // Constructor que inicializa la lista de clientes como vacía
    public GestorBoletos() {
        this.clientes = new ArrayList<>();
    }

    // Método que registra un nuevo cliente, lo agrega a la lista y lo devuelve
    public Cliente registrarCliente(String nombre, String cedula) {
        Cliente cliente = new Cliente(nombre, cedula);
        clientes.add(cliente);
        return cliente;
    }

    // Devuelve el último cliente registrado, o null si la lista está vacía
    public Cliente obtenerUltimoCliente() {
        if (clientes.isEmpty()) {
            return null;
        }
        // Se accede al último elemento con tamaño - 1
        return clientes.get(clientes.size() - 1);
    }

    // Devuelve la lista completa de clientes (por si se necesita fuera del gestor)
    public List<Cliente> getClientes() {
        return clientes;
    }

    // Genera una factura para un cliente si ya tenemos,
    //la reutilizamos y le agrega las nuevas entradas
    public void generarFactura(Cliente cliente, List<EntradaBase> entradas) {
        Factura factura = cliente.getFactura();

        if (factura == null) {
            factura = new Factura(cliente.getNombre());
        }

        // Se agregan las entradas a la factura
        for (EntradaBase entrada : entradas) {
            factura.agregarBoleto(entrada);
        }

        // Se actualiza la factura en el cliente
        cliente.setFactura(factura);
    }

    // Calcula la ganancia total sumando el total con descuento de cada factura
    public double calcularGanancias() {
        double total = 0;
        for (Cliente cliente : clientes) {
            if (cliente.getFactura() != null) {
                total += cliente.getFactura().calcularTotalConDescuento();
            }
        }
        return total;
    }


    public int[] calcularAsistenciaPorEvento(String[] listaEventos) {
        // Se basa en las facturas de todos los clientes
      
  
        int[] asistencia = new int[listaEventos.length];
          // Se crea un arreglo llamado 'asistencia' para contar cuántas personas fueron a cada evento.
        for (Cliente cliente : clientes) {

            // Verificamos si ese cliente tiene una factura
            if (cliente.getFactura() != null) {

               
                for (EntradaBase entrada : cliente.getFactura().getBoletos()) {
                     // Recorremos todos los boletos comprados por el cliente
                    String nombreEvento = entrada.getEvento().getNombre();
                     // Obtenemos el nombre del evento al que corresponde ese boleto
                    for (int i = 0; i < listaEventos.length; i++) {
                        if (listaEventos[i].equalsIgnoreCase(nombreEvento)) {
                            asistencia[i]++;
                        }
                    }
                }
            }
        }

        return asistencia;
    }

    public void generarReporte(String reporteFeriaLoja) {
        int totalClientes = clientes.size();
        int totalNormales = 0;
        int totalEspeciales = 0;
        int totalEntradas = 0;
        double totalRecaudado = 0;

        // Se recorren todos los clientes para sumar estadísticas
        for (Cliente cliente : clientes) {
            Factura f = cliente.getFactura();
            if (f != null) {
                for (EntradaBase entrada : f.getBoletos()) {
                    if (entrada.getTipo().equalsIgnoreCase("Normal")) {
                        totalNormales++;
                    } else 
                        if (entrada.getTipo().equalsIgnoreCase("Especial")) {
                        totalEspeciales++;
                    }
                }
                totalRecaudado += f.calcularTotalConDescuento();
            }
        }

        totalEntradas = totalNormales + totalEspeciales;

        String reporte = ("REPORTE FERIA DE LOJA\n"
                + "--------------------------------------\n"
                + "Total de clientes: " + totalClientes + "\n"
                + "Entradas normales vendidas: " + totalNormales + "\n"
                + "Entradas especiales vendidas: " + totalEspeciales + "\n"
                + "Total de entradas vendidas: " + totalEntradas + "\n"
                + "Recaudación final: $" + String.format("%.2f", totalRecaudado) + "\n");

        try (PrintWriter escritor = new PrintWriter(new FileWriter(reporteFeriaLoja))) {
            escritor.println(reporte);
            System.out.println("Reporte generado en el archivo " + reporteFeriaLoja);
        } catch (IOException e) {
            System.out.println("Error al generar el reporte" + e.getMessage());
        }
    }
}
