package com.example.walletapi.models;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String AccountNumber;
    private String bankName;
    private String gateway;


    @ManyToOne
    private Wallet wallet;

    private LocalDateTime timestamp = LocalDateTime.now();


}
