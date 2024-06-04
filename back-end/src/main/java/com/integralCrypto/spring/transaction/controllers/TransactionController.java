package com.integralCrypto.spring.transaction.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.integralCrypto.spring.transaction.dto.TransactionDTO;
import com.integralCrypto.spring.transaction.models.Transaction;
import com.integralCrypto.spring.transaction.services.TransactionService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@Validated
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/buy")
	public ResponseEntity<TransactionDTO> registerBuyTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
		return ResponseEntity.ok(transactionService.registerBuyTransaction(transactionDTO));
	}

	@PostMapping("/sell")
	public ResponseEntity<TransactionDTO> registerSellTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
		return ResponseEntity.ok(transactionService.registerSellTransaction(transactionDTO));
	}

	@GetMapping("/portfolio/{portfolioId}")
	public ResponseEntity<List<TransactionDTO>> getTransactionsByPortfolio(@PathVariable Long portfolioId) {
		return ResponseEntity.ok(transactionService.getTransactionsByPortfolio(portfolioId));
	}

	@DeleteMapping("/{transactionId}")
	public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
		transactionService.deleteTransaction(transactionId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{transactionId}")
	public ResponseEntity<TransactionDTO> updateTransaction(
			@PathVariable Long transactionId,
			@Valid @RequestBody TransactionDTO transactionDTO) {
		return ResponseEntity.ok(transactionService.updateTransaction(transactionId, transactionDTO));
	}
}