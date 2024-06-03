package com.integralCrypto.spring.ohlc.services.external;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.integralCrypto.spring.coin.models.Coin;
import com.integralCrypto.spring.coin.repository.CoinRepository;
import com.integralCrypto.spring.ohlc.Model.OHLC;
import com.integralCrypto.spring.ohlc.Repository.OHLCRepository;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;

@Service
public class OHLCDataProcessService {
    @Autowired
    private OHLCRepository ohlcRepository;

    @Autowired
    private CoinRepository coinRespository;

    @Autowired
    private OHLCApiExternalService apiService;

    private final RateLimiter rateLimiter;

    public OHLCDataProcessService() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(2000))
                .limitForPeriod(10)
                .limitRefreshPeriod(Duration.ofMillis(500))
                .build();
        this.rateLimiter = RateLimiter.of("ohlcService", config);
    }

    // Periodically every 15 minutes
    @Scheduled(cron = "0 */15 * * * *")
    public void processApiDataPeriodically() {
        try {
            List<Coin> list = coinRespository.findAll();
            List<CompletableFuture<Void>> futures = list.stream()
                .filter(coin -> !"USDT".equals(coin.getSymbol()))
                .map(coin -> CompletableFuture.runAsync(() -> processCoinData(coin)))
                .collect(Collectors.toList());

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            System.out.println("Data saved to database successfully.");
        } catch (Exception e) {
            System.err.println("Error during periodic data processing: " + e.getMessage());
        }
    }

    private void processCoinData(Coin coin) {
        RateLimiter.decorateRunnable(rateLimiter, () -> {
            try {
                OHLC ohclUltimo = ohlcRepository.findFirstByCoinOrderByTimestampDesc(coin);
                String lastTimestamp = (ohclUltimo != null) ? ohclUltimo.getTimestamp() : null;

                if (lastTimestamp == null) {
                    System.out.println("No previous OHLC data found for coin: " + coin.getSymbol());
                }

                String jsonData = apiService.getOHLCByCoin(coin.getSymbol(), lastTimestamp);
                processApiData(jsonData, coin.getId());
            } catch (JSONException e) {
                System.err.println("Error processing API data for coin: " + coin.getSymbol());
            }
        }).run();
    }

    public void processApiData(String jsonData, Long coinId) throws JSONException {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            Coin coin = coinRespository.findById(coinId).orElseThrow(
                () -> new RuntimeException("No se encontró ninguna moneda con el ID proporcionado: " + coinId)
            );

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray innerArray = jsonArray.getJSONArray(i);
                OHLC newElement = new OHLC();

                for (int j = 0; j < innerArray.length(); j++) {
                    String element = innerArray.getString(j);

                    switch (j) {
                        case 0:
                            newElement.setTimestamp(element);
                            break;
                        case 2:
                            newElement.setOpen(new BigDecimal(element));
                            break;
                        case 3:
                            newElement.setHigh(new BigDecimal(element));
                            break;
                        case 4:
                            newElement.setLow(new BigDecimal(element));
                            break;
                        case 5:
                            newElement.setClose(new BigDecimal(element));
                            break;
                        case 6:
                            newElement.setVolume(new BigDecimal(element));
                            break;
                        default:
                            break;
                    }
                }

                newElement.setCoin(coin);
                newElement.setPair(OHLCApiExternalService.pair);

                if (!ohlcRepository.existsByTimestampAndCoin(newElement.getTimestamp(), coin)) {
                    ohlcRepository.save(newElement);
                    System.out.println("New element saved: " + newElement.getTimestamp() + " " + newElement.getCoin().getSymbol());
                } else {
                    System.out.println("Skipping existing element for timestamp: "+newElement.getCoin().getSymbol()+" " + newElement.getTimestamp());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

