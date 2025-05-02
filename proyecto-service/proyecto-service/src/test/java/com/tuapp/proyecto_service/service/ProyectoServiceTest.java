package com.tuapp.proyecto_service.service;

import com.tuapp.proyecto_service.model.Proyecto;
import com.tuapp.proyecto_service.repository.ProyectoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProyectoServiceTest {

    private ProyectoRepository proyectoRepository;
    private ProyectoService proyectoService;

    @BeforeEach
    void setUp() {
        proyectoRepository = mock(ProyectoRepository.class);
        proyectoService = new ProyectoService(proyectoRepository);
    }

    @Test
    void testListarProyectosAprobados() {
        // Arrange
        Proyecto p1 = new Proyecto();
        p1.setNombre("Proyecto A");
        p1.setEstado("APROBADO");

        Proyecto p2 = new Proyecto();
        p2.setNombre("Proyecto B");
        p2.setEstado("APROBADO");

        when(proyectoRepository.findProyectosAprobados()).thenReturn(Arrays.asList(p1, p2));

        // Act
        List<Proyecto> resultado = proyectoService.listarProyectosAprobados();

        // Assert
        assertEquals(2, resultado.size());
        verify(proyectoRepository, times(1)).findProyectosAprobados();
    }
    @Test
    void registrarProyecto_DeberiaGuardarConEstadoPendiente() {
        // Arrange
        Proyecto nuevoProyecto = new Proyecto();
        nuevoProyecto.setNombre("Proyecto de prueba");
        nuevoProyecto.setDescripcion("Descripción de prueba");
        nuevoProyecto.setEmpresaId(1L);

        Proyecto proyectoConEstado = new Proyecto();
        proyectoConEstado.setId(1L);
        proyectoConEstado.setNombre("Proyecto de prueba");
        proyectoConEstado.setDescripcion("Descripción de prueba");
        proyectoConEstado.setEmpresaId(1L);
        proyectoConEstado.setEstado("PENDIENTE");

        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyectoConEstado);

        // Act
        Proyecto resultado = proyectoService.registrarProyecto(nuevoProyecto);

        // Assert
        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstado());
        verify(proyectoRepository).save(any(Proyecto.class));
    }
    @Test
    void actualizarProyecto_DeberiaActualizarCamposBasicos() {
        // Arrange
        Long idProyecto = 1L;

        Proyecto existente = new Proyecto();
        existente.setId(idProyecto);
        existente.setNombre("Viejo nombre");
        existente.setDescripcion("Vieja descripción");
        existente.setEmpresaId(100L);
        existente.setEstado("PENDIENTE");

        Proyecto datosActualizados = new Proyecto();
        datosActualizados.setNombre("Nuevo nombre");
        datosActualizados.setDescripcion("Nueva descripción");
        datosActualizados.setEmpresaId(200L);

        Proyecto actualizado = new Proyecto();
        actualizado.setId(idProyecto);
        actualizado.setNombre("Nuevo nombre");
        actualizado.setDescripcion("Nueva descripción");
        actualizado.setEmpresaId(200L);
        actualizado.setEstado("PENDIENTE");

        when(proyectoRepository.findById(idProyecto)).thenReturn(Optional.of(existente));
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(actualizado);

        // Act
        Optional<Proyecto> resultado = proyectoService.actualizarProyecto(idProyecto, datosActualizados);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Nuevo nombre", resultado.get().getNombre());
        assertEquals("Nueva descripción", resultado.get().getDescripcion());
        assertEquals(200L, resultado.get().getEmpresaId());
        assertEquals("PENDIENTE", resultado.get().getEstado()); // Estado no cambia

        verify(proyectoRepository).findById(idProyecto);
        verify(proyectoRepository).save(any(Proyecto.class));
    }
    @Test
    void actualizarEstado_DeberiaCambiarEstadoCorrectamente() {
        // Arrange
        Long idProyecto = 1L;
        String nuevoEstado = "APROBADO";

        Proyecto existente = new Proyecto();
        existente.setId(idProyecto);
        existente.setNombre("Proyecto Test");
        existente.setDescripcion("Descripción");
        existente.setEmpresaId(5L);
        existente.setEstado("PENDIENTE");

        Proyecto actualizado = new Proyecto();
        actualizado.setId(idProyecto);
        actualizado.setNombre("Proyecto Test");
        actualizado.setDescripcion("Descripción");
        actualizado.setEmpresaId(5L);
        actualizado.setEstado(nuevoEstado);

        when(proyectoRepository.findById(idProyecto)).thenReturn(Optional.of(existente));
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(actualizado);

        // Act
        Optional<Proyecto> resultado = proyectoService.actualizarEstado(idProyecto, nuevoEstado);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(nuevoEstado, resultado.get().getEstado());
        assertEquals("Proyecto Test", resultado.get().getNombre()); // No se debe afectar
        verify(proyectoRepository).findById(idProyecto);
        verify(proyectoRepository).save(any(Proyecto.class));
    }
    @Test
    void eliminarProyecto_DeberiaEliminarPorId() {
        // Arrange
        Long idProyecto = 1L;

        doNothing().when(proyectoRepository).deleteById(idProyecto);

        // Act
        proyectoService.eliminarProyecto(idProyecto);

        // Assert
        verify(proyectoRepository).deleteById(idProyecto);
    }
    @Test
    void actualizarProyecto_DeberiaActualizarDatosExistentes() {
        // Arrange
        Long id = 1L;

        Proyecto existente = new Proyecto();
        existente.setId(id);
        existente.setNombre("Antiguo nombre");
        existente.setDescripcion("Antigua descripción");
        existente.setEmpresaId(5L);

        Proyecto datosNuevos = new Proyecto();
        datosNuevos.setNombre("Nuevo nombre");
        datosNuevos.setDescripcion("Nueva descripción");
        datosNuevos.setEmpresaId(10L);

        Proyecto actualizado = new Proyecto();
        actualizado.setId(id);
        actualizado.setNombre("Nuevo nombre");
        actualizado.setDescripcion("Nueva descripción");
        actualizado.setEmpresaId(10L);

        when(proyectoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(actualizado);

        // Act
        Optional<Proyecto> resultado = proyectoService.actualizarProyecto(id, datosNuevos);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Nuevo nombre", resultado.get().getNombre());
        assertEquals("Nueva descripción", resultado.get().getDescripcion());
        assertEquals(10L, resultado.get().getEmpresaId());
        verify(proyectoRepository).save(any(Proyecto.class));
    }
    @Test
    void obtenerProyectoPorId_DeberiaDevolverProyectoSiExiste() {
        // Arrange
        Long id = 1L;
        Proyecto proyecto = new Proyecto();
        proyecto.setId(id);
        proyecto.setNombre("Proyecto de prueba");

        when(proyectoRepository.findById(id)).thenReturn(Optional.of(proyecto));

        // Act
        Optional<Proyecto> resultado = proyectoService.obtenerProyectoPorId(id);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Proyecto de prueba", resultado.get().getNombre());
    }






}
