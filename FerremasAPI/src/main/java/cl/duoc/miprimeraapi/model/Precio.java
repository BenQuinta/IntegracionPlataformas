package cl.duoc.miprimeraapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Precio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double valor;
    private LocalDateTime fecha;

    public Precio() {
    }

    public Precio(double valor, LocalDateTime fecha) {
        this.valor = valor;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
