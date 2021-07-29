package com.boothby.car.rental.api.service;

import com.boothby.car.rental.api.core.exception.RentalContractException;
import com.boothby.car.rental.api.core.model.RentalCarRequest;
import com.boothby.car.rental.api.core.model.RentalContingencies;
import com.boothby.car.rental.api.core.model.RentalContract;

public interface RentalAgreementService {

	/**
	 * Create a rental contract, between driver and rental agency, based on the request.
	 * @param rentalCarRequest all details for the rental, including driver info
	 * @param contingencies items that need verification and follow-up for the contract to be approved
	 * @return valid contract for rental
	 * @throws RentalContractException thrown when finding any problems with the request or contingencies during contract creation
	 */
	RentalContract createContract(RentalCarRequest rentalCarRequest, RentalContingencies contingencies) throws RentalContractException;
}
