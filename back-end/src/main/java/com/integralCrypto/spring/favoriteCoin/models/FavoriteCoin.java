package com.integralCrypto.spring.favoriteCoin.models;


import com.integralCrypto.spring.coin.models.Coin;
import com.integralCrypto.spring.login.models.User;
import jakarta.persistence.*;

@Entity
@Table (name = "favorite_coin")
public class FavoriteCoin {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "coin_id")
	private Coin coin;

	@ManyToOne
	@JoinColumn (name = "user_id", nullable = false)
	private User user;

	public FavoriteCoin () {
	}

	public FavoriteCoin (Long id, Coin coin, User user) {
		this.id = id;
		this.coin = coin;
		this.user = user;
	}

	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public Coin getCoin () {
		return coin;
	}

	public void setCoin (Coin coin) {
		this.coin = coin;
	}

	public User getUser () {
		return user;
	}

	public void setUser (User user) {
		this.user = user;
	}


}
