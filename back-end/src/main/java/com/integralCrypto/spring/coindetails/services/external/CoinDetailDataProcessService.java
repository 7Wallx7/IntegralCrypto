package com.integralCrypto.spring.coindetails.services.external;

import com.integralCrypto.spring.coin.models.Coin;
import com.integralCrypto.spring.coin.repository.CoinRepository;
import com.integralCrypto.spring.coindetails.model.CoinDetail;
import com.integralCrypto.spring.coindetails.repository.CoinDetailRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class CoinDetailDataProcessService {
	@Autowired
	private CoinDetailRepository coinDetailRepository;

	@Autowired
	private CoinRepository coinRepository;

	@Autowired
	private CoinDetailApiExternalService apiService;

	private static final int MAX_RETRIES = 5;
	private static final long BASE_WAIT_TIME = 1000; // 1 second

	// Periodically every 15 minutes
	//@Scheduled(cron = "0 */15 * * * *")
	public void processApiDataPeriodically() {
		try {
			List<Coin> list = coinRepository.findAll();
			for (Coin coin : list) {
				processCoinDataWithRetries(coin);
				Thread.sleep(2000); // Increase wait time between requests to 2 seconds
			}
			System.out.println("Data saved to database successfully.");
		} catch (Exception e) {
			System.err.println("Error during periodic data processing: " + e.getMessage());
		}
	}

	private void processCoinDataWithRetries(Coin coin) {
		int retries = 0;
		while (retries < MAX_RETRIES) {
			try {
				processCoinData(coin);
				return; // Exit if successful
			} catch (JSONException e) {
				System.err.println("Error processing API data for coin: " + coin.getSymbol());
				return; // Exit on JSON errors
			} catch (Exception e) {
				retries++;
				if (retries >= MAX_RETRIES) {
					System.err.println("Max retries reached for coin: " + coin.getSymbol());
				} else {
					try {
						long waitTime = BASE_WAIT_TIME * (1 << retries); // Exponential backoff
						System.out.println("Retrying in " + waitTime + "ms");
						Thread.sleep(waitTime);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}
	}

	private void processCoinData(Coin coin) throws Exception {
		String jsonData = apiService.getCoinDetail(coin.getSymbol());
		if (jsonData != null) {
			processApiData(jsonData, coin.getId());
		} else {
			System.out.println("No data for this coin: " + coin.getSymbol() + " " + coin.getId());
		}
	}

	public void processApiData(String jsonData, Long coinId) throws JSONException {
		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			JSONObject dataObject = jsonObject.getJSONObject("data");
			String firstKey = dataObject.keys().next();
			JSONArray jsonArray = dataObject.getJSONArray(firstKey);
			JSONObject jsonFirstObject = jsonArray.getJSONObject(0);

			Optional<Coin> optionalCoin = coinRepository.findById(coinId);
			if (optionalCoin.isPresent()) {
				Coin coin = optionalCoin.get();

				BigInteger totalSupply = jsonFirstObject.isNull("total_supply") ? null :
						BigInteger.valueOf(jsonFirstObject.getLong("total_supply"));
				BigInteger maxSupply = jsonFirstObject.isNull("max_supply") ? null :
						BigInteger.valueOf(jsonFirstObject.getLong("max_supply"));
				BigInteger fullyDilutedMarketCap =
						jsonFirstObject.getJSONObject("quote").getJSONObject("USD").isNull(
								"fully_diluted_market_cap") ? null :
								BigInteger.valueOf(jsonFirstObject.getJSONObject("quote").getJSONObject("USD").getLong(
										"fully_diluted_market_cap"));
				BigInteger circulatingSupply = jsonFirstObject.isNull("circulating_supply") ? null :
						BigInteger.valueOf(jsonFirstObject.getLong("circulating_supply"));
				BigInteger marketCap =
						jsonFirstObject.getJSONObject("quote").getJSONObject("USD").isNull("market_cap") ? null :
								BigInteger.valueOf(jsonFirstObject.getJSONObject("quote").getJSONObject("USD").getLong("market_cap"));
				BigInteger volume24h =
						jsonFirstObject.getJSONObject("quote").getJSONObject("USD").isNull("volume_24h") ? null :
								BigInteger.valueOf(jsonFirstObject.getJSONObject("quote").getJSONObject("USD").getLong("volume_24h"));


				CoinDetail newElement = new CoinDetail();

				newElement.setCoin(coin);
				newElement.setTotal_supply(totalSupply);
				newElement.setMax_supply(maxSupply);
				newElement.setFully_diluted_market_cap(fullyDilutedMarketCap);
				newElement.setCirculating_supply(circulatingSupply);
				newElement.setMarket_cap(marketCap);
				newElement.setVolume_24h(volume24h);

				saveOrUpdateCoinDetail(newElement);
			} else {
				System.out.println("Coin with ID " + coinId + " not found");
			}

		} catch (JSONException e) {
			System.out.println("The id of coin failed is: " + coinId);
		}
	}

	public void saveOrUpdateCoinDetail(CoinDetail coinDetail) {
		Optional<CoinDetail> existingCoinDetail = coinDetailRepository.findByCoinId(coinDetail.getCoin().getId());
		if (existingCoinDetail.isPresent()) {
			CoinDetail existing = existingCoinDetail.get();

			existing.setCirculating_supply(coinDetail.getCirculating_supply());
			existing.setFully_diluted_market_cap(coinDetail.getFully_diluted_market_cap());
			existing.setMarket_cap(coinDetail.getMarket_cap());
			existing.setMax_supply(coinDetail.getMax_supply());
			existing.setTotal_supply(coinDetail.getTotal_supply());
			existing.setVolume_24h(coinDetail.getVolume_24h());
			coinDetailRepository.save(existing);
		} else {
			coinDetailRepository.save(coinDetail);
		}
	}
}
