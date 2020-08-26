package com.boothby.dealer.vauto_challenge.solution.answer;

import com.boothby.dealer.vauto_challenge.api.model.Answer;
import com.boothby.dealer.vauto_challenge.api.model.VehiclesAndDealers;

public interface AnswerAssembler {
    
    /**
     * Puts together and matches vehicles with their dealers
     * @param vehiclesAndDealers vehicles and dealer details
     * @return assembled vehicles and dealers
     */
    Answer groupVehiclesWithDealers(VehiclesAndDealers vehiclesAndDealers);
}
