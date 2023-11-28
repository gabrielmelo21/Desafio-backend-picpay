package com.picpaysimplificado.picpaysimplificado.repositories;

import com.picpaysimplificado.picpaysimplificado.domain.transactions.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {

}
