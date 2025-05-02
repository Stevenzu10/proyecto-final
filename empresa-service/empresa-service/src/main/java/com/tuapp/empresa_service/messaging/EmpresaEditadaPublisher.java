package com.tuapp.empresa_service.messaging;

import com.tuapp.shared.model.EmpresaEditadaMensaje;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpresaEditadaPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EmpresaEditadaPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarEmpresaEditada(EmpresaEditadaMensaje mensaje) {
        rabbitTemplate.convertAndSend("empresa.editada", mensaje);
        System.out.println("📤 [MENSAJE ENVIADO] Empresa editada: " + mensaje);
    }
}
