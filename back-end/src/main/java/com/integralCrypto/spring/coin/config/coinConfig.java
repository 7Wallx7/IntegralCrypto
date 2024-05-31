package com.integralCrypto.spring.coin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class coinConfig {
	@Bean
    public RestTemplate restTemplateCoin() {
        return new RestTemplate();
    }
}
