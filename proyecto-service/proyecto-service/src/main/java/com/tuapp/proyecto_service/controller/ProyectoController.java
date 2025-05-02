package com.tuapp.proyecto_service.controller;

import com.tuapp.proyecto_service.model.Proyecto;
import com.tuapp.proyecto_service.service.ProyectoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @PostMapping
    public Proyecto registrarProyecto(@RequestBody Proyecto proyecto) {
        return proyectoService.registrarProyecto(proyecto);
    }

    @GetMapping
    public List<Proyecto> listarProyectos() {
        return proyectoService.listarProyectos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Long id) {
        return proyectoService.obtenerProyectoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyecto) {
        return proyectoService.actualizarProyecto(id, proyecto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Proyecto> actualizarEstado(@PathVariable Long id, @RequestBody String nuevoEstado) {
        String estadoLimpio = nuevoEstado.trim().replace("\"", "");
        return proyectoService.actualizarEstado(id, estadoLimpio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // ✅ Nuevo endpoint: Listar proyectos aprobados
    @GetMapping("/aprobados")
    public ResponseEntity<List<Proyecto>> listarProyectosAprobados() {
        List<Proyecto> aprobados = proyectoService.listarProyectosAprobados();
        return ResponseEntity.ok(aprobados);
    }
    // ProyectoController.java
    @GetMapping("/pendientes")
    public List<Proyecto> listarProyectosPendientes() {
        return proyectoService.listarProyectosPendientes();
    }
    @GetMapping("/empresa/{empresaId}")
    public List<Proyecto> listarPorEmpresa(@PathVariable Long empresaId) {
        return proyectoService.listarPorEmpresa(empresaId);
    }


}
