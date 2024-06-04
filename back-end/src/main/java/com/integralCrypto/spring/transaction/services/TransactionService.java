package com.integralCrypto.spring.transaction.services;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.time.Instant;
import java.util.List;

import com.integralCrypto.spring.transaction.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integralCrypto.spring.coin.models.Coin;
import com.integralCrypto.spring.coin.repository.CoinRepository;
import com.integralCrypto.spring.exception.ResourceNotFoundException;
import com.integralCrypto.spring.portfolio.models.Portfolio;
import com.integralCrypto.spring.portfolio.repository.PortfolioRepository;
import com.integralCrypto.spring.transaction.models.*;
import com.integralCrypto.spring.transaction.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private PortfolioRepository portfolioRepository;

	@Autowired
	private CoinRepository coinRepository;

	public Transaction registerBuyTransaction (Long portfolioId, Long coinId, BigDecimal amount, BigDecimal price) {
		Portfolio portfolio = portfolioRepository.findById(portfolioId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid portfolio ID"));
		Coin coin = coinRepository.findById(coinId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid coin ID"));

		Transaction transaction = new Transaction();
		transaction.setPortfolio(portfolio);
		transaction.setCoin(coin);
		transaction.setType(TransactionType.BUY);
		transaction.setAmount(amount);
		transaction.setPrice(price);
		transaction.setTimestamp(getCurrentUnixTimestampInMinutes());

		return transactionRepository.save(transaction);
	}

	public Transaction registerSellTransaction (Long portfolioId, Long coinId, BigDecimal amount, BigDecimal price) {
		Portfolio portfolio = portfolioRepository.findById(portfolioId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid portfolio ID"));
		Coin coin = coinRepository.findById(coinId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid coin ID"));

		Transaction transaction = new Transaction();
		transaction.setPortfolio(portfolio);
		transaction.setCoin(coin);
		transaction.setType(TransactionType.SELL);
		transaction.setAmount(amount);
		transaction.setPrice(price);
		transaction.setTimestamp(getCurrentUnixTimestampInMinutes());

		return transactionRepository.save(transaction);
	}

	public void deleteTransaction (Long transactionId) {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + transactionId));
		transactionRepository.delete(transaction);
	}

	public List<Transaction> getTransactionsByPortfolio (Long portfolioId) {
		Portfolio portfolio = portfolioRepository.findById(portfolioId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid portfolio ID"));

		List<Transaction> transactions = transactionRepository.findByPortfolio(portfolio);
		return transactions;
	}

	public Transaction updateTransaction (Long transactionId, TransactionDTO transactionDTO) {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

		Portfolio portfolio = portfolioRepository.findById(transactionDTO.getPortfolioId())
				.orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));
		Coin coin = coinRepository.findById(transactionDTO.getCoinId())
				.orElseThrow(() -> new ResourceNotFoundException("Coin not found"));

		transaction.setPortfolio(portfolio);
		transaction.setCoin(coin);
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setPrice(transactionDTO.getPrice());
		return transactionRepository.save(transaction);
	}

	public long getCurrentUnixTimestampInMinutes () {
		Instant instant = Instant.now();
		return instant.truncatedTo(ChronoUnit.MINUTES).getEpochSecond();
	}
}

