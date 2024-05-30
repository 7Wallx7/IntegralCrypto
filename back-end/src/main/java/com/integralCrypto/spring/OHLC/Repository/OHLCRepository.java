package com.integralCrypto.spring.OHLC.Repository;

import com.integralCrypto.spring.Coin.models.Coin;
import com.integralCrypto.spring.OHLC.Model.OHLC;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface OHLCRepository  extends JpaRepository<OHLC, Long> {

    
    boolean existsByTimestampAndCoin(String timestamp, Coin coin);
    
    OHLC findFirstByCoinOrderByTimestampDesc(Coin coin);
}
