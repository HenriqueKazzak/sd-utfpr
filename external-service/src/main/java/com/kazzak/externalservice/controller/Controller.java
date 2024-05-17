package com.kazzak.externalservice.controller;

import com.kazzak.externalservice.model.Cliente;
import com.kazzak.externalservice.model.Pedido;
import com.kazzak.externalservice.service.ProducerRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    ProducerRabbit producerRabbit;

    @PostMapping("api/v1/cliente")
    public ResponseEntity<String> cadastra(@RequestBody Cliente cliente){
        producerRabbit.send(cliente);
        return ResponseEntity.ok("Abertura de conta realizada com sucesso!");
    }

}
