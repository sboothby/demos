package com.boothby.dealer.vauto_challenge.solution.parallel_stream;

import java.util.List;
import java.util.Map;
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
import com.boothby.dealer.vauto_challenge.api.model.DealersResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehicleIdsResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehicleResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehiclesAndDealers;
import com.boothby.dealer.vauto_challenge.solution.answer.AnswerAssembler;

/**
 * Create a program that retrieves a datasetID, retrieves all vehicles and
 * dealers for that dataset, and successfully posts to the answer endpoint. Each
 * vehicle and dealer should be requested only once. You will receive a response
 * structure when you post to the answer endpoint that describes status and
 * total elapsed time; your program should output this response.
 */
public class ParallelStreamSolution {

	private static Logger logger = LogManager.getLogger(ParallelStreamSolution.class);

    private DataSetApi dataSetApi;
    private VehiclesApi vehiclesApi;
    private DealersApi dealersApi;
    private AnswerAssembler answerAssembler;
    
    /**
     * Constructor
     * @param dataSetApi
     * @param vehiclesApi
     * @param dealersApi
     */
    public ParallelStreamSolution(DataSetApi dataSetApi, VehiclesApi vehiclesApi, DealersApi dealersApi,
            AnswerAssembler answerAssembler) {
        this.dataSetApi = dataSetApi;
        this.vehiclesApi = vehiclesApi;
        this.dealersApi = dealersApi;
        this.answerAssembler = answerAssembler;
    }
    
	public void process() {
        logger.info("*** vAuto - Parallel Stream Solution ***");

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
				
				// Init map holder for answer assembler to group the dealers and vehicles.
				VehiclesAndDealers vehiclesAndDealers = new VehiclesAndDealers();
				vehiclesAndDealers.setVehicleMap(vehicleMap);
				vehiclesAndDealers.setDealerMap(dealerMap);
				Answer answer = answerAssembler.groupVehiclesWithDealers(vehiclesAndDealers);

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
}
