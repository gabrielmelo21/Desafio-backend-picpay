package com.picpaysimplificado.picpaysimplificado.services;


import com.picpaysimplificado.picpaysimplificado.domain.transactions.Transactions;
import com.picpaysimplificado.picpaysimplificado.domain.users.Users;
import com.picpaysimplificado.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.picpaysimplificado.producers.EmailProducer;
import com.picpaysimplificado.picpaysimplificado.repositories.TransactionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;


@Service
@AllArgsConstructor
public class TransactionsService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private TransactionsRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmailProducer emailProducer;


    public TransactionsService() {
    }

    public Transactions createTransaction(TransactionDTO transaction) throws Exception{
       Users sender = this.usersService.findUserById(transaction.senderId());
       Users receiver = this.usersService.findUserById(transaction.receiverId());

       usersService.validateTransaction(sender, receiver, transaction.value());

     boolean isAuthorized = this.authorizeTransaction();

     if (!isAuthorized){
          throw new Exception("Transação não autorizada");
     }


        Transactions newTransaction = new Transactions();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));



       this.repository.save(newTransaction);
       this.usersService.saveUser(sender);
       this.usersService.saveUser(receiver);



     emailProducer.publishMessageEmail(sender, receiver, transaction.value());


       return newTransaction;
   }

   public boolean authorizeTransaction(){
       ResponseEntity<Map> authResponse = restTemplate.getForEntity("https://run.mocky.io/v3/d8cac88d-c6e7-48c3-a819-221ad252df8f", Map.class);



       if (authResponse.getStatusCode() == HttpStatus.OK){
           String message = (String) authResponse.getBody().get("message");

           return "Autorizado".equalsIgnoreCase(message);
       }else return false;

   }

}
