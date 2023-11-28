package com.picpaysimplificado.picpaysimplificado.services;

import com.picpaysimplificado.picpaysimplificado.domain.users.UserType;
import com.picpaysimplificado.picpaysimplificado.domain.users.Users;
import com.picpaysimplificado.picpaysimplificado.dtos.UsersDTO;
import com.picpaysimplificado.picpaysimplificado.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
   @Autowired
   private UsersRepository repository;

   public void validateTransaction(Users sender,Users receiver,  BigDecimal amount) throws Exception{

       // verifica se o usuario sender é lojista - lojistas nao podem enviar dinheiro
        if(sender.getUserType() == UserType.MERCHANT){
             throw new Exception("Usuário do tipo Lojista não está autorizado a realizar transação");
        }

        // verifica se tem saldo suficiente para realizar a transação
        if (sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo Insuficiente");
        }

        // verifica se o o sender nao esta enviado para ele mesmo
       if (sender.getId().equals(receiver.getId())){
           throw new Exception("Você não pode enviar para si mesmo(a).");
       }


   }

    public Users findUserById(UUID id) {
        Optional<Users> usersId = repository.findById(id);
        return usersId.orElse(null);
    }



   public void saveUser(Users user){
        this.repository.save(user);

   }


   public Users createUsers(UsersDTO usersDTO){
         Users users = new Users(usersDTO);
         this.saveUser(users);
         return users;
   }

public List<Users> getAllUsers(){
        return repository.findAll();
}














   }


