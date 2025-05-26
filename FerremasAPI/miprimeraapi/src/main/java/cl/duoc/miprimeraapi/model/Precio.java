package main.java.cl.duoc.miprimeraapi.model;

import java.time.localDateTime;
public class Precio {
    private localDateTime fecha;
    private double valor;

    public Precio() {
    }
    public Precio(localDateTime fecha, double valor) {
        this.fecha = fecha;
        this.valor = valor;
    }
    public localDateTime getFecha() {
        return fecha;
    }
    public void setFecha(localDateTime fecha) {
        this.fecha = fecha;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
}

