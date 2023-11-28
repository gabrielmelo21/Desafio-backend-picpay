package com.picpaysimplificado.picpaysimplificado.domain.transactions;

import com.picpaysimplificado.picpaysimplificado.domain.users.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "transactions")
@Data
@NoArgsConstructor
@Table(name = "transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal amount;

    @JoinColumn(name = "sender_id")
    @ManyToOne
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Users receiver;

    private LocalDateTime timestamp;



}
