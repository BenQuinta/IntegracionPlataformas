package cl.duoc.miprimeraapi.controller;

import cl.duoc.miprimeraapi.model.Precio;
import cl.duoc.miprimeraapi.model.PrecioConvertidoDTO;
import cl.duoc.miprimeraapi.repository.PrecioRepository;
import cl.duoc.miprimeraapi.repository.ProductosRepository;
import cl.duoc.miprimeraapi.service.MindicadorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PrecioControllerTest {

    private PrecioRepository precioRepository;
    private ProductosRepository productosRepository;
    private MindicadorService mindicadorService;
    private PrecioController controller;

    @BeforeEach
    void setUp() throws Exception {
        precioRepository = mock(PrecioRepository.class);
        productosRepository = mock(ProductosRepository.class);
        mindicadorService = mock(MindicadorService.class);

        controller = new PrecioController();

        Field field1 = PrecioController.class.getDeclaredField("precioRepository");
        field1.setAccessible(true);
        field1.set(controller, precioRepository);

        Field field2 = PrecioController.class.getDeclaredField("productosRepository");
        field2.setAccessible(true);
        field2.set(controller, productosRepository);

        Field field3 = PrecioController.class.getDeclaredField("mindicadorService");
        field3.setAccessible(true);
        field3.set(controller, mindicadorService);
    }

    @Test
    void testConvertirPrecio() {
        Precio precio = new Precio();
        precio.setValor(10000);

        when(precioRepository.findById(1L)).thenReturn(Optional.of(precio));
        when(mindicadorService.convertirPrecio(precio, "USD"))
                .thenReturn(new PrecioConvertidoDTO(10000, 12.5, "USD"));

        PrecioConvertidoDTO result = controller.convertirPrecio(1L, "USD");

        assertEquals("USD", result.getMonedaDestino());
        assertEquals(12.5, result.getValorConvertido());
    }
    @Test
    void testConvertirPrecioMonedaEUR() {
        Precio precio = new Precio();
        precio.setValor(15000);

        when(precioRepository.findById(2L)).thenReturn(Optional.of(precio));
        when(mindicadorService.convertirPrecio(precio, "EUR"))
                .thenReturn(new PrecioConvertidoDTO(15000, 16.0, "EUR"));

        PrecioConvertidoDTO result = controller.convertirPrecio(2L, "EUR");

        assertEquals("EUR", result.getMonedaDestino());
        assertEquals(16.0, result.getValorConvertido());
    }
    @Test
    void testConvertirPrecioProductoNoExiste() {
        when(precioRepository.findById(999L)).thenReturn(Optional.empty());

        PrecioConvertidoDTO result = controller.convertirPrecio(999L, "USD");

        assertEquals(-1, result.getValorConvertido());
        assertEquals("CLP", result.getMonedaDestino());
    }
    @Test
    void testConvertirPrecioConValorCero() {
        Precio precio = new Precio();
        precio.setValor(0);

        when(precioRepository.findById(3L)).thenReturn(Optional.of(precio));
        when(mindicadorService.convertirPrecio(precio, "USD"))
                .thenReturn(new PrecioConvertidoDTO(0, 0, "USD"));

        PrecioConvertidoDTO result = controller.convertirPrecio(3L, "USD");

        assertEquals(0, result.getValorConvertido());
        assertEquals("USD", result.getMonedaDestino());
    }
    @Test
    void testConvertirPrecioConMonedaInvalida() {
        Precio precio = new Precio();
        precio.setValor(10000);

        when(precioRepository.findById(4L)).thenReturn(Optional.of(precio));
        when(mindicadorService.convertirPrecio(precio, "XYZ"))
                .thenReturn(new PrecioConvertidoDTO(10000, 10000, "CLP"));

        PrecioConvertidoDTO result = controller.convertirPrecio(4L, "XYZ");

        assertEquals("CLP", result.getMonedaDestino());
        assertEquals(10000, result.getValorConvertido());
    }

    @Test
    void testConvertirPrecioServicioRetornaError() {
        Precio precio = new Precio();
        precio.setValor(8000);

        when(precioRepository.findById(5L)).thenReturn(Optional.of(precio));
        when(mindicadorService.convertirPrecio(precio, "USD"))
                .thenReturn(new PrecioConvertidoDTO(8000, -1, "USD"));

        PrecioConvertidoDTO result = controller.convertirPrecio(5L, "USD");

        assertEquals(-1, result.getValorConvertido());
        assertEquals("USD", result.getMonedaDestino());
    }

    @Test
    void testConvertirPrecioConDecimales() {
        Precio precio = new Precio();
        precio.setValor(12345.67);

        when(precioRepository.findById(6L)).thenReturn(Optional.of(precio));
        when(mindicadorService.convertirPrecio(precio, "USD"))
                .thenReturn(new PrecioConvertidoDTO(12345.67, 13.27, "USD"));

        PrecioConvertidoDTO result = controller.convertirPrecio(6L, "USD");

        assertEquals(13.27, result.getValorConvertido());
        assertEquals("USD", result.getMonedaDestino());
    }
}
