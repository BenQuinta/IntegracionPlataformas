package cl.duoc.miprimeraapi.controller;

import cl.duoc.miprimeraapi.model.Usuarios;
import cl.duoc.miprimeraapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*") // Permite peticiones desde tu frontend
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    // Listar todos los usuarios
    @GetMapping
    public List<Usuarios> listarUsuarios() {
        return usuarioRepo.findAll();
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> obtenerUsuario(@PathVariable Long id) {
        Optional<Usuarios> usuario = usuarioRepo.findById(id);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nuevo usuario
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuarios nuevoUsuario) {
        if (usuarioRepo.existsByEmail(nuevoUsuario.getEmail())) {
            return ResponseEntity.badRequest().body("⚠️ El correo ya está registrado.");
        }
        Usuarios creado = usuarioRepo.save(nuevoUsuario);
        return ResponseEntity.ok(creado);
    }

    // Actualizar usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuarios datos) {
        return usuarioRepo.findById(id).map(usuario -> {
            usuario.setNombre(datos.getNombre());
            usuario.setApellido(datos.getApellido());
            usuario.setEmail(datos.getEmail());
            usuario.setPassword(datos.getPassword());
            usuario.setRol(datos.getRol());
            return ResponseEntity.ok(usuarioRepo.save(usuario));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Buscar por email (opcional)
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorEmail(@RequestParam String email) {
        Usuarios usuario = usuarioRepo.findByEmail(email);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    // Iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuarios datos) {
        Usuarios usuario = usuarioRepo.findByEmail(datos.getEmail());

        if (usuario != null && usuario.getPassword().equals(datos.getPassword())) {
            return ResponseEntity.ok(usuario); // Puedes devolver solo el nombre/rol si prefieres
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}
