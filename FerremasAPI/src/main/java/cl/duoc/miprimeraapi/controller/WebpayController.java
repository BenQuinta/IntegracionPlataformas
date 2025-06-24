package cl.duoc.miprimeraapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.miprimeraapi.service.WebpayService;

@RestController
@RequestMapping("/webpay")
public class WebpayController {
    @Autowired
    private WebpayService webpayService;
    
    @PostMapping("/pagar")
    public ResponseEntity<?> pagar(@RequestBody Map<String, Object> body) {
        try {
            double monto = Double.parseDouble(body.get("monto").toString());
            var response = webpayService.crearTransaccion(monto);
            Map<String, Object> result = new HashMap<>();
            result.put("token", response.getToken());
            result.put("url", response.getUrl());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", e.getMessage()));
        }
    }
    @RequestMapping(value = "/confirmacion", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmacion(@RequestParam("token_ws") String token) {
    try {
        var response = webpayService.confirmarTransaccion(token);

        String redirectUrl = String.format(
            "/pago-exitoso.html?orden=%s&monto=%.0f",
            response.getBuyOrder(),
            response.getAmount()
        );

        return ResponseEntity.status(HttpStatus.FOUND)
                             .header("Location", redirectUrl)
                             .build();
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(Map.of("error", e.getMessage()));
    }
    }

}
