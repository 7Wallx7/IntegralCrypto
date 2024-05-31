package com.integralCrypto.spring.transaction.dto;


import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TransactionDTO {

	@NotNull
	private Long portfolioId;

	@NotNull
	private Long coinId;

	@NotNull
	@Min(value = 0, message = "Amount should be greater than zero")
	private BigDecimal amount;

	@NotNull
	@Min(value = 0, message = "Price should be greater than zero")
	private BigDecimal price;

	public Long getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(Long portfolioId) {
		this.portfolioId = portfolioId;
	}

	public Long getCoinId() {
		return coinId;
	}

	public void setCoinId(Long coinId) {
		this.coinId = coinId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}