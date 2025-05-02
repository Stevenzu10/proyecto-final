package com.tuapp.usuario_service.repository;

import com.tuapp.usuario_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByCorreoAndContrasena(String correo, String contrasena);

}

