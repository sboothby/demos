package com.boothby.dealer.vauto_challenge.api.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VehiclesAndDealers {

    private Map<Integer, VehicleResponse> vehicleMap;
    
    private Map<Integer, DealersResponse> dealerMap;

    public VehiclesAndDealers() {
        vehicleMap = new ConcurrentHashMap<>();
        dealerMap = new ConcurrentHashMap<>();
    }

    public Map<Integer, VehicleResponse> getVehicleMap() {
        return vehicleMap;
    }

    public void setVehicleMap(Map<Integer, VehicleResponse> vehicleMap) {
        this.vehicleMap = vehicleMap;
    }

    public Map<Integer, DealersResponse> getDealerMap() {
        return dealerMap;
    }

    public void setDealerMap(Map<Integer, DealersResponse> dealerMap) {
        this.dealerMap = dealerMap;
    }
}
