package cl.duoc.miprimeraapi.controller;

import cl.duoc.miprimeraapi.model.Sucursales;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SucursalesControllerTest {

    private SucursalesController controller;

    @BeforeEach
    void setUp() {
        controller = new SucursalesController();
    }

    // 1. Obtener sucursal existente (id=1) - precargada en constructor
    @Test
    void testGetSucursalPorIdExistente() {
        ResponseEntity<Sucursales> response = controller.getSucursales(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Sucursal Central", response.getBody().getNombreSucursal());
    }

    // 2. Obtener sucursal inexistente (id=99)
    @Test
    void testGetSucursalPorIdInexistente() {
        ResponseEntity<Sucursales> response = controller.getSucursales(99L);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    // 3. Crear nueva sucursal con POST
    @Test
    void testPostSucursales() {
        Sucursales nueva = new Sucursales();
        nueva.setNombreSucursal("Sucursal Nueva");
        nueva.setDireccion("Calle Falsa 123");

        ResponseEntity<Sucursales> response = controller.postSucursales(nueva);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Sucursal Nueva", response.getBody().getNombreSucursal());
        assertEquals("Calle Falsa 123", response.getBody().getDireccion());
        assertNotNull(response.getBody().getId());
    }

    // 4. Obtener todas las sucursales con GET (debería contener al menos la precargada)
    @Test
    void testGetTodosSucursales() {
        ResponseEntity<List<Sucursales>> response = controller.getTodosSucursales();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        // Ya hay 1 sucursal precargada
        assertTrue(response.getBody().stream()
                .anyMatch(s -> "Sucursal Central".equals(s.getNombreSucursal())));
    }

    // 5. Actualizar sucursal con PUT (id=1)
    @Test
    void testPutSucursalesExistente() {
        Sucursales update = new Sucursales();
        update.setNombreSucursal("Sucursal Actualizada");
        update.setDireccion("Av. Actualizada 100");

        ResponseEntity<Sucursales> response = controller.putSucursales(1L, update);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sucursal Actualizada", response.getBody().getNombreSucursal());
        assertEquals("Av. Actualizada 100", response.getBody().getDireccion());
    }

    // 6. Eliminar sucursal (id=1)
    @Test
    void testDeleteSucursalesExistente() {
        ResponseEntity<Void> response = controller.deleteSucursales(1L);
        assertEquals(204, response.getStatusCodeValue());

        // Verificar que ya no existe
        ResponseEntity<Sucursales> responseGet = controller.getSucursales(1L);
        assertEquals(404, responseGet.getStatusCodeValue());
    }
}
