/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;


/**
 *
 * @author Diego
 */
public class EventoNormal extends EventoBase {

    public EventoNormal(String nombre, LocalDate fecha) {
        super(nombre, fecha);
    }

    @Override
    public boolean esEspecial() {
        return false;
    }
}