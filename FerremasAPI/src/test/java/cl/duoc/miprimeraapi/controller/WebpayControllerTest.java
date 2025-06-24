package cl.duoc.miprimeraapi.controller;

import cl.duoc.miprimeraapi.service.WebpayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WebpayControllerTest {

    private WebpayService webpayService;
    private WebpayController controller;

    @BeforeEach
    void setUp() throws Exception {
        webpayService = mock(WebpayService.class);
        controller = new WebpayController();

        Field field = WebpayController.class.getDeclaredField("webpayService");
        field.setAccessible(true);
        field.set(controller, webpayService);
    }

    @Test
    void testPagarErrorPorMonto() {
        Map<String, Object> body = Map.of("monto", "abc");
        ResponseEntity<?> response = controller.pagar(body);
        assertEquals(500, response.getStatusCodeValue());
    }
}