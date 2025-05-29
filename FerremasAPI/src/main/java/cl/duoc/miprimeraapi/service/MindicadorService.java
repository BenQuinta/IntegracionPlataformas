package cl.duoc.miprimeraapi.service;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.duoc.miprimeraapi.model.Precio;
import cl.duoc.miprimeraapi.model.PrecioConvertidoDTO;

import java.util.List;
import java.util.Map;

@Service
public class MindicadorService {

    private final RestTemplate restTemplate = new RestTemplate();

    public double getValorDolar() {
        String url = "https://mindicador.cl/api/dolar";
        ResponseEntity<Map> respuesta = restTemplate.getForEntity(url, Map.class);
        List<Map<String, Object>> series = (List<Map<String, Object>>) respuesta.getBody().get("serie");
        Map<String, Object> serie = series.get(0);
        return Double.parseDouble(serie.get("valor").toString());
    }

    public double getValorEuro() {
        String url = "https://mindicador.cl/api/euro";
        ResponseEntity<Map> respuesta = restTemplate.getForEntity(url, Map.class);
        List<Map<String, Object>> series = (List<Map<String, Object>>) respuesta.getBody().get("serie");
        Map<String, Object> serie = series.get(0);
        return Double.parseDouble(serie.get("valor").toString());
    }
    public PrecioConvertidoDTO convertirPrecio(Precio precio, String moneda) {
        double valorCLP = precio.getValor();
        double valorConvertido = valorCLP;
        String monedaDestino = "CLP";
    
        try {
            if ("USD".equalsIgnoreCase(moneda)) {
                double dolar = getValorDolar();
                valorConvertido = valorCLP / dolar;
                monedaDestino = "USD";
            } else if ("EUR".equalsIgnoreCase(moneda)) {
                double euro = getValorEuro();
                valorConvertido = valorCLP / euro;
                monedaDestino = "EUR";
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el valor de la moneda: " + e.getMessage());
            valorConvertido = -1; // o puedes lanzar una excepción personalizada
        }
    
        return new PrecioConvertidoDTO(valorCLP, valorConvertido, monedaDestino);
    }
    

    
    
    
}
