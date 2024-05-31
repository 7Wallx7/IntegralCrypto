package com.integralCrypto.spring.coin.services.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Qualifier;


@Service
public class ListSymbolApiExternalService {

	@Value ("${gateio.external.api.url}")
	private String url;
	@Autowired
	@Qualifier ("restTemplateCoin") 
	private final RestTemplate restTemplate;

	@Autowired
	public ListSymbolApiExternalService (@Qualifier ("restTemplateCoin") RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}


	public String getSymbolCoin () {
		String fullUrl = url + "/spot/currencies";

		// Setup headers
		HttpHeaders headers = new HttpHeaders();
		//	headers.set("X-CMC_PRO_API_KEY", apiKey);
		headers.set("Accept", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(headers);

		// Make the request
		ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class);
		return response.getBody();
	}
}
