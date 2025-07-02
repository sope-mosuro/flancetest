package com.example.walletapi.services;

import com.example.walletapi.dto.*;
import com.example.walletapi.models.*;
import com.example.walletapi.repositories.*;
import com.example.walletapi.services.gateway.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final Map<String, PaymentGateway> gatewayMap;



    public WalletResponse createWallet(WalletRequest request) {
        if (walletRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Wallet already exists for this email.");
        }

        Wallet wallet = new Wallet();
        wallet.setEmail(request.getEmail());
        wallet.setPhone(request.getPhone());
        wallet.setBalance(BigDecimal.ZERO);
        wallet = walletRepository.save(wallet);

        WalletResponse response = new WalletResponse();
        response.setId(wallet.getId());
        response.setEmail(wallet.getEmail());
        response.setPhone(wallet.getPhone());
        response.setBalance(wallet.getBalance());
        return response;
    }

    public Optional<WalletResponse> getWalletByEmail(String email) {
        return walletRepository.findByEmail(email).map(wallet -> {
            WalletResponse response = new WalletResponse();
            response.setId(wallet.getId());
            response.setEmail(wallet.getEmail());
            response.setPhone(wallet.getPhone());
            response.setBalance(wallet.getBalance());
            return response;
        });
    }


    public BankResponse linkBank(Long walletId, BankRequest request) {
        if (bankAccountRepository.findByAccountNumberAndBank(request.getAccountNumber(), request.getBank()).isPresent()) {
            throw new RuntimeException("Bank account already linked.");
        }

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        BankAccount account = new BankAccount();
        account.setWallet(wallet);
        account.setBank(request.getBank());
        account.setAccountName(request.getAccountName());
        account.setAccountNumber(request.getAccountNumber());
        account.setUniqueKey(request.getAccountNumber() + "_" + request.getBank());
        account = bankAccountRepository.save(account);

        BankResponse response = new BankResponse();
        response.setId(account.getId());
        response.setAccountName(account.getAccountName());
        response.setAccountNumber(account.getAccountNumber());
        response.setBank(account.getBank());
        return response

    ;}

    /**
     * Funds the wallet using a linked bank account and a specified payment gateway.
     * Ensures the account is actually linked and the gateway is supported.
     *
     * Transactional to prevent partial updates (e.g., balance updated but transaction not saved).
     */
    @Transactional
    public FundWalletResponse fundWallet(Long walletId, FundWalletRequest request) {
        Wallet wallet = walletRepository.findByIdForUpdate(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        BankAccount account = bankAccountRepository.findByWalletIdAndAccountNumberAndBank(walletId, request.getAccountNumber(), request.getBankName())
                .orElseThrow(() -> new RuntimeException("Bank account not linked to this wallet"));
        String gatewayName = request.getGateway().toUpperCase();
        PaymentGateway gateway = gatewayMap.get(gatewayName);
        if (gateway == null) {
            throw new RuntimeException("Unsupported payment gateway: " + gatewayName);
        }

        // Simulate gateway processing (no actual integration)
        gateway.fund(account, request.getAmount());

        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        walletRepository.save(wallet);

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(request.getAmount());
        transaction.setAccountNumber(request.getAccountNumber());
        transaction.setBankName(request.getBankName());
        transaction.setGateway(request.getGateway());
        transactionRepository.save(transaction);


        FundWalletResponse response = new FundWalletResponse();
        response.setId(wallet.getId());
        response.setAmount(transaction.getAmount());
        response.setAccountNumber(transaction.getAccountNumber());
        response.setBankName(transaction.getBankName());
        response.setGateway(transaction.getGateway());
        return response;

    }
    public List<BankResponse> getBanksForWallet(Long walletId) {
   return bankAccountRepository.findByWalletId(walletId).stream().map(account -> {
            BankResponse response = new BankResponse();
            response.setId(account.getId());
            response.setAccountName(account.getAccountName());
            response.setAccountNumber(account.getAccountNumber());
            response.setBank(account.getBank());
            return response;
        }).collect(Collectors.toList());
    }


    public List<TransactionResponse> getTransactions(Long walletId) {
        return transactionRepository.findByWalletId(walletId)
                .stream()
                .map(transaction -> new TransactionResponse(
                        transaction.getId(),
                        transaction.getAmount(),
                        transaction.getAccountNumber(),
                        transaction.getBankName(),
                        transaction.getGateway(),
                        transaction.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}

