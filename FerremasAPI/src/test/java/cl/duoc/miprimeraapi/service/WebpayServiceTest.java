package cl.duoc.miprimeraapi.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WebpayServiceTest {

    private WebpayService webpayService = new WebpayService();

    @Test
    void testCrearTransaccionInvalida() {
        assertThrows(Exception.class, () -> webpayService.crearTransaccion(-1000));
    }
}