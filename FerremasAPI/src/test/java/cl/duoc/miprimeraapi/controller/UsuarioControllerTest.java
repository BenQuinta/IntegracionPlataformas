package cl.duoc.miprimeraapi.controller;

import cl.duoc.miprimeraapi.model.Usuarios;
import cl.duoc.miprimeraapi.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    private UsuarioRepository usuarioRepo;
    private UsuarioController controller;

    @BeforeEach
    void setUp() throws Exception {
        usuarioRepo = mock(UsuarioRepository.class);
        controller = new UsuarioController();

        Field field = UsuarioController.class.getDeclaredField("usuarioRepo");
        field.setAccessible(true);
        field.set(controller, usuarioRepo);
    }

    // 1. Crear usuario con email existente -> 400 BAD REQUEST
    @Test
    void testCrearUsuarioConEmailExistente() {
        Usuarios usuario = new Usuarios();
        usuario.setEmail("test@correo.cl");

        when(usuarioRepo.existsByEmail("test@correo.cl")).thenReturn(true);

        ResponseEntity<?> response = controller.crearUsuario(usuario);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("correo ya está registrado"));
        verify(usuarioRepo).existsByEmail("test@correo.cl");
        verify(usuarioRepo, never()).save(any());
    }

    // 2. Crear usuario nuevo con email no existente -> 200 OK
    @Test
    void testCrearUsuarioNuevo() {
        Usuarios usuario = new Usuarios();
        usuario.setEmail("nuevo@correo.cl");

        when(usuarioRepo.existsByEmail("nuevo@correo.cl")).thenReturn(false);
        when(usuarioRepo.save(usuario)).thenReturn(usuario);

        ResponseEntity<?> response = controller.crearUsuario(usuario);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
        verify(usuarioRepo).existsByEmail("nuevo@correo.cl");
        verify(usuarioRepo).save(usuario);
    }

    // 3. Obtener usuario por ID existente -> 200 OK
    @Test
    void testObtenerUsuarioPorIdExistente() {
        Usuarios usuario = new Usuarios();
        usuario.setId(1L);
        usuario.setEmail("existe@correo.cl");

        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuarios> response = controller.obtenerUsuario(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
    }

    // 4. Obtener usuario por ID inexistente -> 404 NOT FOUND
    @Test
    void testObtenerUsuarioPorIdInexistente() {
        when(usuarioRepo.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Usuarios> response = controller.obtenerUsuario(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    // 5. Actualizar usuario existente -> 200 OK
    @Test
    void testActualizarUsuarioExistente() {
        Usuarios usuarioExistente = new Usuarios();
        usuarioExistente.setId(1L);
        usuarioExistente.setEmail("viejo@correo.cl");

        Usuarios datosActualizados = new Usuarios();
        datosActualizados.setNombre("NuevoNombre");
        datosActualizados.setApellido("NuevoApellido");
        datosActualizados.setEmail("nuevo@correo.cl");
        datosActualizados.setPassword("1234");
        datosActualizados.setRol("USER");

        when(usuarioRepo.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepo.save(any(Usuarios.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<?> response = controller.actualizarUsuario(1L, datosActualizados);

        assertEquals(200, response.getStatusCodeValue());
        Usuarios actualizado = (Usuarios) response.getBody();
        assertEquals("NuevoNombre", actualizado.getNombre());
        assertEquals("NuevoApellido", actualizado.getApellido());
        assertEquals("nuevo@correo.cl", actualizado.getEmail());
        assertEquals("1234", actualizado.getPassword());
        assertEquals("USER", actualizado.getRol());
    }

    // 6. Eliminar usuario existente -> 200 OK
    @Test
    void testEliminarUsuarioExistente() {
        when(usuarioRepo.existsById(1L)).thenReturn(true);
        doNothing().when(usuarioRepo).deleteById(1L);

        ResponseEntity<?> response = controller.eliminarUsuario(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(usuarioRepo).existsById(1L);
        verify(usuarioRepo).deleteById(1L);
    }
}
