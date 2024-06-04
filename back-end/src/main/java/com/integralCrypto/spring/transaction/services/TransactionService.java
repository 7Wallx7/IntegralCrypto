package com.integralCrypto.spring.transaction.services;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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

	public TransactionDTO registerBuyTransaction (TransactionDTO transactionDTO) {
		Portfolio portfolio = portfolioRepository.findById(transactionDTO.getPortfolioId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid portfolio ID"));
		Coin coin = coinRepository.findById(transactionDTO.getCoinId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid coin ID"));

		Transaction transaction = new Transaction();
		transaction.setPortfolio(portfolio);
		transaction.setCoin(coin);
		transaction.setType(TransactionType.BUY);
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setPrice(transactionDTO.getPrice());
		transaction.setTimestamp(getCurrentUnixTimestampInMinutes());

		Transaction savedTransaction = transactionRepository.save(transaction);
		return toDTO(savedTransaction);
	}

	public TransactionDTO registerSellTransaction(TransactionDTO transactionDTO) {
		Portfolio portfolio = portfolioRepository.findById(transactionDTO.getPortfolioId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid portfolio ID"));
		Coin coin = coinRepository.findById(transactionDTO.getCoinId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid coin ID"));

		Transaction transaction = new Transaction();
		transaction.setPortfolio(portfolio);
		transaction.setCoin(coin);
		transaction.setType(TransactionType.SELL);
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setPrice(transactionDTO.getPrice());
		transaction.setTimestamp(getCurrentUnixTimestampInMinutes());

		Transaction savedTransaction = transactionRepository.save(transaction);
		return toDTO(savedTransaction); // Convert to DTO before returning
	}

	public void deleteTransaction (Long transactionId) {
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + transactionId));
		transactionRepository.delete(transaction);
	}

	public List<TransactionDTO> getTransactionsByPortfolio(Long portfolioId) {
		Portfolio portfolio = portfolioRepository.findById(portfolioId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid portfolio ID"));

		List<Transaction> transactions = transactionRepository.findByPortfolio(portfolio);
		List<TransactionDTO> transactionDTOs = transactions.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
		return transactionDTOs;
	}

	public TransactionDTO updateTransaction(Long transactionId, TransactionDTO transactionDTO) {
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

		Transaction updatedTransaction = transactionRepository.save(transaction);
		return toDTO(updatedTransaction); // Convert to DTO before returning
	}

	public long getCurrentUnixTimestampInMinutes () {
		Instant instant = Instant.now();
		return instant.truncatedTo(ChronoUnit.MINUTES).getEpochSecond();
	}

	public TransactionDTO toDTO (Transaction transaction) {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setPortfolioId(transaction.getPortfolio().getId());
		transactionDTO.setCoinId(transaction.getCoin().getId());
		transactionDTO.setAmount(transaction.getAmount());
		transactionDTO.setPrice(transaction.getPrice());
		return transactionDTO;
	}
}

