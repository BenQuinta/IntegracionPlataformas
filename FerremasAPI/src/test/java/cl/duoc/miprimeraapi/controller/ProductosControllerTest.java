package cl.duoc.miprimeraapi.controller;

import cl.duoc.miprimeraapi.model.Productos;
import cl.duoc.miprimeraapi.repository.ProductosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductosControllerTest {

    private ProductosRepository productosRepository;
    private ProductosController controller;

    @BeforeEach
    void setUp() throws Exception {
        productosRepository = mock(ProductosRepository.class);
        controller = new ProductosController();

        Field field = ProductosController.class.getDeclaredField("productosRepository");
        field.setAccessible(true);
        field.set(controller, productosRepository);
    }

    @Test
    void testObtenerProductoPorIdExistente() {
        Productos producto = new Productos();
        producto.setId(1L);

        when(productosRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Productos> resultado = controller.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(productosRepository).findById(1L);
    }

    @Test
    void testObtenerProductoPorIdInexistente() {
        when(productosRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Productos> resultado = controller.obtenerPorId(2L);

        assertTrue(resultado.isEmpty());
        verify(productosRepository).findById(2L);
    }
}