package com.example.walletapi.controllers;
import com.example.walletapi.dto.*;
import com.example.walletapi.services.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;


    @PostMapping("/create")
    public ResponseEntity<WalletResponse> createWallet(@Valid @RequestBody WalletRequest request) {
        WalletResponse response = walletService.createWallet(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-email")
    public ResponseEntity<WalletResponse> getWalletByEmail(@RequestParam String email) {
        return walletService.getWalletByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{walletId}/link-bank")
    public ResponseEntity<BankResponse> linkBank(@PathVariable Long walletId, @Valid @RequestBody BankRequest request) {
        BankResponse response = walletService.linkBank(walletId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{walletId}/fund")
    public ResponseEntity<FundWalletResponse> fundWallet(@PathVariable Long walletId, @Valid @RequestBody FundWalletRequest request) {
        FundWalletResponse response = walletService.fundWallet(walletId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{walletId}/banks")
    public ResponseEntity<List<BankResponse>> getBanks(@PathVariable Long walletId) {
        List<BankResponse> responses = walletService.getBanksForWallet(walletId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{walletId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable Long walletId) {
        List<TransactionResponse> responses = walletService.getTransactions(walletId);
        return ResponseEntity.ok(responses);
    }
}
