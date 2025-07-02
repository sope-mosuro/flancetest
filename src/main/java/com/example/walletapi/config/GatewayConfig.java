package com.example.walletapi.config;

import com.example.walletapi.services.gateway.PaymentGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * Map of supported payment gateways, keyed by uppercase gateway name (e.g., "FLUTTERWAVE").
 * This allows easy extension of new gateway implementations using the strategy pattern.
 */
@Configuration
public class GatewayConfig {


    @Bean
    public Map<String, PaymentGateway> gatewayMap(List<PaymentGateway> gateways) {
        return gateways.stream()
                .collect(Collectors.toMap(PaymentGateway::getName, gateway -> gateway));
    }
}

