package cl.duoc.miprimeraapi.controller;

import cl.duoc.miprimeraapi.model.Usuarios;
import cl.duoc.miprimeraapi.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;

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

    @Test
    void testCrearUsuarioConEmailExistente() {
        Usuarios usuario = new Usuarios();
        usuario.setEmail("test@correo.cl");

        when(usuarioRepo.existsByEmail("test@correo.cl")).thenReturn(true);

        ResponseEntity<?> response = controller.crearUsuario(usuario);

        assertEquals(400, response.getStatusCodeValue());
        verify(usuarioRepo).existsByEmail("test@correo.cl");
    }
}

