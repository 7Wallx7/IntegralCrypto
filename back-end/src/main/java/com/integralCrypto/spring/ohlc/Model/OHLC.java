package com.integralCrypto.spring.ohlc.Model;

import com.integralCrypto.spring.coin.models.Coin;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table (name = "ohlc")
public class OHLC {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@NotNull
	@JoinColumn (name = "coin_id", nullable = false)
	private Coin coin;

	@Column
	@NotNull
	private Long  timestamp;

	@Column(precision = 38, scale = 18)
	@NotNull
	private BigDecimal open;

	@Column(precision = 38, scale = 18)
	@NotNull
	private BigDecimal high;

	@Column(precision = 38, scale = 18)
	@NotNull
	private BigDecimal low;

	@Column(precision = 38, scale = 18)
	@NotNull
	private BigDecimal close;

	@Column(precision = 38, scale = 18)
	@NotNull
	private BigDecimal volume;
	
	@Column
	@NotNull
	String pair;

	public OHLC () {
	}

	public OHLC (Long id, Coin coin, Long  timestamp, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, BigDecimal volume,String pair) {
		this.id = id;
		this.coin = coin;
		this.timestamp = timestamp;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.pair=pair;
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

	public Long  getTimestamp () {
		return timestamp;
	}

	public void setTimestamp (Long  timestamp) {
		this.timestamp = timestamp;
	}

	public BigDecimal getOpen () {
		return open;
	}

	public void setOpen (BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getHigh () {
		return high;
	}

	public void setHigh (BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow () {
		return low;
	}

	public void setLow (BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getClose () {
		return close;
	}

	public void setClose (BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getVolume () {
		return volume;
	}

	public void setVolume (BigDecimal volume) {
		this.volume = volume;
	}

	public String getPair() {
		return pair;
	}

	public void setPair(String pair) {
		this.pair = pair;
	}

	

}

