package com.boothby.dealer.vauto_challenge.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
public class VAutoChallenge_ParallelStream {

	private static Logger logger = LogManager.getLogger(VAutoChallenge_ParallelStream.class);

    private DataSetApi dataSetApi;
    private VehiclesApi vehiclesApi;
    private DealersApi dealersApi;

    /**
     * Constructor
     * @param dataSetApi
     * @param vehiclesApi
     * @param dealersApi
     */
    public VAutoChallenge_ParallelStream(DataSetApi dataSetApi, VehiclesApi vehiclesApi, DealersApi dealersApi) {
        this.dataSetApi = dataSetApi;
        this.vehiclesApi = vehiclesApi;
        this.dealersApi = dealersApi;
    }
    
    private class VehicleResponseTask {

		private VehiclesApi vehiclesApi;
		private String datasetId;
		private int vehicleId;

		public VehicleResponseTask(VehiclesApi vehiclesApi, String datasetId, int vehicleId) {
			this.vehiclesApi = vehiclesApi;
			this.datasetId = datasetId;
			this.vehicleId = vehicleId;
		}

		public VehicleResponse getVehicleResponse() {
			VehicleResponse vehicleResponse = null;
			try {
				vehicleResponse = vehiclesApi.getVehicle(datasetId, vehicleId);
			} catch (ApiException e) {
				logger.error(e.getMessage());
			}
			return vehicleResponse;
		}
	}

	private class DealerResponseTask {

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
			try {
				// Get all the vehicle identifiers.
				VehicleIdsResponse vehicleIdsResponse = vehiclesApi.getIds(datasetId);
				List<Integer> vehicleIdList = vehicleIdsResponse.getVehicleIds();
				// Get all vehicles details, each only once.
				List<Integer> uniqueVehicleIdList = vehicleIdList.parallelStream()
						.distinct()
						.collect(Collectors.toList());
				final String finalDatasetId = datasetId;
				// Create each vehicle response task.
				List<VehicleResponseTask> vehicleDetailTasks = uniqueVehicleIdList.parallelStream()
						.map(vehicleId -> new VehicleResponseTask(vehiclesApi, finalDatasetId, vehicleId))
						.collect(Collectors.toList());
				// Execute each vehicle response task in parallel, obtaining map of vehicle
				// details.
				Map<Integer, VehicleResponse> vehicleMap = vehicleDetailTasks.parallelStream()
						.map(VehicleResponseTask::getVehicleResponse)
						.collect(Collectors.toMap(vehicleResponse -> vehicleResponse.getVehicleId(), // map key
																	 vehicleResponse -> vehicleResponse)); // map value
				// Get all dealers, each only once.
				List<Integer> uniqueDealerIdList = vehicleMap.entrySet().parallelStream()
						.map(entry -> new Integer(entry.getValue().getDealerId())).distinct()
						.collect(Collectors.toList());
				// Create each dealer response task.
				List<DealerResponseTask> dealerResponseTasks = uniqueDealerIdList.parallelStream()
						.map(dealerId -> new DealerResponseTask(dealersApi, finalDatasetId, dealerId))
						.collect(Collectors.toList());
				// Execute each dealer response task in parallel, obtaining map of dealers.
				Map<Integer, DealersResponse> dealerMap = dealerResponseTasks.parallelStream()
						.map(DealerResponseTask::getDealerResponse)
						.collect(Collectors.toMap(dealerResponse -> dealerResponse.getDealerId(), // map key
																	dealerResponse -> dealerResponse)); // map value
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
				List<DealerAnswer> dealerAnswerList = dealerAnswerMap.values().stream().collect(Collectors.toList());
				Answer answer = new Answer();
				answer.setDealers(dealerAnswerList);
				// Post to answer endpoint.
				AnswerResponse answerResponse = dataSetApi.postAnswer(datasetId, answer);
				// Output answer response (status, total elapsed time)
				logger.info(String.format("Status: %s, total elapsed time (sec): %3.2f seconds",
						answerResponse.getMessage(), (float) answerResponse.getTotalMilliseconds() / 1000.0f));
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
}
