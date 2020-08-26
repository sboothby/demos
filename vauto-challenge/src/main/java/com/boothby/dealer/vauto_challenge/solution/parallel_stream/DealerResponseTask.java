package com.boothby.dealer.vauto_challenge.solution.parallel_stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boothby.dealer.vauto_challenge.api.client.DealersApi;
import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.DealersResponse;

public class DealerResponseTask {

    private static Logger logger = LogManager.getLogger(DealerResponseTask.class);

    private DealersApi dealersApi;
    private String datasetId;
    private int dealerId;

    public DealerResponseTask(DealersApi dealersApi, String datasetId, int dealerId) {
        this.dealersApi = dealersApi;
        this.datasetId = datasetId;
        this.dealerId = dealerId;
    }

    public DealersResponse getDealerResponse() {
        DealersResponse dealersResponse = null;
        try {
            dealersResponse = dealersApi.getDealer(datasetId, dealerId);
        } catch (ApiException e) {
            logger.error(e.getMessage());
        }
        return dealersResponse;
    }
}
