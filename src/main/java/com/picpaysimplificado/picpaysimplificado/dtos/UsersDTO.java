package com.picpaysimplificado.picpaysimplificado.dtos;
import com.picpaysimplificado.picpaysimplificado.domain.users.UserType;

import java.math.BigDecimal;

public record UsersDTO(String firstName, String lastName, String cpf, String email, String password, BigDecimal balance, UserType userType) {
}
