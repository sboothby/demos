package com.boothby.dealer.vauto_challenge.api.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.DealersResponse;
import com.boothby.dealer.vauto_challenge.config.AppConfig;

public class DealersApiImpl implements DealersApi {

    private AppConfig appConfig;
    private RestTemplate restTemplate;
    
    public DealersApiImpl(AppConfig appConfig, RestTemplate restTemplate) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public DealersResponse getDealer(String datasetId, int dealerId) throws ApiException {
        ResponseEntity<DealersResponse> response;
        try {
            response = restTemplate.getForEntity(String.format("%s/%s/dealers/%d", appConfig.getApiBaseUrl(), datasetId, dealerId), DealersResponse.class);
        } catch (RestClientException e) {
            throw new ApiException("Error received getting dealer details", e);
        }
        return response.getBody();
    }
}
