package cl.duoc.miprimeraapi.controller;

import cl.duoc.miprimeraapi.model.Productos;
import cl.duoc.miprimeraapi.repository.ProductosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

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
        producto.setNombre("Martillo");

        when(productosRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Productos> resultado = controller.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        assertEquals("Martillo", resultado.get().getNombre());
    }

    @Test
    void testObtenerTodosLosProductos() {
        Productos p1 = new Productos();
        p1.setId(1L);
        p1.setNombre("Martillo");

        Productos p2 = new Productos();
        p2.setId(2L);
        p2.setNombre("Taladro");

        List<Productos> productos = Arrays.asList(p1, p2);

        when(productosRepository.findAll()).thenReturn(productos);

        List<Productos> resultado = controller.obtenerTodos();

        assertEquals(2, resultado.size());
        assertEquals("Martillo", resultado.get(0).getNombre());
        assertEquals("Taladro", resultado.get(1).getNombre());
    }

    @Test
    void testCrearProducto() {
        Productos nuevo = new Productos();
        nuevo.setNombre("Sierra");

        Productos guardado = new Productos();
        guardado.setId(3L);
        guardado.setNombre("Sierra");

        when(productosRepository.save(nuevo)).thenReturn(guardado);

        Productos resultado = controller.crearProducto(nuevo);

        assertNotNull(resultado);
        assertEquals(3L, resultado.getId());
        assertEquals("Sierra", resultado.getNombre());
    }

    @Test
    void testEliminarProducto() {
        doNothing().when(productosRepository).deleteById(4L);

        assertDoesNotThrow(() -> controller.eliminarProducto(4L));
        verify(productosRepository).deleteById(4L);
    }

    @Test
    void testBuscarPorCategoria() {
        Productos producto = new Productos();
        producto.setNombre("Martillo");
        producto.setCategoria("Herramientas");

        List<Productos> lista = Collections.singletonList(producto);

        when(productosRepository.findByCategoria("Herramientas")).thenReturn(lista);

        List<Productos> resultado = controller.obtenerPorCategoria("Herramientas");

        assertEquals(1, resultado.size());
        assertEquals("Martillo", resultado.get(0).getNombre());
    }

    @Test
    void testBuscarPorNombre() {
        Productos producto = new Productos();
        producto.setNombre("Taladro Eléctrico");

        List<Productos> lista = Collections.singletonList(producto);

        when(productosRepository.findByNombreContainingIgnoreCase("taladro")).thenReturn(lista);

        List<Productos> resultado = controller.buscarPorNombre("taladro");

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getNombre().toLowerCase().contains("taladro"));
    }
}
