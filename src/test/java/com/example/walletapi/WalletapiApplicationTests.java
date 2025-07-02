package com.example.walletapi;

import com.example.walletapi.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.walletapi.models.BankAccount;
import com.example.walletapi.models.Transaction;
import com.example.walletapi.models.Wallet;
import com.example.walletapi.repositories.BankAccountRepository;
import com.example.walletapi.repositories.TransactionRepository;
import com.example.walletapi.repositories.WalletRepository;
import com.example.walletapi.services.gateway.PaymentGateway;
import com.example.walletapi.services.WalletService;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;


import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for WalletService methods:
 * - createWallet()
 * - linkBank()
 * - fundWallet()
 * - getBanksForWallet()
 * - getWalletByEmail()
 *
 * Uses Mockito to mock dependencies.
 */

@SpringBootTest
@Transactional
class WalletapiApplicationServiceTests {

	private WalletRepository walletRepo;
	private BankAccountRepository bankRepo;
	private TransactionRepository transactionRepository;
	private PaymentGateway flutterwaveGateway;
	private WalletService walletService;

	@BeforeEach
	void setUp() {
		walletRepo = mock(WalletRepository.class);
		bankRepo = mock(BankAccountRepository.class);
		transactionRepository = mock(TransactionRepository.class);
		flutterwaveGateway = mock(PaymentGateway.class);

		when(flutterwaveGateway.getName()).thenReturn("FLUTTERWAVE");

		Map<String, PaymentGateway> gatewayMap = Map.of("FLUTTERWAVE", flutterwaveGateway);
		walletService = new WalletService(walletRepo, bankRepo, transactionRepository, gatewayMap);
	}

	// ==============================
	// createWallet()
	// ==============================

	@Test
	@DisplayName("Create wallet successfully")
	void testCreateWallet_Success() {
		WalletRequest request = new WalletRequest();
		request.setEmail("test@example.com");
		request.setPhone("08123456789");

		when(walletRepo.findByEmail(request.getEmail())).thenReturn(Optional.empty());
		when(walletRepo.save(any(Wallet.class))).thenAnswer(i -> i.getArguments()[0]);

		WalletResponse wallet = walletService.createWallet(request);

		assertEquals(request.getEmail(), wallet.getEmail());
		assertEquals(BigDecimal.ZERO, wallet.getBalance());
	}


	// ==============================
	// linkBank()
	// ==============================



	@Test
	@DisplayName("Fail to link bank if account already exists")
	void testLinkBank_AccountAlreadyExists() {
		BankRequest request = new BankRequest();
		request.setAccountName("John Doe");
		request.setAccountNumber("1234567890");
		request.setBank("FLUTTERWAVE");

		when(bankRepo.findByAccountNumberAndBank(request.getAccountNumber(), request.getBank()))
				.thenReturn(Optional.of(new BankAccount()));

		RuntimeException ex = assertThrows(RuntimeException.class, () -> walletService.linkBank(1L, request));
		assertTrue(ex.getMessage().contains("already linked"));
	}

	// ==============================
	// fundWallet()
	// ==============================



	@Test
	@DisplayName("Fail to fund wallet with unlinked bank")
	void testFundWallet_BankNotLinked() {
		FundWalletRequest request = new FundWalletRequest();
		request.setAccountNumber("9999999999");
		request.setGateway("FLUTTERWAVE");
		request.setAmount(BigDecimal.valueOf(1000));

		Wallet wallet = new Wallet();
		wallet.setId(2L);

		when(walletRepo.findByIdForUpdate(wallet.getId())).thenReturn(Optional.of(wallet));
		when(bankRepo.findByWalletIdAndAccountNumberAndBank(wallet.getId(), request.getAccountNumber(), request.getGateway()))
				.thenReturn(Optional.empty());

		RuntimeException ex = assertThrows(RuntimeException.class,
				() -> walletService.fundWallet(wallet.getId(), request));

		assertTrue(ex.getMessage().contains("not linked"));
	}


	// ==============================
	// getWalletByEmail()
	// ==============================

	@Test
	@DisplayName("Return wallet by email if it exists")
	void testGetWalletByEmail_Found() {
		Wallet wallet = new Wallet();
		wallet.setEmail("user@example.com");

		when(walletRepo.findByEmail("user@example.com")).thenReturn(Optional.of(wallet));

		Optional<WalletResponse> result = walletService.getWalletByEmail("user@example.com");
		assertTrue(result.isPresent());
		assertEquals("user@example.com", result.get().getEmail());
	}

	@Test
	@DisplayName("Return empty if wallet not found by email")
	void testGetWalletByEmail_NotFound() {
		when(walletRepo.findByEmail("missing@example.com")).thenReturn(Optional.empty());

		Optional<WalletResponse> result = walletService.getWalletByEmail("missing@example.com");
		assertFalse(result.isPresent());
	}

	// ==============================
	// getBanksForWallet()
	// ==============================

	@Test
	@DisplayName("Get all banks linked to wallet")
	void testGetBanksForWallet() {
		List<BankAccount> accounts = List.of(new BankAccount(), new BankAccount());

		when(bankRepo.findByWalletId(1L)).thenReturn(accounts);

		List<BankResponse> result = walletService.getBanksForWallet(1L);
		assertEquals(2, result.size());
	}

}
