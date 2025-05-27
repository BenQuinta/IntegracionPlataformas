package cl.duoc.miprimeraapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.miprimeraapi.model.Productos;

@RestController
public class ProductosController {
    private Map<Long, Productos> data = new HashMap<>();
    // Contador de ID para productos
    private Long contadorId = 0L;
    // Método GET productos por ID
    @GetMapping("productos/{id}")
    public ResponseEntity<Productos> getProductos(@PathVariable Long id) {
        // Se busca el producto en el "mapa" (nuestra base de datos en memoria)
        Productos productData = data.get(id);
        // Si no se encuentra, se responde con 404 Not Found
        if (productData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Si se encuentra, se responde con el producto y estado 200 OK
        return new ResponseEntity<>(productData, HttpStatus.OK);
    }
    // Método POST para crear un nuevo producto
    @PostMapping("productos")
    public ResponseEntity<Productos> postProductos(@RequestBody Productos producto) {
        // Se incrementa el contador para asignar un ID único
        producto.setId(++contadorId);
        // Se guarda el nuevo producto en el mapa
        data.put(contadorId, producto);
        // Se responde con el nuevo producto y código 201 Created
        return new ResponseEntity<>(data.get(contadorId), HttpStatus.CREATED);
    }
    // Método GET para obtener todos los productos almacenados
    @GetMapping("productos")
    public ResponseEntity<List<Productos>> getTodosProductos() {
        // Se convierte el mapa de productos a una lista
        List<Productos> response = new ArrayList<Productos>(data.values());
        // Se responde con la lista y código 200 OK
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // Método PUT para actualizar completamente un producto existente
    @PutMapping("productos/{id}")
    public ResponseEntity<Productos> putProducto(@PathVariable Long id, @RequestBody Productos productoRequest) {
        // Se busca el producto por ID
        Productos productData = data.get(id);
        // Si no se encuentra, se responde con 404 Not Found
        if (productData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Se actualizan todos los campos del producto
        productData.setNombre(productoRequest.getNombre());
        productData.setMarca(productoRequest.getMarca());
        productData.setStock(productoRequest.getStock());
        productData.setModelo(productoRequest.getModelo());
        productData.setPrecio(productoRequest.getPrecio());
        // Se guarda el producto actualizado
        data.put(id, productData);
        // Se responde con el producto actualizado y código 200 OK
        return new ResponseEntity<>(productData, HttpStatus.OK);
    }

    // Método PATCH para actualizar algunos campos de un producto
    @PatchMapping("productos/{id}")
    public ResponseEntity<Productos> patchProducto(@PathVariable Long id, @RequestBody Productos productoRequest) {
        // Se busca el producto por ID
        Productos productData = data.get(id);
        // Si no se encuentra, se responde con 404 Not Found
        if (productData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Solo se actualiza el nombre si viene en la solicitud y no está vacío
        if (productoRequest.getNombre() != null && !productoRequest.getNombre().isEmpty()
                && !productoRequest.getNombre().isBlank()) {
            productData.setNombre(productoRequest.getNombre());
        }
        // Solo se actualiza la marca si viene en la solicitud y no está vacía
        if (productoRequest.getMarca() != null && !productoRequest.getMarca().isEmpty()
                && !productoRequest.getMarca().isBlank()) {
            productData.setMarca(productoRequest.getMarca());
        }
        // Solo se actualiza el stock si viene en la solicitud y es mayor o igual a 0
        if (productoRequest.getStock() != null && productoRequest.getStock() >= 0) {
            productData.setStock(productoRequest.getStock());
        }
        // Solo se actualiza el modelo si viene en la solicitud y no está vacío
        if (productoRequest.getModelo() != null && !productoRequest.getModelo().isEmpty()
                && !productoRequest.getModelo().isBlank()) {
            productData.setModelo(productoRequest.getModelo());
        }
        // Solo se actualiza el precio si viene en la solicitud y no está vacío
        if (productoRequest.getPrecio() != null && !productoRequest.getPrecio().isEmpty()) {
            productData.setPrecio(productoRequest.getPrecio());
        }
        // Se guarda el producto modificado
        data.put(id, productData);
        // Se responde con el producto actualizado y código 200 OK
        return new ResponseEntity<>(productData, HttpStatus.OK);
    }

    // Método DELETE para eliminar un producto por su ID
    @DeleteMapping("productos/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        // Se elimina el producto y se verifica si existía
        Boolean eliminado = data.remove(id) != null;
        // Si se eliminó correctamente, se responde con 204 No Content
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // Si no se encontró el producto, se responde con 404 Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}