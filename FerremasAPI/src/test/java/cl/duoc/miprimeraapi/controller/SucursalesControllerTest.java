package cl.duoc.miprimeraapi.controller;

import cl.duoc.miprimeraapi.model.Sucursales;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class SucursalesControllerTest {

    @Test
    void testGetSucursalPorDefecto() {
        SucursalesController controller = new SucursalesController();
        ResponseEntity<Sucursales> response = controller.getSucursales(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sucursal Central", response.getBody().getNombreSucursal());
    }
}
