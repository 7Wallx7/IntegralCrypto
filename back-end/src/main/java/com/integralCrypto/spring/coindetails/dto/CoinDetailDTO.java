package com.integralCrypto.spring.coindetails.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public class CoinDetailDTO {
	private Long id;

	private BigInteger total_supply;

	private BigInteger max_supply;


	private BigInteger fully_diluted_market_cap;


	private BigInteger circulating_supply;


	private BigInteger market_cap;


	private BigInteger volume_24h;

	public CoinDetailDTO () {
	}

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public BigInteger getTotal_supply () {
		return total_supply;
	}

	public void setTotal_supply (BigInteger total_supply) {
		this.total_supply = total_supply;
	}

	public BigInteger getMax_supply () {
		return max_supply;
	}

	public void setMax_supply (BigInteger max_supply) {
		this.max_supply = max_supply;
	}

	public BigInteger getFully_diluted_market_cap () {
		return fully_diluted_market_cap;
	}

	public void setFully_diluted_market_cap (BigInteger fully_diluted_market_cap) {
		this.fully_diluted_market_cap = fully_diluted_market_cap;
	}

	public BigInteger getCirculating_supply () {
		return circulating_supply;
	}

	public void setCirculating_supply (BigInteger circulating_supply) {
		this.circulating_supply = circulating_supply;
	}

	public BigInteger getMarket_cap () {
		return market_cap;
	}

	public void setMarket_cap (BigInteger market_cap) {
		this.market_cap = market_cap;
	}

	public BigInteger getVolume_24h () {
		return volume_24h;
	}

	public void setVolume_24h (BigInteger volume_24h) {
		this.volume_24h = volume_24h;
	}
}
