package com.integralCrypto.spring.ohlc.Repository;

import com.integralCrypto.spring.coin.models.Coin;
import com.integralCrypto.spring.ohlc.Model.OHLC;

import org.springframework.data.jpa.repository.JpaRepository;


public interface OHLCRepository  extends JpaRepository<OHLC, Long> {

    
    boolean existsByTimestampAndCoin(String timestamp, Coin coin);
    
    OHLC findFirstByCoinOrderByTimestampDesc(Coin coin);
}
