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

// Esta anotación indica que esta clase es un controlador REST que responderá a peticiones HTTP
@RestController
public class UsuarioController {

    // Este mapa simula una base de datos en memoria. La clave es el ID del usuario y el valor es el objeto Usuario
    private Map<Long, Productos> data = new HashMap<>();
    
    // Contador para generar IDs únicos para los nuevos usuarios
    private Long contadorId = 0L;

    // Método GET para obtener un usuario específico por su ID
    @GetMapping("usuarios/{id}")
    public ResponseEntity<Productos> getUsuarios(@PathVariable Long id) {
        // Se busca el usuario en el "mapa" (nuestra base de datos en memoria)
        Productos userData = data.get(id);

        // Si no se encuentra, se responde con 404 Not Found
        if (userData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Si se encuentra, se responde con el usuario y estado 200 OK
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    // Método POST para crear un nuevo usuario
    @PostMapping("usuarios")
    public ResponseEntity<Productos> postUsuarios(@RequestBody Productos usuario) {
        // Se incrementa el contador para asignar un ID único
        usuario.setId(++contadorId);

        // Se guarda el nuevo usuario en el mapa
        data.put(contadorId, usuario);

        // Se responde con el nuevo usuario y código 201 Created
        return new ResponseEntity<>(data.get(contadorId), HttpStatus.CREATED);
    }

    // Método GET para obtener todos los usuarios almacenados
    @GetMapping("usuarios")
    public ResponseEntity<List<Productos>> getTodosUsuarios() {
        // Se convierte el mapa de usuarios a una lista
        List<Productos> response = new ArrayList<Productos>(data.values());

        // Se responde con la lista y código 200 OK
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Método PUT para actualizar completamente un usuario existente
    @PutMapping("usuarios/{id}")
    public ResponseEntity<Productos> putUsuario(@PathVariable Long id, @RequestBody Productos usuarioRequest) {
        // Se busca el usuario por ID
        Productos userData = data.get(id);

        // Si no se encuentra, se responde con 404 Not Found
        if (userData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Se actualizan todos los campos del usuario (nombre y dirección)
        userData.setNombre(usuarioRequest.getNombre());
        userData.setMarca(usuarioRequest.getMarca());

        // Se guarda el usuario actualizado
        data.put(id, userData);

        // Se responde con el usuario actualizado y código 200 OK
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    // Método PATCH para actualizar parcialmente un usuario (solo algunos campos)
    @PatchMapping("usuarios/{id}")
    public ResponseEntity<Productos> patchUsuario(@PathVariable Long id, @RequestBody Productos usuarioRequest) {
        // Se busca el usuario por ID
        Productos userData = data.get(id);

        // Si no se encuentra, se responde con 404 Not Found
        if (userData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Solo se actualiza el nombre si viene en la solicitud y no está vacío
        if (usuarioRequest.getNombre() != null && !usuarioRequest.getNombre().isEmpty()
                && !usuarioRequest.getNombre().isBlank()) {
            userData.setNombre(usuarioRequest.getNombre());
        }

        // Solo se actualiza la dirección si viene en la solicitud y no está vacía
        if (usuarioRequest.getMarca() != null && !usuarioRequest.getMarca().isEmpty()
                && !usuarioRequest.getMarca().isBlank()) {
            userData.setMarca(usuarioRequest.getMarca());
        }

        // Se guarda el usuario modificado
        data.put(id, userData);

        // Se responde con el usuario actualizado y código 200 OK
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    // Método DELETE para eliminar un usuario por su ID
    @DeleteMapping("usuarios/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        // Se elimina el usuario y se verifica si existía
        Boolean eliminado = data.remove(id) != null;

        // Si se eliminó correctamente, se responde con 204 No Content
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // Si no se encontró el usuario, se responde con 404 Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
