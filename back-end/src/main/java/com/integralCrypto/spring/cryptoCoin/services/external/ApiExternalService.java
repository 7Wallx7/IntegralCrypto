package com.integralCrypto.spring.cryptoCoin.services.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiExternalService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${coinmarketcap.external.api.url}")
	private String url;

	@Value("${coinmarketcap.external.api.key}")
	private String apiKey;

	public String getDataListCoin() {
		String fullUrl = url + "/v1/cryptocurrency/map";

		// Setup headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-CMC_PRO_API_KEY", apiKey);
		headers.set("Accept", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(headers);

		// Make the request
		ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class);
		return response.getBody();
	}
}
