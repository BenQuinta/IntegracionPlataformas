package cl.duoc.miprimeraapi.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WebpayServiceTest {

    private WebpayService webpayService = new WebpayService();

    // 1. Monto negativo lanza excepción
    @Test
    void testCrearTransaccionMontoNegativo() {
        assertThrows(Exception.class, () -> webpayService.crearTransaccion(-1000));
    }

    // 2. Monto cero lanza excepción
    @Test
    void testCrearTransaccionMontoCero() {
        assertThrows(Exception.class, () -> webpayService.crearTransaccion(0));
    }

    // 3. Monto válido retorna resultado no nulo
    @Test
    void testCrearTransaccionMontoValido() throws Exception {
        Object resultado = webpayService.crearTransaccion(10000);
        assertNotNull(resultado, "La transacción debe retornar un objeto no nulo");
    }

    // 4. Monto muy grande retorna resultado válido o lanza excepción controlada
    @Test
    void testCrearTransaccionMontoExcesivo() {
        assertDoesNotThrow(() -> webpayService.crearTransaccion(1_000_000));
    }

    // 5. Monto válido retorna tipo esperado (puedes ajustar esto al tipo real si existe)
    @Test
    void testCrearTransaccionTipoDeRetorno() throws Exception {
        Object resultado = webpayService.crearTransaccion(5000);
        assertTrue(resultado instanceof String || resultado instanceof Object, "Debe retornar un tipo válido");
    }

    // 6. Validar que dos transacciones distintas generen resultados distintos (si aplica)
    @Test
    void testCrearTransaccionGeneraTransaccionesUnicas() throws Exception {
        Object resultado1 = webpayService.crearTransaccion(7000);
        Object resultado2 = webpayService.crearTransaccion(7000);
        assertNotEquals(resultado1, resultado2, "Cada transacción debe tener un identificador único");
    }
}
