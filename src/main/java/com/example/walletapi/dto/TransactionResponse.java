package com.example.walletapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private String accountNumber;
    private String bankName;
    private String gateway;
    private LocalDateTime timestamp;
}
