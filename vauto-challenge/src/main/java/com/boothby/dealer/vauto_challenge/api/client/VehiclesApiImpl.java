package com.boothby.dealer.vauto_challenge.api.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.VehicleIdsResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehicleResponse;
import com.boothby.dealer.vauto_challenge.config.AppConfig;

public class VehiclesApiImpl implements VehiclesApi {

    private AppConfig appConfig;
    private RestTemplate restTemplate;

    public VehiclesApiImpl(AppConfig appConfig, RestTemplate restTemplate) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public VehicleIdsResponse getIds(String datasetId) throws ApiException {
        ResponseEntity<VehicleIdsResponse> response;
        try {
            response = restTemplate.getForEntity(String.format("%s/%s/vehicles", appConfig.getApiBaseUrl(), datasetId), VehicleIdsResponse.class);
        } catch (RestClientException e) {
            throw new ApiException("Error received getting vehicle ids", e);
        }
        return response.getBody();
    }

    @Override
    public VehicleResponse getVehicle(String datasetId, int vehicleId) throws ApiException {
        ResponseEntity<VehicleResponse> response;
        try {
            response = restTemplate.getForEntity(String.format("%s/%s/vehicles/%d", appConfig.getApiBaseUrl(), datasetId, vehicleId), VehicleResponse.class);
        } catch (RestClientException e) {
            throw new ApiException("Error received getting vehicle details", e);
        }
        return response.getBody();
    }

}
