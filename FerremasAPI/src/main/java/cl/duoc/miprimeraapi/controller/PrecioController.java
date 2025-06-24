package cl.duoc.miprimeraapi.controller;

import cl.duoc.miprimeraapi.model.Precio;
import cl.duoc.miprimeraapi.model.PrecioConvertidoDTO;
import cl.duoc.miprimeraapi.repository.ProductosRepository;
import cl.duoc.miprimeraapi.service.MindicadorService;
import cl.duoc.miprimeraapi.repository.PrecioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/precios")
public class PrecioController {

    @Autowired
    private MindicadorService mindicadorService;

    @GetMapping("/convertir/{id}/{moneda}")
public PrecioConvertidoDTO convertirPrecio(@PathVariable Long id, @PathVariable String moneda) {
    Precio precio = precioRepository.findById(id).orElseThrow();
    return mindicadorService.convertirPrecio(precio, moneda);
}

    @Autowired
    private PrecioRepository precioRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @GetMapping("/fecha/{fecha}")
    public List<Precio> buscarPorFecha(@PathVariable String fecha) {
        return precioRepository.findByFecha(LocalDateTime.parse(fecha));
    }

    @GetMapping("/producto/{codigoProducto}")
    public List<Precio> buscarPorProducto(@PathVariable String codigoProducto) {
        return productosRepository.findByCodigoProducto(codigoProducto)
                .stream().findFirst().map(p -> p.getPrecios()).orElse(List.of());
    }

}
