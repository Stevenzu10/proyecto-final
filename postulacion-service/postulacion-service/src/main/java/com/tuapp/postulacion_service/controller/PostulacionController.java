package com.tuapp.postulacion_service.controller;

import com.tuapp.postulacion_service.model.Postulacion;
import com.tuapp.postulacion_service.service.PostulacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/postulaciones")
public class PostulacionController {

    private final PostulacionService postulacionService;

    public PostulacionController(PostulacionService postulacionService) {
        this.postulacionService = postulacionService;
    }

    // 👉 Registrar nueva postulación
    @PostMapping
    public ResponseEntity<?> registrarPostulacion(@RequestBody Postulacion postulacion) {
        try {
            Postulacion guardada = postulacionService.registrarPostulacion(postulacion);
            return ResponseEntity.ok(guardada);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // 👉 Listar todas las postulaciones
    @GetMapping
    public List<Postulacion> listarPostulaciones() {
        return postulacionService.listarPostulaciones();
    }

    // 👉 Obtener postulación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Postulacion> obtenerPostulacionPorId(@PathVariable Long id) {
        return postulacionService.obtenerPostulacionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 👉 Aprobar o rechazar postulación
    @PutMapping("/{id}/estado")
    public ResponseEntity<Postulacion> actualizarEstado(@PathVariable Long id, @RequestBody String nuevoEstado) {
        return postulacionService.actualizarEstado(id, nuevoEstado.trim()) // 👈 trim para evitar \r\n
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 👉 Listar postulaciones por proyecto
    @GetMapping("/proyecto/{proyectoId}")
    public List<Postulacion> listarPostulacionesPorProyecto(@PathVariable Long proyectoId) {
        return postulacionService.listarPostulacionesPorProyecto(proyectoId);
    }

    // 👉 Listar postulaciones por estudiante
    @GetMapping("/estudiante/{estudianteId}")
    public List<Postulacion> listarPostulacionesPorEstudiante(@PathVariable Long estudianteId) {
        return postulacionService.listarPostulacionesPorEstudiante(estudianteId);
    }
    @GetMapping("/proyecto/{proyectoId}/pendientes")
    public List<Postulacion> listarPendientesPorProyecto(@PathVariable Long proyectoId) {
        return postulacionService.listarPendientesPorProyecto(proyectoId);
    }

}
