package com.integralCrypto.spring.coindetails.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
public class CoinDetailsConfig {
	@Bean
    public RestTemplate restTemplateCoinDetails() {
        return new RestTemplate();
    }
	
}
