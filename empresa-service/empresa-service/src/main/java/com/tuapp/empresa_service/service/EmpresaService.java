package com.tuapp.empresa_service.service;

import com.tuapp.empresa_service.messaging.EmpresaEliminadaPublisher;
import com.tuapp.empresa_service.messaging.EmpresaEditadaPublisher;
import com.tuapp.empresa_service.messaging.EmpresaPublisher;
import com.tuapp.empresa_service.model.Empresa;
import com.tuapp.empresa_service.repository.EmpresaRepository;
import com.tuapp.shared.model.EmpresaEliminadaMensaje;
import com.tuapp.shared.model.EmpresaEditadaMensaje;
import com.tuapp.shared.model.EmpresaMensaje;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EmpresaPublisher empresaPublisher;
    private final EmpresaEliminadaPublisher empresaEliminadaPublisher;
    private final EmpresaEditadaPublisher empresaEditadaPublisher;

    public EmpresaService(EmpresaRepository empresaRepository,
                          EmpresaPublisher empresaPublisher,
                          EmpresaEliminadaPublisher empresaEliminadaPublisher,
                          EmpresaEditadaPublisher empresaEditadaPublisher) {
        this.empresaRepository = empresaRepository;
        this.empresaPublisher = empresaPublisher;
        this.empresaEliminadaPublisher = empresaEliminadaPublisher;
        this.empresaEditadaPublisher = empresaEditadaPublisher;
    }

    public Empresa guardarEmpresa(Empresa empresa) {
        Empresa guardada = empresaRepository.save(empresa);

        EmpresaMensaje mensaje = new EmpresaMensaje(
                guardada.getId(),
                guardada.getNombre(),
                guardada.getContacto()
        );

        empresaPublisher.enviarEmpresaCreada(mensaje);
        return guardada;
    }

    public Optional<Empresa> actualizarEmpresa(Long id, Empresa datosNuevos) {
        return empresaRepository.findById(id).map(empresa -> {
            empresa.setNombre(datosNuevos.getNombre());
            empresa.setDescripcion(datosNuevos.getDescripcion());
            empresa.setContacto(datosNuevos.getContacto());
            empresa.setCorreo(datosNuevos.getCorreo());

            Empresa actualizada = empresaRepository.save(empresa);

            EmpresaEditadaMensaje mensaje = new EmpresaEditadaMensaje();
            mensaje.setId(actualizada.getId());
            mensaje.setNombre(actualizada.getNombre());
            mensaje.setCorreo(actualizada.getCorreo());

            empresaEditadaPublisher.enviarEmpresaEditada(mensaje);

            return actualizada;
        });
    }

    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    public void eliminarEmpresa(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con id: " + id));

        empresaRepository.deleteById(id);

        EmpresaEliminadaMensaje mensaje = new EmpresaEliminadaMensaje();
        mensaje.setId(empresa.getId());
        mensaje.setNombre(empresa.getNombre());
        mensaje.setCorreo(empresa.getCorreo());

        empresaEliminadaPublisher.enviarEmpresaEliminada(mensaje);
    }
    public Optional<Empresa> obtenerEmpresaPorId(Long id) {
        return empresaRepository.findById(id);
    }
    public List<Empresa> buscarEmpresasPorNombre(String nombre) {
        return empresaRepository.buscarPorNombre(nombre);
    }


}
