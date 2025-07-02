package com.example.walletapi.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BankRequest {
    @NotBlank
    private String accountName;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String bank;

}
