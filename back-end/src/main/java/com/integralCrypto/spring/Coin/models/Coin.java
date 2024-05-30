package com.integralCrypto.spring.Coin.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table (name="coin")		
public class Coin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	@NotBlank
	private String symbol;

	public Coin () {
	}

	public Coin (Long id,String symbol) {
		this.id = id;
		this.symbol = symbol;
	}

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}



	public String getSymbol () {
		return symbol;
	}

	public void setSymbol (String symbol) {
		this.symbol = symbol;
	}

}
