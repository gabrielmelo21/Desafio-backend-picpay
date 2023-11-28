package com.picpaysimplificado.picpaysimplificado.controllers;

import com.picpaysimplificado.picpaysimplificado.producers.EmailProducerTeste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private EmailProducerTeste emailProducerTeste;

    @GetMapping
    public String sendEmailTeste(){
         emailProducerTeste.publishMessageEmailTeste();
         return "Teste Enviado...";
    }
}
