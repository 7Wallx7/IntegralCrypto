package com.integralCrypto.spring.coin.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CoinDTO {


	@NotEmpty
	@Size(min = 1, max = 15)
		private String symbol;

	public CoinDTO () {
	}

	public CoinDTO (String symbol) {
		this.symbol = symbol;
	}


	public String getSymbol () {
		return symbol;
	}

	public void setSymbol (String symbol) {
		this.symbol = symbol;
	}
}
