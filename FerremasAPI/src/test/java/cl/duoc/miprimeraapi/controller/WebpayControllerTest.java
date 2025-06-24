package cl.duoc.miprimeraapi.controller;

import cl.duoc.miprimeraapi.service.WebpayService;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.net.URI;
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

    // Test 1: Pagar con monto inválido (no numérico)
    @Test
    void testPagarErrorPorMonto() {
        Map<String, Object> body = Map.of("monto", "abc");
        ResponseEntity<?> response = controller.pagar(body);
        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("error"));
    }

    // Test 2: Pagar exitoso
    @Test
    void testPagarExitoso() throws Exception {
        Map<String, Object> body = Map.of("monto", 15000.0);

        WebpayPlusTransactionCreateResponse mockResponse = mock(WebpayPlusTransactionCreateResponse.class);
        when(mockResponse.getToken()).thenReturn("token123");
        when(mockResponse.getUrl()).thenReturn("https://webpay.cl/pago");

        when(webpayService.crearTransaccion(15000.0)).thenReturn(mockResponse);

        ResponseEntity<?> response = controller.pagar(body);

        assertEquals(200, response.getStatusCodeValue());
        Map<String, Object> result = (Map<String, Object>) response.getBody();
        assertEquals("token123", result.get("token"));
        assertEquals("https://webpay.cl/pago", result.get("url"));
    }

    // Test 3: Pagar con excepción en servicio
    @Test
    void testPagarServicioException() throws Exception {
        Map<String, Object> body = Map.of("monto", 1000.0);

        when(webpayService.crearTransaccion(1000.0)).thenThrow(new RuntimeException("Error de servicio"));

        ResponseEntity<?> response = controller.pagar(body);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Error de servicio"));
    }

    // Test 4: Confirmación exitosa con redirección 302
    @Test
    void testConfirmacionExitosa() throws Exception {
        WebpayPlusTransactionCommitResponse mockResponse = mock(WebpayPlusTransactionCommitResponse.class);
        when(mockResponse.getBuyOrder()).thenReturn("ORD001");
        when(mockResponse.getAmount()).thenReturn(30000.0);

        when(webpayService.confirmarTransaccion("tokenValido")).thenReturn(mockResponse);

        ResponseEntity<?> response = controller.confirmacion("tokenValido");

        assertEquals(302, response.getStatusCodeValue());
        URI location = response.getHeaders().getLocation();
        assertNotNull(location);
        String locString = location.toString();
        assertTrue(locString.contains("orden=ORD001"));
        assertTrue(locString.contains("monto=30000"));
    }

    // Test 5: Confirmación con token inválido (error 400)
    @Test
    void testConfirmacionTokenInvalido() throws Exception {
        when(webpayService.confirmarTransaccion("tokenInvalido"))
                .thenThrow(new RuntimeException("Token inválido"));

        ResponseEntity<?> response = controller.confirmacion("tokenInvalido");

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Token inválido"));
    }

    // Test 6: Confirmación con buyOrder null (redirige con orden=null)
    @Test
    void testConfirmacionBuyOrderNull() throws Exception {
        WebpayPlusTransactionCommitResponse mockResponse = mock(WebpayPlusTransactionCommitResponse.class);
        when(mockResponse.getBuyOrder()).thenReturn(null);
        when(mockResponse.getAmount()).thenReturn(0.0);

        when(webpayService.confirmarTransaccion("tokenConBuyOrderNull")).thenReturn(mockResponse);

        ResponseEntity<?> response = controller.confirmacion("tokenConBuyOrderNull");

        assertEquals(302, response.getStatusCodeValue());
        URI location = response.getHeaders().getLocation();
        assertNotNull(location);
        assertTrue(location.toString().contains("orden=null"));
    }
}
