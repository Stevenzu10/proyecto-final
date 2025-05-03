package com.tuapp.postulacion_service.repository;

import com.tuapp.postulacion_service.model.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {

    //  Buscar postulaciones por proyecto
    List<Postulacion> findByProyectoId(Long proyectoId);

    //  Buscar postulaciones por estudiante
    List<Postulacion> findByEstudianteId(Long estudianteId);

    //  Buscar postulaciones por estado (por ejemplo, para ver solo las pendientes)
    List<Postulacion> findByEstado(String estado);
    Optional<Postulacion> findByEstudianteIdAndProyectoId(Long estudianteId, Long proyectoId);
    List<Postulacion> findByProyectoIdAndEstado(Long proyectoId, String estado);


}
