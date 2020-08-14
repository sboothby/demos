package com.boothby.dealer.vauto_challenge.solution.completable_future;

import com.boothby.dealer.vauto_challenge.api.model.VehiclesAndDealers;

public interface VehicleProvider {

    /**
     * Obtains vehicles and dealer details
     * @param datasetId dataset source for vehicles and dealers
     * @return vehicle and dealer details
     */
    VehiclesAndDealers getVehiclesAndDealers(String datasetId);
}
