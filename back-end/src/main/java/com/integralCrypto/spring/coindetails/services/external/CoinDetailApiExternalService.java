package com.integralCrypto.spring.coindetails.services.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinDetailApiExternalService {

	@Autowired
	@Qualifier ("restTemplateOHLC")
	private final RestTemplate restTemplate;

	@Value ("${coinmarketcap.external.api.url}")
	private String url;

	@Value ("${coinmarketcap.external.api.key}")
	private String token;

	@Autowired
	public CoinDetailApiExternalService (@Qualifier ("restTemplateCoinDetails") RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getCoinDetail (String symbol) {
		String fullUrl = url + "/v2/cryptocurrency/quotes/latest?symbol=";
		fullUrl += symbol;

		// Setup headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.set("X-CMC_PRO_API_KEY", token);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		try {

			// Make the request
			ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return null;
			/*if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
				token =  "${coinmarketcap.external.api.key}";
				return getCoinDetail(symbol);
			} else {
				return null;
			}*/
		}
	}
}
