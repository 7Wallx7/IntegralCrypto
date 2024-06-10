package com.integralCrypto.spring.coin.repository;

import com.integralCrypto.spring.coin.models.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Long> {

	Coin findBySymbol (String symbol);

	boolean existsBySymbol (String symbol);

}
