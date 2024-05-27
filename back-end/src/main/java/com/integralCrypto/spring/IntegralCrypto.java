package com.integralCrypto.spring;

import org.json.JSONException;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.integralCrypto.spring.cryptoCoin.services.external.ApiExternalService;
import com.integralCrypto.spring.cryptoCoin.services.external.DataProcessService;

@SpringBootApplication
public class IntegralCrypto {

    public static void main(String[] args) {
        SpringApplication.run(IntegralCrypto.class, args);
    }

    @Bean
    public ApplicationRunner initialize(ApiExternalService apiService, DataProcessService processApiData) {
        return args -> {
            // Llama al método para ejecutar el proceso de datos de la API inmediatamente
            executeApiDataProcessing(apiService, processApiData);
        };
    }

    // Método para ejecutar el proceso de datos de la API inmediatamente
    private void executeApiDataProcessing(ApiExternalService apiService, DataProcessService processApiData) {
        try {
            String jsonData = apiService.getDataListCoin();
            processApiData.processApiData(jsonData);
            System.out.println("Data saved to database successfully.");
        } catch (JSONException e) {
            System.err.println("Error parsing JSON data: " + e.getMessage());
        }
    }
}
