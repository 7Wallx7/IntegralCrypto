package com.integralCrypto.spring;

import org.json.JSONException;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.integralCrypto.spring.Coin.services.external.ListSymbolApiExternalService;
import com.integralCrypto.spring.OHLC.services.external.OHLCDataProcessService;
import com.integralCrypto.spring.Coin.services.external.ListSymbolDataProcessService;


@SpringBootApplication
@EnableScheduling
public class IntegralCrypto {

    public static void main(String[] args) {
        SpringApplication.run(IntegralCrypto.class, args);
    }

    @Bean
    public ApplicationRunner initialize(ListSymbolApiExternalService apiService, ListSymbolDataProcessService processApiData, OHLCDataProcessService ohlcdata) {
        return args -> {
            // Llama al método para ejecutar el proceso de datos de la API inmediatamente
           // executeApiDataProcessing(apiService, processApiData);
            ohlcdata.processApiDataPeriodically();
        };
    }

    // Método para ejecutar el proceso de datos de la API inmediatamente
    private void executeApiDataProcessing(ListSymbolApiExternalService apiService, ListSymbolDataProcessService processApiData) {
        try {
            String jsonData = apiService.getSymbolCoin();
            processApiData.processApiData(jsonData);
            System.out.println("Data saved to database successfully.");
        } catch (JSONException e) {
            System.err.println("Error parsing JSON data: " + e.getMessage());
        }
    }
}
