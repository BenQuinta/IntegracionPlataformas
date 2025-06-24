package cl.duoc.miprimeraapi.service;

import cl.duoc.miprimeraapi.model.Precio;
import cl.duoc.miprimeraapi.model.PrecioConvertidoDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MindicadorServiceTest {

    private MindicadorService mindicadorService = new MindicadorService();

    @Test
    void testConvertirCLPaUSD() {
        Precio precio = new Precio();
        precio.setValor(10000);
        PrecioConvertidoDTO dto = mindicadorService.convertirPrecio(precio, "USD");

        // Este test solo pasa si la API está disponible
        assertNotEquals(-1, dto.getValorConvertido());
    }

    @Test
    void testConvertirCLPaEUR() {
        Precio precio = new Precio();
        precio.setValor(12000);
        PrecioConvertidoDTO dto = mindicadorService.convertirPrecio(precio, "EUR");

        assertNotEquals(-1, dto.getValorConvertido());
    }

    @Test
    void testMonedaInvalida() {
        Precio precio = new Precio();
        precio.setValor(15000);
        PrecioConvertidoDTO dto = mindicadorService.convertirPrecio(precio, "ABC");

        assertEquals(15000, dto.getValorConvertido());  // sin conversión
        assertEquals("CLP", dto.getMonedaDestino());
    }

    @Test
    void testValorOriginalConservado() {
        Precio precio = new Precio();
        precio.setValor(9999);
        PrecioConvertidoDTO dto = mindicadorService.convertirPrecio(precio, "EUR");

        assertEquals(9999, dto.getValorOriginal());
    }
    @Test
    void testValorConvertidoUSDNoEsNegativo() {
        Precio precio = new Precio();
        precio.setValor(10000);

        PrecioConvertidoDTO dto = mindicadorService.convertirPrecio(precio, "USD");

        assertTrue(dto.getValorConvertido() > 0, "El valor convertido debe ser mayor que cero");
        assertEquals("USD", dto.getMonedaDestino());
    }

    @Test
    void testErrorPorMonedaInexistente() {
        Precio precio = new Precio();
        precio.setValor(5000);

        PrecioConvertidoDTO dto = mindicadorService.convertirPrecio(precio, "BITCOIN");

        // El servicio debe manejar esta moneda inválida retornando el valor original sin conversión
        assertEquals(5000, dto.getValorConvertido());
        assertEquals("CLP", dto.getMonedaDestino());
    }
}
