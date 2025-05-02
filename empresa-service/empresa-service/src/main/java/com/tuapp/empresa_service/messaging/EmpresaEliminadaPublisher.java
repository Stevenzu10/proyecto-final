package com.tuapp.empresa_service.messaging;

import com.tuapp.shared.model.EmpresaEliminadaMensaje;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EmpresaEliminadaPublisher {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RabbitTemplate rabbitTemplate;

    public EmpresaEliminadaPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarEmpresaEliminada(EmpresaEliminadaMensaje mensaje) {
        rabbitTemplate.convertAndSend("empresa.eliminada", mensaje);

        try {
            String json = objectMapper.writeValueAsString(mensaje);
            System.out.println("📤 [MENSAJE ENVIADO] Empresa eliminada: " + json);
        } catch (Exception e) {
            System.out.println("📤 [ERROR AL CONVERTIR MENSAJE A JSON]");
        }
    }
}

