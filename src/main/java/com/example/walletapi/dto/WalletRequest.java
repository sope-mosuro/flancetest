package com.example.walletapi.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WalletRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phone;
}
