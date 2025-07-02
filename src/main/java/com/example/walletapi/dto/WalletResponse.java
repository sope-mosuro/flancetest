package com.example.walletapi.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletResponse {
    private Long id;
    private String email;
    private String phone;
    private BigDecimal balance;
}
