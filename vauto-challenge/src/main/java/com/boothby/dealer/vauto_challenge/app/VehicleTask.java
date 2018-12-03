package com.boothby.dealer.vauto_challenge.app;

import java.util.concurrent.Callable;

import com.boothby.dealer.vauto_challenge.client.api.VehiclesApi;
import com.boothby.dealer.vauto_challenge.client.api.io.swagger.client.model.VehicleResponse;

public class VehicleTask implements Callable<VehicleResponse> {

	private VehiclesApi vehiclesApi;
	private String datasetId;
	private int vehicleId;
	
	public VehicleTask(String datasetId, int vehicleId) {
		vehiclesApi = new VehiclesApi();
		this.datasetId = datasetId;
		this.vehicleId = vehicleId;
	}

	@Override
	public VehicleResponse call() throws Exception {
		VehicleResponse vehicleResponse = vehiclesApi.vehiclesGetVehicle(datasetId, vehicleId);
		return vehicleResponse;
	}
}
