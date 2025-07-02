package com.example.walletapi.dto;


import lombok.Data;

@Data
public class BankResponse {
    private Long id;
    private String accountName;
    private String accountNumber;
    private String bank;
}