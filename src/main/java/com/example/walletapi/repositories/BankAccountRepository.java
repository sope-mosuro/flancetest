package com.example.walletapi.repositories;

import com.example.walletapi.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByWalletId(Long walletId);
    Optional<BankAccount> findByAccountNumberAndBank(String accountNumber, String bank);
    Optional<BankAccount> findByWalletIdAndAccountNumberAndBank(Long walletId, String accountNumber, String bankName);
}
