package com.integralCrypto.spring.cryptoCoin.services.external;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.integralCrypto.spring.cryptoCoin.models.Coin;
import com.integralCrypto.spring.cryptoCoin.repository.CoinRepository;

@Service
public class DataProcessService {
	@Autowired
	private CoinRepository cryptoDataRepository;

	@Autowired
	private ApiExternalService apiService;

	

	//Periodicamente cada día a las 09.00 AM
	@Scheduled(cron = "0 9 0 * * *")
	public void processApiDataPeriodically() {
		try {
			String jsonData = apiService.getDataListCoin();
			processApiData(jsonData);
			System.out.println("Data saved to database successfully.");
		} catch (JSONException e) {
			System.err.println("Error parsing JSON data: " + e.getMessage());
		}
	}


	public void processApiData(String jsonData) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonData);

		JSONArray dataArray = jsonObject.getJSONArray("data");

		for (int i = 0; i < dataArray.length(); i++) {
			JSONObject dataObject = dataArray.getJSONObject(i);


			Long idExternal = dataObject.getLong("id");
			String name = dataObject.getString("name");
			String symbol = dataObject.getString("symbol");

			//Settear
			Coin  cryptoData = new Coin();
            cryptoData.setId(idExternal);
            cryptoData.setName(name);
            cryptoData.setSymbol(symbol);

            cryptoDataRepository.save(cryptoData);
		}
	}
}