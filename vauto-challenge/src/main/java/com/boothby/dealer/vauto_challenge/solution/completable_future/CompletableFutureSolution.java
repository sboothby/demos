package com.boothby.dealer.vauto_challenge.solution.completable_future;

import java.util.concurrent.ExecutorService;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boothby.dealer.vauto_challenge.api.client.DataSetApi;
import com.boothby.dealer.vauto_challenge.api.model.Answer;
import com.boothby.dealer.vauto_challenge.api.model.AnswerResponse;
import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.DatasetIdResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehiclesAndDealers;

/**
 * Create a program that retrieves a datasetID, retrieves all vehicles and
 * dealers for that dataset, and successfully posts to the answer endpoint. Each
 * vehicle and dealer should be requested only once. You will receive a response
 * structure when you post to the answer endpoint that describes status and
 * total elapsed time; your program should output this response.
 */
public class CompletableFutureSolution {

    private static Logger logger = LogManager.getLogger(CompletableFutureSolution.class);

	private DataSetApi dataSetApi;
	private VehicleProvider vehicleProvider;
	private AnswerAssembler answerAssembler;
	
	/**
	 * Constructor
	 * @param dataSetApi
	 * @param vehiclesApi
	 * @param dealersApi
	 */
	public CompletableFutureSolution(DataSetApi dataSetApi, VehicleProvider vehicleProvider,
	        AnswerAssembler answerAssembler) {
	    this.dataSetApi = dataSetApi;
	    this.vehicleProvider = vehicleProvider;
	    this.answerAssembler = answerAssembler;
	}
	
	public void process() {
	    logger.info("*** vAuto - Completable Future Solution ***");
	    
	    // Get new dataset id.
		DatasetIdResponse dsIdResponse = null;
		String datasetId = null;
		try {
			dsIdResponse = dataSetApi.getDataSetId();
			datasetId = dsIdResponse.getDatasetId();
		} catch (ApiException e) {
			logger.info(e);
		}
		if (StringUtils.isNotEmpty(datasetId)) {
			ExecutorService executor = null;
			try {
			    // Get resolved vehicles and resolved dealers.
			    VehiclesAndDealers vehiclesAndDealers = vehicleProvider.getVehiclesAndDealers(datasetId);
			    //logger.info("TIME: " + new Date().getTime());
			    Answer answer = answerAssembler.groupVehiclesWithDealers(vehiclesAndDealers);
			    // Post to answer endpoint.
				AnswerResponse answerResponse = dataSetApi.postAnswer(datasetId, answer);
				// Output answer response (status, total elapsed time)
				logger.info(String.format("Status: %s, total elapsed time (sec): %3.2f seconds",
						answerResponse.getMessage(), (float) answerResponse.getTotalMilliseconds() / 1000.0f));
			} catch (ApiException e) {
				logger.error(e.getMessage());
			} finally {
				// Shutdown executor
				if (executor != null) {
					executor.shutdown();
				}
			}
		}
	}

}
