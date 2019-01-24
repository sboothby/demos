package com.boothby.dealer.vauto_challenge.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boothby.dealer.vauto_challenge.client.api.DataSetApi;
import com.boothby.dealer.vauto_challenge.client.api.DealersApi;
import com.boothby.dealer.vauto_challenge.client.api.VehiclesApi;
import com.boothby.dealer.vauto_challenge.client.api.io.swagger.client.ApiException;
import com.boothby.dealer.vauto_challenge.client.api.io.swagger.client.model.Answer;
import com.boothby.dealer.vauto_challenge.client.api.io.swagger.client.model.AnswerResponse;
import com.boothby.dealer.vauto_challenge.client.api.io.swagger.client.model.DatasetIdResponse;
import com.boothby.dealer.vauto_challenge.client.api.io.swagger.client.model.DealerAnswer;
import com.boothby.dealer.vauto_challenge.client.api.io.swagger.client.model.DealersResponse;
import com.boothby.dealer.vauto_challenge.client.api.io.swagger.client.model.VehicleAnswer;
import com.boothby.dealer.vauto_challenge.client.api.io.swagger.client.model.VehicleIdsResponse;
import com.boothby.dealer.vauto_challenge.client.api.io.swagger.client.model.VehicleResponse;

public class VAutoChallengeApp_CompletableFuture {

	private static final Integer NUM_EXECUTOR_THREADS = 20;
	
	private static Logger logger = LogManager.getLogger(VAutoChallengeApp_CompletableFuture.class);
	
	/**
	 * Create a program that retrieves a datasetID, retrieves all vehicles and
	 * dealers for that dataset, and successfully posts to the answer endpoint. Each
	 * vehicle and dealer should be requested only once. You will receive a response
	 * structure when you post to the answer endpoint that describes status and
	 * total elapsed time; your program should output this response.
	 */
	private class VehicleDealerResponse {
		VehicleResponse vehicleResponse;
		DealersResponse dealersResponse;
	}
	
