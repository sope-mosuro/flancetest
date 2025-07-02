package com.example.walletapi.dto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FundWalletResponse {
    private Long id;
    private BigDecimal amount;
    private String AccountNumber;
    private String bankName;
    private String gateway;

}
