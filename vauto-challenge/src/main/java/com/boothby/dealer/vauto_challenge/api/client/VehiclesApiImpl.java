package com.boothby.dealer.vauto_challenge.api.client;

import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.VehicleIdsResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehicleResponse;
import com.boothby.dealer.vauto_challenge.config.AppConfig;

public class VehiclesApiImpl implements VehiclesApi {

    private AppConfig appConfig;

    public VehiclesApiImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public VehicleIdsResponse getIds(String datasetId) throws ApiException{
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public VehicleResponse getVehicle(String datasetId, int vehicleId) throws ApiException {
        // TODO Auto-generated method stub
        return null;
    }

}
