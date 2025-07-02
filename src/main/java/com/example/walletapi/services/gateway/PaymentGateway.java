package com.example.walletapi.services.gateway;
import com.example.walletapi.models.BankAccount;

import java.math.BigDecimal;
/**
 * Strategy interface for implementing different payment gateways
 * (e.g., Flutterwave, Paystack).
 */
public interface PaymentGateway {
    String getName();
    void fund(BankAccount account, BigDecimal amount);
}