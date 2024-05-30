package com.integralCrypto.spring.Coin.repository;

import com.integralCrypto.spring.Coin.models.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, Long> {

	Coin findBySymbol(String symbol);

	  boolean existsBySymbol(String symbol);
	
}
