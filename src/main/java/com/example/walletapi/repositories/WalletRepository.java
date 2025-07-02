package com.example.walletapi.repositories;
import com.example.walletapi.models.Wallet;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;


import java.util.Optional;
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByEmail(String email);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM Wallet w WHERE w.id = :id")
    Optional<Wallet> findByIdForUpdate(@Param("id") Long id);
}
