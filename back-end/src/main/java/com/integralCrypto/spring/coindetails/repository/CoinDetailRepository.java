package com.integralCrypto.spring.coindetails.repository;

import com.integralCrypto.spring.coin.models.Coin;
import com.integralCrypto.spring.coindetails.model.CoinDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinDetailRepository extends JpaRepository<CoinDetail, Long> {
	Optional<CoinDetail> findByCoinId(Long coinId);
}
