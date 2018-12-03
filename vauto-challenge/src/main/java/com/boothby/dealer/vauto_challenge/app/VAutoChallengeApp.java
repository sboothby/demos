package com.boothby.dealer.vauto_challenge.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boothby.dealer.vauto_challenge.client.api.DataSetApi;
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
		// Get new dataset id
		DataSetApi datasetApi = new DataSetApi();
		DatasetIdResponse dsIdResponse = null;
		String datasetId = "";
		try {
			dsIdResponse = datasetApi.dataSetGetDataSetId();
			datasetId = dsIdResponse.getDatasetId();
		} catch (ApiException e) {
			logger.info(e);
		}
		// Keep track of vehicle/dealer to retrieve each only once.
		Map<Integer, VehicleResponse> vehicleMap = new HashMap<Integer, VehicleResponse>();
		Map<Integer, DealersResponse> dealerMap = new HashMap<Integer, DealersResponse>();
		// Retrieve all vehicles and dealers for the dataset
		VehiclesApi vehiclesApi = new VehiclesApi();
		VehicleIdsResponse vehicleIdsResponse = null;
		List<Integer> vehicleIdList = null;
		if (StringUtils.isNotEmpty(datasetId)) {
			try {
				vehicleIdsResponse = vehiclesApi.vehiclesGetIds(datasetId);
				vehicleIdList = vehicleIdsResponse.getVehicleIds();
				// Asynchronously, get all vehicles, each only once.
				List<Integer> uniqueVehicleIdList = vehicleIdList.stream().distinct().collect(Collectors.toList());
				List<Future<VehicleResponse>> vehicleResponseFutureList = new ArrayList<Future<VehicleResponse>>();
				for (Integer vehicleId : uniqueVehicleIdList) {
					Future<VehicleResponse> vehicleResponseFuture = executor.submit(new VehicleTask(datasetId, vehicleId));
					vehicleResponseFutureList.add(vehicleResponseFuture);
				}
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
				for (Integer uniqueDealerId : uniqueDealerIdSet) {
					Future<DealersResponse> futureDealersResponse = executor.submit(new DealersTask(datasetId, uniqueDealerId));
					dealersResponseFutureList.add(futureDealersResponse);
				}
				for (Future<DealersResponse> futureDealersResponse : dealersResponseFutureList) {
					DealersResponse dealersResponse;
					try {
						dealersResponse = futureDealersResponse.get();
						dealerMap.put(dealersResponse.getDealerId(), dealersResponse);
					} catch (InterruptedException | ExecutionException e) {
						logger.error(e);
					}
				}
				// Group vehicles with their dealers
				Map<DealersResponse, List<VehicleResponse>> dealerVehiclesMap = new HashMap<DealersResponse, List<VehicleResponse>>();
				for (Entry<Integer, VehicleResponse> vehicleEntry : vehicleMap.entrySet()) {
					DealersResponse dealersResponse = dealerMap.get(vehicleEntry.getValue().getDealerId());
					List<VehicleResponse> dealerVehicleList = null;
					if (dealerVehiclesMap.containsKey(dealersResponse)) {
						dealerVehicleList = dealerVehiclesMap.get(dealersResponse);
					} else {
						dealerVehicleList = new ArrayList<VehicleResponse>();
						dealerVehiclesMap.put(dealersResponse, dealerVehicleList);
					}
					dealerVehicleList.add(vehicleEntry.getValue());
				}
				// Create Answer, which groups each dealer to all it's vehicles
				Answer answer = null;
				if (dealerVehiclesMap.size() > 0) {
					answer = new Answer();
					for (Entry<DealersResponse, List<VehicleResponse>> dealerVehicleEntry : dealerVehiclesMap.entrySet()) {
						DealersResponse dealersResponse = dealerVehicleEntry.getKey();
						List<VehicleResponse> vehicleResponseList = dealerVehicleEntry.getValue();
						List<VehicleAnswer> vehicleAnswerList = new ArrayList<VehicleAnswer>();
						for (VehicleResponse vehicleResponse : vehicleResponseList) {
							VehicleAnswer vehicleAnswer = getVehicleAnswer(vehicleResponse);
							vehicleAnswerList.add(vehicleAnswer);
						}
						DealerAnswer dealerAnswer = getDealerAnswer(vehicleAnswerList, dealersResponse.getDealerId(), dealersResponse.getName());
						answer.addDealersItem(dealerAnswer);
					}
				}
				// Post to answer endpoint
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
	
	private VehicleAnswer getVehicleAnswer(VehicleResponse vehicleResponse) {
		VehicleAnswer vehicleAnswer = new VehicleAnswer();
		vehicleAnswer.setMake(vehicleResponse.getMake());
		vehicleAnswer.setModel(vehicleResponse.getModel());
		vehicleAnswer.setVehicleId(vehicleResponse.getVehicleId());
		vehicleAnswer.setYear(vehicleResponse.getYear());
		return vehicleAnswer;
	}
	
	private DealerAnswer getDealerAnswer(final List<VehicleAnswer> vehicleAnswerList, int dealerId, String name) {
		DealerAnswer dealerAnswer = new DealerAnswer();
		dealerAnswer.setDealerId(dealerId);
		dealerAnswer.setName(name);
		dealerAnswer.setVehicles(vehicleAnswerList);
		return dealerAnswer;
	}
	
	public static void main(String[] args) {
		VAutoChallengeApp app = new VAutoChallengeApp();
		app.process();
		app.shutdown();
	}
}
