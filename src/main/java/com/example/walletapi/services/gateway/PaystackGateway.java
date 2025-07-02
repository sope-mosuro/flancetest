package com.example.walletapi.services.gateway;
import com.example.walletapi.models.BankAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaystackGateway implements PaymentGateway {

    @Override
    public String getName() {
        return "PAYSTACK";
    }

    @Override
    public void fund(BankAccount account, BigDecimal amount) {
        System.out.printf("PAYSTACK Funding account %s with ₦%s%n",
                account.getAccountNumber(), amount);
    }
}
