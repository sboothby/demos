package com.boothby.dealer.vauto_challenge.solution.parallel_stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boothby.dealer.vauto_challenge.api.client.VehiclesApi;
import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.VehicleResponse;

public class VehicleResponseTask {

    private static Logger logger = LogManager.getLogger(VehicleResponseTask.class);
    
    private VehiclesApi vehiclesApi;
    private String datasetId;
    private int vehicleId;

    public VehicleResponseTask(VehiclesApi vehiclesApi, String datasetId, int vehicleId) {
        this.vehiclesApi = vehiclesApi;
        this.datasetId = datasetId;
        this.vehicleId = vehicleId;
    }

    public VehicleResponse getVehicleResponse() {
        VehicleResponse vehicleResponse = null;
        try {
            vehicleResponse = vehiclesApi.getVehicle(datasetId, vehicleId);
        } catch (ApiException e) {
            logger.error(e.getMessage());
        }
        return vehicleResponse;
    }
}
