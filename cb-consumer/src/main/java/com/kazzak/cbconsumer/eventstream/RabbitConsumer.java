package com.kazzak.cbconsumer.eventstream;

import com.kazzak.cbconsumer.EmailSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {

    @Autowired
    EmailSender emailSender;

    @RabbitListener(queues = "cb-open-queue")
    public void receiveMessage(String message) {
        emailSender.sendEmail(message);
    }
}
