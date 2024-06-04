package com.integralCrypto.spring.ohlc.services.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class OHLCApiExternalService {

	@Autowired
	@Qualifier("restTemplateOHLC")
	private final RestTemplate restTemplate;

	@Value("${gateio.external.api.url}")
	private String url;

	public static String pair;

	@Value("${ohlc.timeframe}")
	private String interval;

	@Autowired
	public OHLCApiExternalService(@Qualifier("restTemplateOHLC") RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getOHLCByCoin(String symbol, Long timestamp) {
		pair = "USDT";
		String fullUrl = url + "/spot/candlesticks?currency_pair=" + symbol + "_" + pair;
		fullUrl += "&interval="+interval;
		
		if (timestamp != null) {
			fullUrl += "&from=" + timestamp;
		}
		// Setup headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");

		HttpEntity<String> entity = new HttpEntity<>(headers);
	
		try {
		
			// Make the request
			ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class);
			return response.getBody();
		} catch (HttpClientErrorException e) {
			// Handle the bad request error
			if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
				// Change the symbol to use "_ETH" instead of "_USDT"
				pair = "ETH";
				fullUrl = url + "/spot/candlesticks?currency_pair=" + symbol + "_" + pair;
				fullUrl += "&interval="+interval;
				
				if (timestamp != null) {
					fullUrl += "&from=" + timestamp;
				}

				// Retry the request with the updated URL
				ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class);
				return response.getBody();
			} else {
				// For other types of errors, rethrow the exception
				throw e;
			}
		}
	}

}
