package com.kazzak.externalservice.service;

import com.kazzak.externalservice.model.Cliente;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class ProducerRabbit {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProducerRabbit(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(Cliente message) {
        rabbitTemplate.convertAndSend("client-data-exchange", "new", message.toJsonString().getBytes(StandardCharsets.UTF_8));
    }
}
