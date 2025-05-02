package com.tuapp.usuario_service.service;

import com.tuapp.usuario_service.model.Usuario;
import com.tuapp.usuario_service.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
    public Optional<Usuario> actualizarUsuario(Long id, Usuario datosNuevos) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(datosNuevos.getNombre());
            usuario.setCorreo(datosNuevos.getCorreo());
            usuario.setContrasena(datosNuevos.getContrasena());
            usuario.setRol(datosNuevos.getRol());
            return usuarioRepository.save(usuario);
        });
    }
    public Optional<Usuario> login(String correo, String contrasena) {
        return usuarioRepository.findByCorreo(correo)
                .filter(usuario -> usuario.getContrasena().equals(contrasena));
    }


}
