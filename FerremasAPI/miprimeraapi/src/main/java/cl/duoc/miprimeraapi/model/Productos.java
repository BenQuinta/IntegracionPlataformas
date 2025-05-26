package cl.duoc.miprimeraapi.model;

import java.util.List;

public class Productos {
    
    private Long id;
    private String nombre;
    private String marca;
    private int stock;
    private String modelo;
    private List<Producto> precio;

    public Productos() {
    }
    public Productos(Long id, String nombre, String marca, int stock, String modelo, List<Producto> precio) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.stock = stock;
        this.modelo = modelo;
        this.precio = precio;
    }
    public Long getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getMarca() {
        return marca;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public List<Producto> getPrecio() {
        return precio;
    }
    public void setPrecio(List<Producto> precio) {
        this.precio = precio;
        
    }
}
