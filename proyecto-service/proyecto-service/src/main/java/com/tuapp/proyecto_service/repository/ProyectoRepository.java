package com.tuapp.proyecto_service.repository;

import com.tuapp.proyecto_service.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    // 🔎 Buscar proyectos por empresa
    List<Proyecto> findByEmpresaId(Long empresaId);


    // 🔎 Buscar proyectos por estado (APROBADO, PENDIENTE, RECHAZADO, etc.)
    List<Proyecto> findByEstado(String estado);
    @Query("SELECT p FROM Proyecto p WHERE p.estado = 'APROBADO'")
    List<Proyecto> findProyectosAprobados();

}
