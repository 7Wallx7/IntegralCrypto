package com.integralCrypto.spring.transaction.repository;

import com.integralCrypto.spring.portfolio.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import com.integralCrypto.spring.transaction.models.*;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByPortfolio(Portfolio portfolio);
}