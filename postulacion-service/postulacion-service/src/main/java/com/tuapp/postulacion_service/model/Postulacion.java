package com.tuapp.postulacion_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Postulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long estudianteId;

    private Long proyectoId;

    private String estado; // Puede ser "PENDIENTE", "APROBADO" o "RECHAZADO"
}
