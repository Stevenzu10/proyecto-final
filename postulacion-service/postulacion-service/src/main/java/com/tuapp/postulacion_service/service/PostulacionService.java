package com.tuapp.postulacion_service.service;

import com.tuapp.postulacion_service.model.Postulacion;
import com.tuapp.postulacion_service.repository.PostulacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostulacionService {

    private final PostulacionRepository postulacionRepository;

    public PostulacionService(PostulacionRepository postulacionRepository) {
        this.postulacionRepository = postulacionRepository;
    }

    // 👉 Registrar una nueva postulación
    public Postulacion registrarPostulacion(Postulacion postulacion) {
        Optional<Postulacion> existente = postulacionRepository
                .findByEstudianteIdAndProyectoId(postulacion.getEstudianteId(), postulacion.getProyectoId());

        if (existente.isPresent()) {
            throw new IllegalStateException("Ya existe una postulación para este estudiante en este proyecto.");
        }

        postulacion.setEstado("PENDIENTE");
        return postulacionRepository.save(postulacion);
    }


    // 👉 Listar todas las postulaciones
    public List<Postulacion> listarPostulaciones() {
        return postulacionRepository.findAll();
    }

    // 👉 Obtener una postulación por ID
    public Optional<Postulacion> obtenerPostulacionPorId(Long id) {
        return postulacionRepository.findById(id);
    }

    // 👉 Aprobar o rechazar una postulación (cambia el estado)
    public Optional<Postulacion> actualizarEstado(Long id, String nuevoEstado) {
        return postulacionRepository.findById(id)
                .map(postulacion -> {
                    postulacion.setEstado(nuevoEstado);
                    return postulacionRepository.save(postulacion);
                });
    }

    // 👉 Listar postulaciones por proyecto
    public List<Postulacion> listarPostulacionesPorProyecto(Long proyectoId) {
        return postulacionRepository.findByProyectoId(proyectoId);
    }

    // 👉 Listar postulaciones por estudiante
    public List<Postulacion> listarPostulacionesPorEstudiante(Long estudianteId) {
        return postulacionRepository.findByEstudianteId(estudianteId);
    }
    public List<Postulacion> listarPendientesPorProyecto(Long proyectoId) {
        return postulacionRepository.findByProyectoIdAndEstado(proyectoId, "PENDIENTE");
    }

}
