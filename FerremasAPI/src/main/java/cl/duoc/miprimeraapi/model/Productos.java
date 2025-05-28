package cl.duoc.miprimeraapi.model;

import java.util.List;

public class Productos {
    
    private Long id;
    private String nombre;
    private String marca;
    private Integer stock;
    private String modelo;
    private String categoria;
    private List<Precio> precio;

    public Productos() {
    }
    public Productos(Long id, String nombre, String marca, Integer stock, String modelo, String categoria,List<Precio> precio) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.stock = stock;
        this.modelo = modelo;
        this.categoria = categoria;
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
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public List<Precio> getPrecio() {
        return precio;
    }
    public void setPrecio(List<Precio> precio) {
        this.precio = precio;
        
    }
}
