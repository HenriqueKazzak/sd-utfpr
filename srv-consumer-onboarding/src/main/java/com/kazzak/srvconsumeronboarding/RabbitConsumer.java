package com.kazzak.srvconsumeronboarding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kazzak.srvconsumeronboarding.repository.OnboardingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

@Service
@Slf4j
public class RabbitConsumer {

    private final Cipher decryptCipher = Cipher.getInstance("RSA");
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    private OnboardingRepository onboardingRepository;

    public RabbitConsumer() throws NoSuchPaddingException, NoSuchAlgorithmException {
    }

    @RabbitListener(queues = "onboarding-queue")
    public void receiveMessage(@Payload String message) throws IllegalBlockSizeException, BadPaddingException, JsonProcessingException {
        byte[] decryped = decryptCipher.doFinal(message.getBytes());
        String decryptedMessage = new String(decryped);
        OnboardingEntity onboardingEntity = objectMapper.readValue(decryptedMessage, OnboardingEntity.class);
        onboardingRepository.save(onboardingEntity);
        log.warn("Recebendo mensagem de onboarding: {}", decryptedMessage);
    }

    @PostConstruct
    public void init() throws InvalidKeySpecException, InvalidKeyException, IOException, NoSuchAlgorithmException {
        String privateKeyText = Arrays.toString(Files.readAllBytes(Paths.get("private_key.pem")));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec privateKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(privateKeyText));
        Key privateKey = keyFactory.generatePrivate(privateKeySpec);
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
    }
}
