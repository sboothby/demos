package com.boothby.dealer.vauto_challenge.api.client;

import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.DealersResponse;

public interface DealersApi {
    
    DealersResponse getDealer(String datasetId, int dealerId) throws ApiException;
}
