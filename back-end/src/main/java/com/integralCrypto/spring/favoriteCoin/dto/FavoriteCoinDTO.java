package com.integralCrypto.spring.favoriteCoin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class FavoriteCoinDTO {

	@NotNull
    private Long id;

	@NotNull
	private Long userId;

	@NotNull
	private String nameCoin;

	@NotEmpty
	private Long coinId;

	public FavoriteCoinDTO () {
	}

	public FavoriteCoinDTO (Long userId, String nameCoin, Long coinId) {
		this.userId = userId;
		this.nameCoin = nameCoin;
		this.coinId = coinId;
	}

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public Long getUserId () {
		return userId;
	}

	public void setUserId (Long userId) {
		this.userId = userId;
	}

	public String getNameCoin () {
		return nameCoin;
	}

	public void setNameCoin (String nameCoin) {
		this.nameCoin = nameCoin;
	}

	public Long getCoinId () {
		return coinId;
	}

	public void setCoinId (Long coinId) {
		this.coinId = coinId;
	}
}
