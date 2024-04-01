package com.kazzak.cbconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSender {
    public void sendEmail(String message) {
        log.warn("Enviando email CB OPEN com as metricas: {}", message );
    }
}
