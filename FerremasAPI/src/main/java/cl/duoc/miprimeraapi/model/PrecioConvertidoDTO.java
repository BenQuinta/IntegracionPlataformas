package cl.duoc.miprimeraapi.model;

public class PrecioConvertidoDTO {
    private double valorOriginal;
    private double valorConvertido;
    private String monedaDestino;

    
    public PrecioConvertidoDTO() {
    }

    
    public PrecioConvertidoDTO(double valorOriginal, double valorConvertido, String monedaDestino) {
        this.valorOriginal = valorOriginal;
        this.valorConvertido = valorConvertido;
        this.monedaDestino = monedaDestino;
    }

    
    public double getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(double valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public double getValorConvertido() {
        return valorConvertido;
    }

    public void setValorConvertido(double valorConvertido) {
        this.valorConvertido = valorConvertido;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public void setMonedaDestino(String monedaDestino) {
        this.monedaDestino = monedaDestino;
    }
}
