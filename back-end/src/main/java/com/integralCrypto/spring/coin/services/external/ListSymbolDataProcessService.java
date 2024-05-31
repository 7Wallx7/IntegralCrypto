package com.integralCrypto.spring.coin.services.external;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.integralCrypto.spring.coin.models.Coin;
import com.integralCrypto.spring.coin.repository.CoinRepository;

@Service
public class ListSymbolDataProcessService {

	@Autowired
	private CoinRepository cryptoDataRepository;

	@Autowired
	private ListSymbolApiExternalService apiService;


	//Periodicamente cada día a las 09.00 AM
	@Scheduled (cron = "0 9 0 * * *")
	public void processApiDataSymbolListPeriodically () {
		try {
			String jsonData = apiService.getSymbolCoin();
			processApiData(jsonData);
			System.out.println("Data saved to database successfully.");
		} catch (JSONException e) {
			System.err.println("Error parsing JSON data: " + e.getMessage());
		}
	}


	public void processApiData (String jsonData) throws JSONException {
		// Parsear la cadena JSON en un JSONArray
		JSONArray jsonArray = new JSONArray(jsonData);

		// Iterar sobre el JSONArray
		for (int i = 0; i < jsonArray.length(); i++) {
			// Obtener el objeto JSON en la posición i
			JSONObject dataObject = jsonArray.getJSONObject(i);

			//Si el trading esta deshabilitado no guardarla
			if (!dataObject.getBoolean("trade_disabled")) {

				// Obtener el valor de la propiedad "currency"
				String symbol = dataObject.getString("currency");

				// Crear una nueva instancia de Coin y setear el símbolo
				Coin cryptoData = new Coin();
				cryptoData.setSymbol(symbol);
				
				if (!cryptoDataRepository.existsBySymbol(symbol)) {
					// Guardar el objeto Coin en el repositorio
					cryptoDataRepository.save(cryptoData);

					System.out.println("new Element saved.");
				}
			}

		}
	}
}