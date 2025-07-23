/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.util.Scanner;

/**
 *
 * @author Diego
 */
public class VistaConsola {

    Scanner scanner = new Scanner(System.in);

    public int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextInt();
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        scanner.nextLine();
        return scanner.nextLine();
    }

    public double leerDecimal(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextDouble();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
