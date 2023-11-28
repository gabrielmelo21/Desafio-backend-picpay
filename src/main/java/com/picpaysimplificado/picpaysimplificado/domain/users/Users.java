package com.picpaysimplificado.picpaysimplificado.domain.users;

import com.picpaysimplificado.picpaysimplificado.dtos.UsersDTO;
import jakarta.persistence.*;


import lombok.*;


import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "users")
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "firstName", length = 40, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = 40, nullable = false)
    private String lastName;

    @Column(name = "cpf", length = 15, nullable = false, unique = true)
    private String cpf;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "userType")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public Users(UsersDTO data){
        this.firstName = data.firstName();
        this.lastName = data.lastName();
        this.cpf = data.cpf();
        this.email = data.email();
        this.password = data.password();
        this.balance = data.balance();
        this.userType = data.userType();
    }


}
