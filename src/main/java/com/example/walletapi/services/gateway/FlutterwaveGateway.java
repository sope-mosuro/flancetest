package com.example.walletapi.services.gateway;
import com.example.walletapi.models.BankAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FlutterwaveGateway implements PaymentGateway {

    @Override
    public String getName() {
        return "FLUTTERWAVE";
    }

    @Override
    public void fund(BankAccount account, BigDecimal amount) {
        System.out.printf("FLUTTERWAVE Funding account %s with ₦%s%n",
                account.getAccountNumber(), amount);
    }
}