	private void process() {
		// Get new dataset id.
		DataSetApi datasetApi = new DataSetApi();
		DatasetIdResponse dsIdResponse = null;
		String datasetId = null;
		try {
			dsIdResponse = datasetApi.dataSetGetDataSetId();
			datasetId = dsIdResponse.getDatasetId();
		} catch (ApiException e) {
			logger.info(e);
		}
		if (StringUtils.isNotEmpty(datasetId)) {
			//ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
			ExecutorService executor = Executors.newFixedThreadPool(NUM_EXECUTOR_THREADS);
			try {
				// Get all the vehicle identifiers.
				VehiclesApi vehiclesApi = new VehiclesApi();
				VehicleIdsResponse vehicleIdsResponse = vehiclesApi.vehiclesGetIds(datasetId);
				List<Integer> vehicleIdList = vehicleIdsResponse.getVehicleIds();
				// Get all vehicles details, each only once.
				List<Integer> uniqueVehicleIdList = vehicleIdList.parallelStream()
						.distinct()
						.collect(Collectors.toList());
				final String finalDatasetId = datasetId;
				// Create all the futures, which are async tasks to get vehicle and it's dealer.  Will make
				// only one(1) call to APIs per vehicle and dealer.
				DealersApi dealersApi = new DealersApi();
				Map<Integer, DealersResponse> dealerMap = new HashMap<Integer, DealersResponse>();
				Map<Integer, VehicleResponse> vehicleMap = new HashMap<Integer, VehicleResponse>();
				List<CompletableFuture<Boolean>> futureList = 
					uniqueVehicleIdList.stream()
						.map(vehicleId ->
							CompletableFuture.supplyAsync(new Supplier<Boolean>() {
								@Override
								public Boolean get() {
									//TODO check vehicleMap if response already gotten for that vehicle id, then won't need the unique vehicle id filtering, above.
									VehicleResponse vehicleResponse = null;
									try {
										vehicleResponse = vehiclesApi.vehiclesGetVehicle(finalDatasetId, vehicleId);
										vehicleMap.put(vehicleId, vehicleResponse);
									} catch (ApiException e) {
										e.printStackTrace();
									}
									DealersResponse dealersResponse = null;
									if (dealerMap.containsKey(vehicleResponse.getDealerId())) {
										dealersResponse = dealerMap.get(vehicleResponse.getDealerId());
									} else {
										try {
											dealersResponse = dealersApi.dealersGetDealer(finalDatasetId, vehicleResponse.getDealerId());
											dealerMap.put(vehicleResponse.getDealerId(), dealersResponse);
										} catch (ApiException e) {
											e.printStackTrace();
										}
									}
									return true;
								}
							}, executor)).collect(Collectors.toList());
				// Asynchronously execute all the tasks, and then we'll have the dealer and vehicle maps at that point,
				// when each thread completes.  We're not interested in the boolean status return values of the future, just
				// the maps of vehicles and dealers, that were created in the asynchronous processing.
				futureList.stream()
					.map(CompletableFuture::join)
					.collect(Collectors.toList());
				// Create Answer, which groups each dealer to all it's vehicles.
				Map<Integer, DealerAnswer> dealerAnswerMap = new HashMap<Integer, DealerAnswer>();
				for (Entry<Integer, VehicleResponse> vehicleEntry : vehicleMap.entrySet()) {
					VehicleAnswer vehicleAnswer = getVehicleAnswer(vehicleEntry.getValue());
					DealersResponse dealerResponseForVehicle = dealerMap.get(vehicleEntry.getValue().getDealerId());
					DealerAnswer dealerAnswer = null;
					if (dealerAnswerMap.containsKey(dealerResponseForVehicle.getDealerId())) {
						dealerAnswer = dealerAnswerMap.get(dealerResponseForVehicle.getDealerId());
					} else {
						dealerAnswer = getDealerAnswer(dealerResponseForVehicle);
						dealerAnswerMap.put(dealerResponseForVehicle.getDealerId(), dealerAnswer);
					}
					dealerAnswer.addVehiclesItem(vehicleAnswer);
				}
				List<DealerAnswer> dealerAnswerList = dealerAnswerMap.values()
					.stream()
					.collect(Collectors.toList());
				Answer answer = new Answer();
				answer.setDealers(dealerAnswerList);
				// Post to answer endpoint.
				AnswerResponse answerResponse = datasetApi.dataSetPostAnswer(datasetId, answer);
				// Output answer response (status, total elapsed time)
				logger.info(String.format("Status: %s, total elapsed time (sec): %3.2f seconds",
						answerResponse.getMessage(), (float) answerResponse.getTotalMilliseconds() / 1000.0f));
				// Shutdown executor
				executor.shutdown();
			} catch (ApiException e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * Get a vehicle answer object from the API response.
	 * 
	 * @param vehicleResponse
	 *            response from Vehicles API
	 * @return vehicle answer with vehicle properties
	 */
	private VehicleAnswer getVehicleAnswer(VehicleResponse vehicleResponse) {
		VehicleAnswer vehicleAnswer = new VehicleAnswer();
		vehicleAnswer.setMake(vehicleResponse.getMake());
		vehicleAnswer.setModel(vehicleResponse.getModel());
		vehicleAnswer.setVehicleId(vehicleResponse.getVehicleId());
		vehicleAnswer.setYear(vehicleResponse.getYear());
		return vehicleAnswer;
	}

	/**
	 * Get a dealer answer object from the API response.
	 * 
	 * @param dealersResponse
	 *            response from Dealers API
	 * @param vehicleAnswerList
	 *            vehicle answers list
	 */
	private DealerAnswer getDealerAnswer(DealersResponse dealersResponse) {
		DealerAnswer dealerAnswer = new DealerAnswer();
		dealerAnswer.setDealerId(dealersResponse.getDealerId());
		dealerAnswer.setName(dealersResponse.getName());
		return dealerAnswer;
	}

	public static void main(String[] args) {
		VAutoChallengeApp_CompletableFuture app = new VAutoChallengeApp_CompletableFuture();
		app.process();
	}
}
