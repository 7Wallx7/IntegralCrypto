package com.integralCrypto.spring.transaction.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.integralCrypto.spring.transaction.dto.TransactionDTO;
import com.integralCrypto.spring.transaction.models.Transaction;
import com.integralCrypto.spring.transaction.services.TransactionService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/transactions")
@Validated
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/buy")
    public ResponseEntity<Transaction> registerBuyTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = transactionService.registerBuyTransaction(transactionDTO.getPortfolioId(), transactionDTO.getCoinId(), transactionDTO.getAmount(), transactionDTO.getPrice());
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/sell")
    public ResponseEntity<Transaction> registerSellTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = transactionService.registerSellTransaction(transactionDTO.getPortfolioId(), transactionDTO.getCoinId(), transactionDTO.getAmount(), transactionDTO.getPrice());
        return ResponseEntity.ok(transaction);
    }
    
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
    	transactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }
}