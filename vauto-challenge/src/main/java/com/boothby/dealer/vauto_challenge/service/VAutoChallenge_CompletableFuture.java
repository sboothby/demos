package com.boothby.dealer.vauto_challenge.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boothby.dealer.vauto_challenge.api.client.DataSetApi;
import com.boothby.dealer.vauto_challenge.api.client.DealersApi;
import com.boothby.dealer.vauto_challenge.api.client.VehiclesApi;
import com.boothby.dealer.vauto_challenge.api.model.Answer;
import com.boothby.dealer.vauto_challenge.api.model.AnswerResponse;
import com.boothby.dealer.vauto_challenge.api.model.ApiException;
import com.boothby.dealer.vauto_challenge.api.model.DatasetIdResponse;
import com.boothby.dealer.vauto_challenge.api.model.DealerAnswer;
import com.boothby.dealer.vauto_challenge.api.model.DealersResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehicleAnswer;
import com.boothby.dealer.vauto_challenge.api.model.VehicleIdsResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehicleResponse;

/**
 * Create a program that retrieves a datasetID, retrieves all vehicles and
 * dealers for that dataset, and successfully posts to the answer endpoint. Each
 * vehicle and dealer should be requested only once. You will receive a response
 * structure when you post to the answer endpoint that describes status and
 * total elapsed time; your program should output this response.
 */
public class VAutoChallenge_CompletableFuture {

    private static Logger logger = LogManager.getLogger(VAutoChallenge_CompletableFuture.class);

    private static final Integer MAX_EXECUTOR_THREADS = 10;
	
	private DataSetApi dataSetApi;
	private VehiclesApi vehiclesApi;
	private DealersApi dealersApi;
	
	private Map<Integer, DealersResponse> dealerMap = new ConcurrentHashMap<Integer, DealersResponse>();
	private Map<Integer, VehicleResponse> vehicleMap = new ConcurrentHashMap<Integer, VehicleResponse>();
	// Not CPU intensive, mostly network, so increase threads beyond number processors.
	//ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
	private ExecutorService executor = Executors.newFixedThreadPool(MAX_EXECUTOR_THREADS);

	/**
	 * Constructor
	 * @param dataSetApi
	 * @param vehiclesApi
	 * @param dealersApi
	 */
	public VAutoChallenge_CompletableFuture(DataSetApi dataSetApi, VehiclesApi vehiclesApi, DealersApi dealersApi) {
	    this.dataSetApi = dataSetApi;
	    this.vehiclesApi = vehiclesApi;
	    this.dealersApi = dealersApi;
	}
	
	public void process() {
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
				// Get all the vehicle identifiers.
				VehicleIdsResponse vehicleIdsResponse = vehiclesApi.getIds(datasetId);
				logger.info("TIME: " + new Date().getTime());
				List<Integer> vehicleIdList = vehicleIdsResponse.getVehicleIds();
				// Filter out duplicate vehicles, if any.
				List<Integer> uniqueVehicleIdList = vehicleIdList.parallelStream()
					.distinct()
					.collect(Collectors.toList());
				final String finalDatasetId = datasetId;
				// Create all the futures, which are async tasks to get vehicle and it's dealer.  Will make
				// only one(1) call to APIs per vehicle and dealer.
				List<CompletableFuture<Boolean>> futures = uniqueVehicleIdList.stream()
						.map(vehicleId -> getVehicleDealerFuture(vehicleId, finalDatasetId))
						.collect(Collectors.toList());
				// Execute the futures, waiting for them to complete.  Not interested in final list of boolean statuses, just
				// the vehicle and dealer maps that were created in each async task.
				futures.stream()
						.map(CompletableFuture::join)
						.collect(Collectors.toList());
				logger.info("TIME: " + new Date().getTime());
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

	/**
	 * Get a future invocation for vehicle detail with it's dealer info.
	 * @param vehicleId
	 * @param datasetId
	 * @return
	 */
	private CompletableFuture<Boolean> getVehicleDealerFuture(Integer vehicleId, String datasetId) {
		return CompletableFuture.supplyAsync(new Supplier<Boolean>() {
				@Override
				public Boolean get() {
					VehicleResponse vehicleResponse = null;
					try {
						vehicleResponse = vehiclesApi.getVehicle(datasetId, vehicleId);
						vehicleMap.put(vehicleId, vehicleResponse);
					} catch (ApiException e) {
						logger.error(e);
					}
					if (!dealerMap.containsKey(vehicleResponse.getDealerId())) {
						try {
							DealersResponse dealersResponse = dealersApi.getDealer(datasetId, vehicleResponse.getDealerId());
							dealerMap.put(vehicleResponse.getDealerId(), dealersResponse);
						} catch (ApiException e) {
							logger.error(e);
						}
					}
					return true;
				}
		}, executor);
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
}
