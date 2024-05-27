package com.integralCrypto.spring.cryptoCoin.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table (name="coin")		
public class Coin {

	@Id
	@NotNull
	private Long id;

	@NotBlank
	private String name;

	@NotBlank
	private String symbol;

	public Coin () {
	}

	public Coin (Long id,String name, String symbol) {
		this.id = id;
		this.name = name;
		this.symbol = symbol;
	}

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getSymbol () {
		return symbol;
	}

	public void setSymbol (String symbol) {
		this.symbol = symbol;
	}

}
