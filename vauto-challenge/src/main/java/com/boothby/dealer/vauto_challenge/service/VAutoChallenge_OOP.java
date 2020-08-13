package com.boothby.dealer.vauto_challenge.service;

import com.boothby.dealer.vauto_challenge.api.client.DataSetApi;
import com.boothby.dealer.vauto_challenge.api.client.DealersApi;
import com.boothby.dealer.vauto_challenge.api.client.VehiclesApi;

/**
 * Create a program that retrieves a datasetID, retrieves all vehicles and
 * dealers for that dataset, and successfully posts to the answer endpoint. Each
 * vehicle and dealer should be requested only once. You will receive a response
 * structure when you post to the answer endpoint that describes status and
 * total elapsed time; your program should output this response.
 */
public class VAutoChallenge_OOP {

    private DataSetApi dataSetApi;
    private VehiclesApi vehiclesApi;
    private DealersApi dealersApi;
    
    public VAutoChallenge_OOP(DataSetApi dataSetApi, VehiclesApi vehiclesApi, DealersApi dealersApi) {
        this.dataSetApi = dataSetApi;
        this.vehiclesApi = vehiclesApi;
        this.dealersApi = dealersApi;
    }
    
    public void process() {
        //TODO
    }
}
