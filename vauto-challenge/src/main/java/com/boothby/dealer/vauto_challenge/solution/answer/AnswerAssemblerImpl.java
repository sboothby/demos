package com.boothby.dealer.vauto_challenge.solution.answer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.boothby.dealer.vauto_challenge.api.model.Answer;
import com.boothby.dealer.vauto_challenge.api.model.DealerAnswer;
import com.boothby.dealer.vauto_challenge.api.model.DealersResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehicleAnswer;
import com.boothby.dealer.vauto_challenge.api.model.VehicleResponse;
import com.boothby.dealer.vauto_challenge.api.model.VehiclesAndDealers;

public class AnswerAssemblerImpl implements AnswerAssembler {

    @Override
    public Answer groupVehiclesWithDealers(VehiclesAndDealers vehiclesAndDealers) {
        // Create Answer, which groups each dealer to all it's vehicles.
        Map<Integer, DealerAnswer> dealerAnswerMap = new HashMap<Integer, DealerAnswer>();
        for (Entry<Integer, VehicleResponse> vehicleEntry : vehiclesAndDealers.getVehicleMap().entrySet()) {
            VehicleAnswer vehicleAnswer = getVehicleAnswer(vehicleEntry.getValue());
            DealersResponse dealerResponseForVehicle = vehiclesAndDealers.getDealerMap().get(vehicleEntry.getValue().getDealerId());
            DealerAnswer dealerAnswer = null;
            if (dealerAnswerMap.containsKey(dealerResponseForVehicle.getDealerId())) {
                dealerAnswer = dealerAnswerMap.get(dealerResponseForVehicle.getDealerId());
            } else {
                dealerAnswer = getDealerAnswer(dealerResponseForVehicle);
                dealerAnswerMap.put(dealerResponseForVehicle.getDealerId(), dealerAnswer);
            }
            dealerAnswer.addVehiclesItem(vehicleAnswer);
        }
        // Put list of answers together for response.
        List<DealerAnswer> dealerAnswerList = dealerAnswerMap.values()
            .stream()
            .collect(Collectors.toList());
        Answer answer = new Answer();
        answer.setDealers(dealerAnswerList);
        return answer;
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
