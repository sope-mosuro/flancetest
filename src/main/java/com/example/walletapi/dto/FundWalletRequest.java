package com.example.walletapi.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class FundWalletRequest {
    @NotBlank
    private String accountNumber;

    @NotBlank
    private String gateway;

    @NotBlank
    private String BankName;

    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;


}
