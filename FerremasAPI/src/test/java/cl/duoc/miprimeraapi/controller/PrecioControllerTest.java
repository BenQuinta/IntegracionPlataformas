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
}
