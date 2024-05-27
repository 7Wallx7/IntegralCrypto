package com.integralCrypto.spring.cryptoCoin.repository;

import com.integralCrypto.spring.cryptoCoin.models.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, Long> {

}
