package com.tuapp.proyecto_service.service;

import com.tuapp.proyecto_service.model.Proyecto;
import com.tuapp.proyecto_service.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    public Proyecto registrarProyecto(Proyecto proyecto) {
        proyecto.setEstado("PENDIENTE"); // Siempre un proyecto nuevo empieza en estado PENDIENTE
        return proyectoRepository.save(proyecto);
    }

    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAll();
    }

    public Optional<Proyecto> obtenerProyectoPorId(Long id) {
        return proyectoRepository.findById(id);
    }

    public void eliminarProyecto(Long id) {
        proyectoRepository.deleteById(id);
    }

    public Optional<Proyecto> actualizarProyecto(Long id, Proyecto datosActualizados) {
        return proyectoRepository.findById(id)
                .map(proyecto -> {
                    proyecto.setNombre(datosActualizados.getNombre());
                    proyecto.setDescripcion(datosActualizados.getDescripcion());
                    proyecto.setEmpresaId(datosActualizados.getEmpresaId());
                    // No se cambia el estado aquí
                    return proyectoRepository.save(proyecto);
                });
    }

    public Optional<Proyecto> actualizarEstado(Long id, String nuevoEstado) {
        return proyectoRepository.findById(id)
                .map(proyecto -> {
                    proyecto.setEstado(nuevoEstado);
                    return proyectoRepository.save(proyecto);
                });
    }

    // ✅ Nuevo método corregido: listar proyectos aprobados
    public List<Proyecto> listarProyectosAprobados() {
        return proyectoRepository.findByEstado("APROBADO");
    }
    // ProyectoService.java
    public List<Proyecto> listarProyectosPendientes() {
        return proyectoRepository.findByEstado("PENDIENTE");
    }
    public List<Proyecto> listarPorEmpresa(Long empresaId) {
        return proyectoRepository.findByEmpresaId(empresaId); // ⬅️ este método debe traer TODOS, sin filtrar por estado
    }



}
