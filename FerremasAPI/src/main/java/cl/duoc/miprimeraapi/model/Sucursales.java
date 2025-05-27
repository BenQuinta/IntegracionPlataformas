package cl.duoc.miprimeraapi.model;

public class Sucursales {
    private Long id;
    private String nombreSucursal;
    private String direccion;

    public Sucursales() {
    }
    public Sucursales(Long id, String nombreSucursal, String direccion) {
        this.id = id;
        this.nombreSucursal = nombreSucursal;
        this.direccion = direccion;
    }
    public Long getId() {
        return id;
    }
    public String getNombreSucursal() {
        return nombreSucursal;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
