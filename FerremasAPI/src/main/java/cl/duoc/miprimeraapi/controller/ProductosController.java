package cl.duoc.miprimeraapi.controller;


import cl.duoc.miprimeraapi.model.Productos;
import cl.duoc.miprimeraapi.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductosController {

    


    @Autowired
    private ProductosRepository productosRepository;

    
    @GetMapping
    public List<Productos> obtenerTodos() {
        return productosRepository.findAll();
    }

    
    @GetMapping("/{id}")
    public Optional<Productos> obtenerPorId(@PathVariable Long id) {
        return productosRepository.findById(id);
    }

    
    @PostMapping
    public Productos crearProducto(@RequestBody Productos producto) {
        return productosRepository.save(producto);
    }

    
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productosRepository.deleteById(id);
    }

    
    @PutMapping("/{id}")
    public Productos actualizarProducto(@PathVariable Long id, @RequestBody Productos productoActualizado) {
        productoActualizado.setId(id);
        return productosRepository.save(productoActualizado);
    }

    
    @GetMapping("/categoria/{categoria}")
    public List<Productos> obtenerPorCategoria(@PathVariable String categoria) {
        return productosRepository.findByCategoria(categoria);
    }

    
    @GetMapping("/codigo/{codigoProducto}")
    public Productos buscarPorCodigo(@PathVariable String codigoProducto) {
        return productosRepository.findByCodigoProducto(codigoProducto).stream().findFirst().orElse(null);
    }

    
    @GetMapping("/nombre/{nombre}")
    public List<Productos> buscarPorNombre(@PathVariable String nombre) {
        return productosRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @GetMapping("/stock/menor/{valor}")
    public List<Productos> productosConBajoStock(@PathVariable int valor) {
        return productosRepository.findByStockLessThan(valor);
    }
}
