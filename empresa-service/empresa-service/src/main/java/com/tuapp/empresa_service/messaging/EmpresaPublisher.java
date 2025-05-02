package com.tuapp.empresa_service.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.tuapp.shared.model.EmpresaMensaje;


@Component
public class EmpresaPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${cola.empresa}")
    private String colaEmpresa;

    public EmpresaPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarEmpresaCreada(EmpresaMensaje mensaje) {
        rabbitTemplate.convertAndSend(colaEmpresa, mensaje);
        System.out.println("📤 Mensaje enviado a RabbitMQ: " + mensaje.getNombre());
    }
    public void enviarEmpresaEliminada(EmpresaMensaje empresaMensaje) {
        rabbitTemplate.convertAndSend("empresa.exchange", "empresa.eliminada", empresaMensaje);
    }
    public void enviarEmpresaEditada(EmpresaMensaje mensaje) {
        System.out.println("📤 [MENSAJE ENVIADO] Empresa editada: " + mensaje);
        rabbitTemplate.convertAndSend("empresa.editada", mensaje);
    }




}
