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
public abstract class EventoBase {

    protected String nombre;
    protected LocalDate fecha;

    public EventoBase(String nombre, LocalDate fecha) {
        this.nombre = nombre;
        this.fecha = fecha;

    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public abstract boolean esEspecial();
}
