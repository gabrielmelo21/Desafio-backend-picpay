package com.picpaysimplificado.picpaysimplificado.producers;


import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@Data
public class EmailProducerTeste {
    @Autowired
    final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmailTeste() {
        Email email = new Email();
        email.setUserId(UUID.randomUUID());
        email.setEmailTo("gabriel.user0001@gmail.com");
        email.setSubject("Title -> Mensagem de email vinda do Teste (desafio picpay) testando o htmlSettings");
        email.setText("MSG -> Mensagem de email vinda do Teste (desafio picpay) testando o htmlSettings");

        HTMLSettings settings = new HTMLSettings(
                "https://img.freepik.com/free-vector/geometric-abstract-green-background_23-2148380710.jpg?size=626&ext=jpg&ga=GA1.1.1880011253.1699833600&semt=ais",
                "https://content-assets.unum.com.br/wp-content/2020/04/ppay-icon.png",
                "#FFF",
                "#FFF",
                email.getSubject(),
                email.getText() +" "+ formatDateTime(LocalDateTime.now()),
                "<button style='border-radius:20px;border:none;background:#fff;color:black;width:30%;padding:10px;margin:auto;'>Ver no App</button>"
        );

        MessageWrapper messageWrapper = new MessageWrapper();
        messageWrapper.setEmail(email);
        messageWrapper.setHtmlSettings(settings);

/**
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(messageWrapper);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
**/
       rabbitTemplate.convertAndSend("", routingKey, messageWrapper);

    }
    private static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM - HH:mm");
        return dateTime.format(formatter);
    }

    }
