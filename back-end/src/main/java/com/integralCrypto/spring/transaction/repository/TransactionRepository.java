package com.integralCrypto.spring.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.integralCrypto.spring.transaction.models.*;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}