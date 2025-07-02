package com.example.walletapi.repositories;
import com.example.walletapi.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWalletId(Long walletId);
}