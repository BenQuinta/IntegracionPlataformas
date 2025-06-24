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
        assertNotEquals(-1, dto.getValorConvertido());
    }
}