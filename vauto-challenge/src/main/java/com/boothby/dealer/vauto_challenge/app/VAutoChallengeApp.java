package com.boothby.dealer.vauto_challenge.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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

public class VAutoChallengeApp {

	private static Logger logger = LogManager.getLogger(VAutoChallengeApp.class);
	private ExecutorService executor;
	
	/**
 		Create a program that retrieves a datasetID, retrieves all vehicles and dealers for that dataset, and successfully 
 		posts to the answer endpoint. Each vehicle and dealer should be requested only once. You will receive a response 
 		structure when you post to the answer endpoint that describes status and total elapsed time; your program should
 		output this response.	  
	 */
	
	public VAutoChallengeApp() {
		executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
	}
	
	public void shutdown() {
		executor.shutdown();
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
			try {
				// Get all the vehicle identifiers.
				VehiclesApi vehiclesApi = new VehiclesApi();
				VehicleIdsResponse vehicleIdsResponse = vehiclesApi.vehiclesGetIds(datasetId);
				List<Integer> vehicleIdList = vehicleIdsResponse.getVehicleIds();
				// Asynchronously, get all vehicles details, each only once.
				List<Integer> uniqueVehicleIdList = vehicleIdList.stream().distinct().collect(Collectors.toList());
				List<Future<VehicleResponse>> vehicleResponseFutureList = new ArrayList<Future<VehicleResponse>>();
				final String finalDatasetId = datasetId;
				for (Integer uniqueVehicleId : uniqueVehicleIdList) {
					// Note: trying out CompletableFuture instead of the VehicleTask (Callable) approach.
					CompletableFuture<VehicleResponse> vehicleResponseFuture = 
						CompletableFuture.supplyAsync(new Supplier<VehicleResponse>() {
							@Override
							public VehicleResponse get() {
								VehicleResponse vehicleResponse = null;
								try {
									vehicleResponse = vehiclesApi.vehiclesGetVehicle(finalDatasetId, uniqueVehicleId);
								} catch (ApiException e) {
									e.printStackTrace();
								}
								return vehicleResponse;
							}
						}, executor);
					vehicleResponseFutureList.add(vehicleResponseFuture);
				}
				// Keep track of vehicles.
				//TODO - the Future.get will block on each API call; move this to parallelStream?
				Map<Integer, VehicleResponse> vehicleMap = new HashMap<Integer, VehicleResponse>();
				for (Future<VehicleResponse> vehicleResponseFuture : vehicleResponseFutureList) {
					try {
						VehicleResponse vehicleResponse = vehicleResponseFuture.get();
						vehicleMap.put(vehicleResponse.getVehicleId(), vehicleResponse);
					} catch (InterruptedException | ExecutionException e) {
						logger.error(e);
					}
				}
				// Asynchronously, get all dealers, each only once.
				Set<Integer> uniqueDealerIdSet = new HashSet<Integer>();
				for (Entry<Integer, VehicleResponse> entry : vehicleMap.entrySet()) {
					uniqueDealerIdSet.add(entry.getValue().getDealerId());
				}
				List<Future<DealersResponse>> dealersResponseFutureList = new ArrayList<Future<DealersResponse>>();
				DealersApi dealersApi = new DealersApi();
				for (Integer uniqueDealerId : uniqueDealerIdSet) {
					// Note: trying out CompletableFuture instead of the DealersTask (Callable) approach.
					CompletableFuture<DealersResponse> futureDealersResponse = 
						CompletableFuture.supplyAsync(new Supplier<DealersResponse>() {
							@Override
							public DealersResponse get() {
								DealersResponse dealersResponse = null;
								try {
									dealersResponse = dealersApi.dealersGetDealer(finalDatasetId, uniqueDealerId);
								} catch (ApiException e) {
									e.printStackTrace();
								}
								return dealersResponse;
							}
						}, executor);
					dealersResponseFutureList.add(futureDealersResponse);
				}
				// Keep track of dealers.
				//TODO - the Future.get will block on each API call; move this to parallelStream?
				Map<Integer, DealersResponse> dealerMap = new HashMap<Integer, DealersResponse>();
				for (Future<DealersResponse> futureDealersResponse : dealersResponseFutureList) {
					DealersResponse dealersResponse;
					try {
						dealersResponse = futureDealersResponse.get();
						dealerMap.put(dealersResponse.getDealerId(), dealersResponse);
					} catch (InterruptedException | ExecutionException e) {
						logger.error(e);
					}
				}
				// Create Answer, which groups each dealer to all it's vehicles.
				Map<Integer, DealerAnswer> dealerAnswerMap = new HashMap<Integer, DealerAnswer>();
				for (Entry<Integer, VehicleResponse> vehicleEntry : vehicleMap.entrySet()) {
					VehicleAnswer vehicleAnswer = getVehicleAnswer(vehicleEntry.getValue());
					DealersResponse dealerForVehicle = dealerMap.get(vehicleEntry.getValue().getDealerId());
					DealerAnswer dealerAnswer = null;
					if (dealerAnswerMap.containsKey(dealerForVehicle.getDealerId())) {
						dealerAnswer = dealerAnswerMap.get(dealerForVehicle.getDealerId());
					} else {
						dealerAnswer = new DealerAnswer();
						dealerAnswer.setDealerId(dealerForVehicle.getDealerId());
						dealerAnswer.setName(dealerForVehicle.getName());
						dealerAnswerMap.put(dealerForVehicle.getDealerId(), dealerAnswer);
					}
					dealerAnswer.addVehiclesItem(vehicleAnswer);
				}
				List<DealerAnswer> dealerAnswerList = dealerAnswerMap.values().stream().collect(Collectors.toList());
				Answer answer = new Answer();
				answer.setDealers(dealerAnswerList);
				
				// Post to answer endpoint.
				AnswerResponse answerResponse = datasetApi.dataSetPostAnswer(datasetId, answer);
				// Output answer response (status, total elapsed time)
				logger.info(String.format("Status: %s, total elasped time (sec): %3.2f seconds",
					answerResponse.getMessage(), (float)answerResponse.getTotalMilliseconds()/1000.0f));
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get a vehicle answer object from the API response.
	 * @param vehicleResponse response from Vehicles API
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
	 * @param dealersResponse response from Dealers API
	 * @param vehicleAnswerList vehicle answers list
	 * @return dealer answer of grouped vehicles
	 */
	private DealerAnswer getDealerAnswer(DealersResponse dealersResponse, final List<VehicleAnswer> vehicleAnswerList) {
		DealerAnswer dealerAnswer = new DealerAnswer();
		dealerAnswer.setDealerId(dealersResponse.getDealerId());
		dealerAnswer.setName(dealersResponse.getName());
		dealerAnswer.setVehicles(vehicleAnswerList);
		return dealerAnswer;
	}
	
	public static void main(String[] args) {
		VAutoChallengeApp app = new VAutoChallengeApp();
		app.process();
		app.shutdown();
	}
}
