package com.boothby.dealer.vauto_challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.boothby.dealer.vauto_challenge.api.client.DataSetApi;
import com.boothby.dealer.vauto_challenge.api.client.DataSetApiImpl;
import com.boothby.dealer.vauto_challenge.api.client.DealersApi;
import com.boothby.dealer.vauto_challenge.api.client.DealersApiImpl;
import com.boothby.dealer.vauto_challenge.api.client.VehiclesApi;
import com.boothby.dealer.vauto_challenge.api.client.VehiclesApiImpl;
import com.boothby.dealer.vauto_challenge.service.VAutoChallenge_CompletableFuture;
import com.boothby.dealer.vauto_challenge.service.VAutoChallenge_OOP;
import com.boothby.dealer.vauto_challenge.service.VAutoChallenge_ParallelStream;

@Configuration
public class ConfigBeans {
    
    @Bean
    public AppConfig appConfig(@Value("${api.base.url}") String apiBaseUrl,
                               @Value("${api.solution}") String apiSolution) {
        AppConfig appConfig = new AppConfig();
        appConfig.setApiBaseUrl(apiBaseUrl);
        appConfig.setApiSolution(apiSolution);
        return appConfig;
    }
    
    @Bean
    public DataSetApi dataSetApi(AppConfig appConfig, RestTemplate restTemplate) {
        return new DataSetApiImpl(appConfig, restTemplate);
    }
    
    @Bean
    public VehiclesApi vehiclesApi(AppConfig appConfig, RestTemplate restTemplate) {
        return new VehiclesApiImpl(appConfig, restTemplate);
    }
    
    @Bean
    public DealersApi dealersApi(AppConfig appConfig, RestTemplate restTemplate) {
        return new DealersApiImpl(appConfig, restTemplate);
    }
    
    @Bean
    public VAutoChallenge_CompletableFuture vAutoChallenge_CompletableFuture(DataSetApi dataSetApi, VehiclesApi vehiclesApi, DealersApi dealersApi) {
        return new VAutoChallenge_CompletableFuture(dataSetApi, vehiclesApi, dealersApi);
    }
    
    @Bean
    public VAutoChallenge_ParallelStream vAutoChallenge_ParallelStream(DataSetApi dataSetApi, VehiclesApi vehiclesApi, DealersApi dealersApi) {
        return new VAutoChallenge_ParallelStream(dataSetApi, vehiclesApi, dealersApi);
    }
    
    @Bean
    public VAutoChallenge_OOP vAutoChallenge_OOP(DataSetApi dataSetApi, VehiclesApi vehiclesApi, DealersApi dealersApi) {
        return new VAutoChallenge_OOP(dataSetApi, vehiclesApi, dealersApi);
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
