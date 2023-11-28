package com.picpaysimplificado.picpaysimplificado.producers;

import com.picpaysimplificado.picpaysimplificado.domain.users.Users;
import com.picpaysimplificado.picpaysimplificado.dtos.EmailDto;
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
public class EmailProducer {

    @Autowired
    final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(Users sender, Users receiver, BigDecimal transactionValue) {
        Email senderEmail = createEmail(sender, receiver, transactionValue, true);
        Email receiverEmail = createEmail(sender, receiver, transactionValue, false);

        rabbitTemplate.convertAndSend("", routingKey, createMessageWrapper(senderEmail));
        rabbitTemplate.convertAndSend("", routingKey, createMessageWrapper(receiverEmail));

        System.out.println("Email enviado para 2 usuários..");
    }


    private Email createEmail(Users sender, Users receiver, BigDecimal transactionValue, boolean isSender) {
        String recipientName = isSender ? receiver.getFirstName() : sender.getFirstName();
        String subject = isSender ? "Você enviou" : "Você recebeu";
        String text = isSender ? "para" : "de";

        Email email = new Email();
        email.setUserId(UUID.randomUUID());
        email.setEmailTo(isSender ? sender.getEmail() : receiver.getEmail());
        email.setSubject("Olá " + recipientName + ", " + subject + " R$" + transactionValue + " com sucesso!");
        email.setText("Olá " + recipientName + ", " + subject + " R$" + transactionValue + " " + text + " " + (isSender ? receiver.getFirstName() : sender.getFirstName()) + " com sucesso!");

        return email;
    }

    private MessageWrapper createMessageWrapper(Email email) {
        HTMLSettings settings = new HTMLSettings(
                "https://img.freepik.com/free-vector/geometric-abstract-green-background_23-2148380710.jpg?size=626&ext=jpg&ga=GA1.1.1880011253.1699833600&semt=ais",
                "https://content-assets.unum.com.br/wp-content/2020/04/ppay-icon.png",
                "#FFF",
                "#FFF",
                email.getSubject(),
                email.getText() + " " + formatDateTime(LocalDateTime.now()),
                "<button style='border-radius:20px;border:none;background:#fff;color:black;width:30%;padding:10px;margin:auto;'>Ver no App</button>"
        );

        MessageWrapper messageWrapper = new MessageWrapper();
        messageWrapper.setEmail(email);
        messageWrapper.setHtmlSettings(settings);

        return messageWrapper;
    }



    private static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM - HH:mm");
        return dateTime.format(formatter);
    }

}
