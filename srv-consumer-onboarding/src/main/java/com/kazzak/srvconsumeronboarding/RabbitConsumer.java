package com.kazzak.srvconsumeronboarding;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitConsumer {

    @RabbitListener(queues = "onboarding-queue")
    public void receiveMessage(@Payload String message) {
       log.warn("Recebendo mensagem de onboarding: {}", message);
    }
}
