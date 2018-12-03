package com.boothby.dealer.vauto_challenge.app;

import java.util.concurrent.Callable;

import com.boothby.dealer.vauto_challenge.client.api.DealersApi;
import com.boothby.dealer.vauto_challenge.client.api.io.swagger.client.model.DealersResponse;

public class DealersTask implements Callable<DealersResponse> {

	private DealersApi dealersApi;
	private String datasetId;
	private int dealerId;
	
	public DealersTask(String datasetId, int dealerId) {
		dealersApi = new DealersApi();
		this.datasetId = datasetId;
		this.dealerId = dealerId;
	}

	@Override
	public DealersResponse call() throws Exception {
		DealersResponse dealersResponse = dealersApi.dealersGetDealer(datasetId, dealerId);
		return dealersResponse;
	}
}
