package com.boothby.dealer.vauto_challenge.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import com.boothby.dealer.vauto_challenge.solution.answer.AnswerAssembler;
import com.boothby.dealer.vauto_challenge.solution.answer.AnswerAssemblerImpl;
import com.boothby.dealer.vauto_challenge.solution.completable_future.CompletableFutureSolution;
import com.boothby.dealer.vauto_challenge.solution.completable_future.VehicleProvider;
import com.boothby.dealer.vauto_challenge.solution.completable_future.VehicleProviderImpl;
import com.boothby.dealer.vauto_challenge.solution.parallel_stream.ParallelStreamSolution;

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
    public CompletableFutureSolution completableFutureSolution(DataSetApi dataSetApi, VehicleProvider vehicleProvider, AnswerAssembler answerAssembler) {
        return new CompletableFutureSolution(dataSetApi, vehicleProvider, answerAssembler);
    }
    
    @Bean
    public VehicleProvider vehicleProvider(VehiclesApi vehiclesApi, DealersApi dealersApi, ExecutorService executorService) {
        return new VehicleProviderImpl(vehiclesApi, dealersApi, executorService);
    }
    
    @Bean
    public AnswerAssembler answerAssembler() {
        return new AnswerAssemblerImpl();
    }
    
    @Bean
    public ParallelStreamSolution parallelStreamSolution(DataSetApi dataSetApi, VehiclesApi vehiclesApi, DealersApi dealersApi,
            AnswerAssembler answerAssembler) {
        return new ParallelStreamSolution(dataSetApi, vehiclesApi, dealersApi, answerAssembler);
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }
}
