package com.tuapp.postulacion_service.service;

import com.tuapp.postulacion_service.model.Postulacion;
import com.tuapp.postulacion_service.repository.PostulacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostulacionServiceTest {

    private PostulacionRepository postulacionRepository;
    private PostulacionService postulacionService;

    @BeforeEach
    void setUp() {
        postulacionRepository = mock(PostulacionRepository.class);
        postulacionService = new PostulacionService(postulacionRepository);
    }

    @Test
    void registrarPostulacion_debeAsignarEstadoPendiente() {
        Postulacion postulacion = new Postulacion();
        postulacion.setEstudianteId(1L);
        postulacion.setProyectoId(2L);

        when(postulacionRepository.save(any(Postulacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Postulacion resultado = postulacionService.registrarPostulacion(postulacion);

        assertEquals("PENDIENTE", resultado.getEstado());
        verify(postulacionRepository).save(postulacion);
    }

    @Test
    void listarPostulaciones_debeRetornarTodas() {
        List<Postulacion> lista = Arrays.asList(new Postulacion(), new Postulacion());
        when(postulacionRepository.findAll()).thenReturn(lista);

        List<Postulacion> resultado = postulacionService.listarPostulaciones();

        assertEquals(2, resultado.size());
        verify(postulacionRepository).findAll();
    }

    @Test
    void obtenerPostulacionPorId_encontrada() {
        Postulacion postulacion = new Postulacion();
        postulacion.setId(1L);

        when(postulacionRepository.findById(1L)).thenReturn(Optional.of(postulacion));

        Optional<Postulacion> resultado = postulacionService.obtenerPostulacionPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void actualizarEstado_debeActualizarCorrectamente() {
        Postulacion postulacion = new Postulacion();
        postulacion.setId(1L);
        postulacion.setEstado("PENDIENTE");

        when(postulacionRepository.findById(1L)).thenReturn(Optional.of(postulacion));
        when(postulacionRepository.save(any(Postulacion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Postulacion> resultado = postulacionService.actualizarEstado(1L, "APROBADO");

        assertTrue(resultado.isPresent());
        assertEquals("APROBADO", resultado.get().getEstado());
        verify(postulacionRepository).save(postulacion);
    }
}
