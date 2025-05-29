package cl.duoc.miprimeraapi.model;

import java.time.LocalDateTime;
public class Precio {
    private LocalDateTime fecha;
    private double valor;

    public Precio() {
    }
    public Precio(LocalDateTime fecha, double valor) {
        this.fecha = fecha;
        this.valor = valor;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
}

