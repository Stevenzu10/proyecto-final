package com.tuapp.empresa_service.service;

import com.tuapp.empresa_service.messaging.EmpresaEditadaPublisher;
import com.tuapp.empresa_service.messaging.EmpresaEliminadaPublisher;
import com.tuapp.empresa_service.messaging.EmpresaPublisher;
import com.tuapp.empresa_service.model.Empresa;
import com.tuapp.empresa_service.repository.EmpresaRepository;
import com.tuapp.shared.model.EmpresaEliminadaMensaje;
import com.tuapp.shared.model.EmpresaEditadaMensaje;
import com.tuapp.shared.model.EmpresaMensaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;
    @Mock
    private EmpresaPublisher empresaPublisher;
    @Mock
    private EmpresaEliminadaPublisher empresaEliminadaPublisher;
    @Mock
    private EmpresaEditadaPublisher empresaEditadaPublisher;

    @InjectMocks
    private EmpresaService empresaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNombre("Test");
        empresa.setContacto("1234");

        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa);

        Empresa guardada = empresaService.guardarEmpresa(empresa);

        assertEquals("Test", guardada.getNombre());
        verify(empresaPublisher).enviarEmpresaCreada(any(EmpresaMensaje.class));
    }

    @Test
    void testActualizarEmpresa() {
        Empresa existente = new Empresa();
        existente.setId(1L);

        Empresa datosNuevos = new Empresa();
        datosNuevos.setNombre("Nuevo Nombre");
        datosNuevos.setDescripcion("Desc");
        datosNuevos.setContacto("Contacto");
        datosNuevos.setCorreo("correo@test.com");

        when(empresaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(empresaRepository.save(any(Empresa.class))).thenReturn(existente);

        Optional<Empresa> actualizada = empresaService.actualizarEmpresa(1L, datosNuevos);

        assertTrue(actualizada.isPresent());
        assertEquals("Nuevo Nombre", actualizada.get().getNombre());
        verify(empresaEditadaPublisher).enviarEmpresaEditada(any(EmpresaEditadaMensaje.class));
    }

    @Test
    void testListarEmpresas() {
        Empresa e1 = new Empresa();
        Empresa e2 = new Empresa();
        when(empresaRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Empresa> lista = empresaService.listarEmpresas();

        assertEquals(2, lista.size());
    }

    @Test
    void testEliminarEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNombre("Empresa X");
        empresa.setCorreo("correo@x.com");

        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));

        empresaService.eliminarEmpresa(1L);

        verify(empresaRepository).deleteById(1L);
        verify(empresaEliminadaPublisher).enviarEmpresaEliminada(any(EmpresaEliminadaMensaje.class));
    }

    @Test
    void testObtenerEmpresaPorId() {
        Empresa empresa = new Empresa();
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));

        Optional<Empresa> resultado = empresaService.obtenerEmpresaPorId(1L);

        assertTrue(resultado.isPresent());
    }

    @Test
    void testBuscarEmpresasPorNombre() {
        Empresa empresa = new Empresa();
        when(empresaRepository.buscarPorNombre("test"))
                .thenReturn(List.of(empresa));

        List<Empresa> resultados = empresaService.buscarEmpresasPorNombre("test");

        assertEquals(1, resultados.size());
    }
}
