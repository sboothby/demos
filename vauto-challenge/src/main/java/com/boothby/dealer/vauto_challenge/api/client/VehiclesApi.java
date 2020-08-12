package com.boothby.dealer.vauto_challenge.api.client;

import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.VehicleIdsResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehicleResponse;

public interface VehiclesApi {
    
    VehicleIdsResponse getIds(String datasetId) throws ApiException;
    
    VehicleResponse getVehicle(String datasetId, int vehicleId) throws ApiException;
}
