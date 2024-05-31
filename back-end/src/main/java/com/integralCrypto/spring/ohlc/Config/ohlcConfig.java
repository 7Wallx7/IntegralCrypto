package com.integralCrypto.spring.ohlc.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
public class ohlcConfig {
	@Bean
    public RestTemplate restTemplateOHLC() {
        return new RestTemplate();
    }
	
}
