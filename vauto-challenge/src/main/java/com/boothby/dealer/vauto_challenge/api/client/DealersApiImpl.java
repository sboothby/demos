package com.boothby.dealer.vauto_challenge.api.client;

import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.DealersResponse;
import com.boothby.dealer.vauto_challenge.config.AppConfig;

public class DealersApiImpl implements DealersApi {

    private AppConfig appConfig;
    
    public DealersApiImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public DealersResponse getDealer(String datasetId, int dealerId) throws ApiException {
        // TODO Auto-generated method stub
        return null;
    }

}
