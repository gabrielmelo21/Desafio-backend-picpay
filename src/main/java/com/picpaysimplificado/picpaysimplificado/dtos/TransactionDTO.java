package com.picpaysimplificado.picpaysimplificado.dtos;


import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO(UUID senderId, UUID receiverId, BigDecimal value) {

}
