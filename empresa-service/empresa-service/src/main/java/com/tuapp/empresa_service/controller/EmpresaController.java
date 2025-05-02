package com.tuapp.empresa_service.controller;

import com.tuapp.empresa_service.model.Empresa;
import com.tuapp.empresa_service.service.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    public Empresa registrarEmpresa(@RequestBody Empresa empresa) {
        return empresaService.guardarEmpresa(empresa);
    }

    @GetMapping
    public List<Empresa> obtenerEmpresas() {
        return empresaService.listarEmpresas();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        empresaService.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> actualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresaActualizada) {
        return empresaService.actualizarEmpresa(id, empresaActualizada)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerEmpresaPorId(@PathVariable Long id) {
        return empresaService.obtenerEmpresaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/buscar")
    public List<Empresa> buscarEmpresas(@RequestParam String nombre) {
        return empresaService.buscarEmpresasPorNombre(nombre);
    }


}
