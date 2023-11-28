package com.picpaysimplificado.picpaysimplificado.controllers;

import com.picpaysimplificado.picpaysimplificado.domain.users.Users;
import com.picpaysimplificado.picpaysimplificado.dtos.UsersDTO;
import com.picpaysimplificado.picpaysimplificado.producers.EmailProducer;
import com.picpaysimplificado.picpaysimplificado.producers.EmailProducerTeste;
import com.picpaysimplificado.picpaysimplificado.services.UsersService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private EmailProducer emailProducer;

    @Autowired
    private EmailProducerTeste emailProducerTeste;

       @PostMapping
     public ResponseEntity<Users> createUser(@RequestBody UsersDTO usersDTO){

        Users newUser = usersService.createUsers(usersDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
       }
       @GetMapping("/teste")
       public ResponseEntity<String> teste(){
           try {
               // Lógica do seu método aqui

               // Se algo der errado, lança uma exceção
               // throw new Exception("Mensagem de erro");

               return new ResponseEntity<>("Teste", HttpStatus.OK);
           } catch (Exception e) {
               // Captura a exceção e retorna uma resposta de erro
               return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
           }
       }
       @GetMapping("/{id}")
       public ResponseEntity<Users> findById(@PathVariable UUID id){
            Users usersId = usersService.findUserById(id);
            return new ResponseEntity<>(usersId, HttpStatus.OK);
       }

       @GetMapping
    public ResponseEntity<List<Users>> list(){
           List<Users> users =  this.usersService.getAllUsers();
           return new ResponseEntity<>(users, HttpStatus.OK);
       }




       @PostMapping("/sendEmail")
     public String sendEmail(){
           emailProducerTeste.publishMessageEmailTeste();
           return "Email Teste enviado.";

       }

}
