package com.tuapp.usuario_service.service;

import com.tuapp.usuario_service.model.Usuario;
import com.tuapp.usuario_service.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    void registrarUsuario_DeberiaGuardarYRetornarUsuario() {
        Usuario nuevo = new Usuario();
        nuevo.setCorreo("test@mail.com");

        when(usuarioRepository.save(any())).thenReturn(nuevo);

        Usuario registrado = usuarioService.registrarUsuario(nuevo);

        assertNotNull(registrado);
        assertEquals("test@mail.com", registrado.getCorreo());
        verify(usuarioRepository, times(1)).save(nuevo);
    }

    @Test
    void login_CredencialesValidas_DeberiaRetornarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setCorreo("correo@mail.com");
        usuario.setContrasena("1234");

        when(usuarioRepository.findByCorreoAndContrasena("correo@mail.com", "1234"))
                .thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.login("correo@mail.com", "1234");

        assertTrue(resultado.isPresent());
        assertEquals("correo@mail.com", resultado.get().getCorreo());
    }

    @Test
    void login_CredencialesInvalidas_DeberiaRetornarVacio() {
        when(usuarioRepository.findByCorreoAndContrasena("no@mail.com", "wrong"))
                .thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.login("no@mail.com", "wrong");

        assertFalse(resultado.isPresent());
    }
}
