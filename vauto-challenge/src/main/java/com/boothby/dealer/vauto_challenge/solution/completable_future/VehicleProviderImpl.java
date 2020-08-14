package com.boothby.dealer.vauto_challenge.solution.completable_future;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boothby.dealer.vauto_challenge.api.client.DealersApi;
import com.boothby.dealer.vauto_challenge.api.client.VehiclesApi;
import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.DealersResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehicleIdsResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehicleResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehiclesAndDealers;

public class VehicleProviderImpl implements VehicleProvider {

    private static Logger logger = LogManager.getLogger(VehicleProviderImpl.class);

    private VehiclesApi vehiclesApi;
    private DealersApi dealersApi;
    private ExecutorService executorService;

    public VehicleProviderImpl(VehiclesApi vehiclesApi, DealersApi dealersApi, ExecutorService executorService) {
        this.vehiclesApi = vehiclesApi;
        this.dealersApi = dealersApi;
        this.executorService = executorService;
    }

    @Override
    public VehiclesAndDealers getVehiclesAndDealers(String datasetId) {
        // Get all the vehicle identifiers.
        try {
            VehicleIdsResponse vehicleIdsResponse = vehiclesApi.getIds(datasetId);
            // logger.info("TIME: " + new Date().getTime());
            List<Integer> vehicleIdList = vehicleIdsResponse.getVehicleIds();
            // Filter out duplicate vehicles, if any.
            List<Integer> uniqueVehicleIdList = vehicleIdList.parallelStream().distinct().collect(Collectors.toList());
            // Retrieve vehicle and dealer results asynchronously.
            VehiclesAndDealers vehiclesAndDealers = new VehiclesAndDealers();
            uniqueVehicleIdList.parallelStream().forEach(vehicleId -> {
                getVehicleAsync(vehicleId, datasetId, vehiclesAndDealers);
            });
            return vehiclesAndDealers;
        } catch (ApiException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    private void getVehicleAsync(Integer vehicleId, String datasetId, VehiclesAndDealers vehiclesAndDealers) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                VehicleResponse vehicleResponse = vehiclesApi.getVehicle(datasetId, vehicleId);
                if (vehicleResponse != null) {
                    vehiclesAndDealers.getVehicleMap().put(vehicleId, vehicleResponse);
                    if (!vehiclesAndDealers.getDealerMap().containsKey(vehicleResponse.getDealerId())) {
                        DealersResponse dealersResponse = dealersApi.getDealer(datasetId,
                                vehicleResponse.getDealerId());
                        vehiclesAndDealers.getDealerMap().put(vehicleResponse.getDealerId(), dealersResponse);
                    }
                }
            } catch (ApiException e) {
                logger.error(e.getMessage());
            }
        }, executorService);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
    }
}