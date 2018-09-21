package com.boothby.car.rental.api.core;

import com.boothby.car.rental.api.core.exception.RentalValidationException;
import com.boothby.car.rental.api.core.model.RentalCarRequest;
import com.boothby.car.rental.api.core.model.RentalContingencies;
import com.boothby.car.rental.api.core.model.RentalContract;

public interface RentalAgreementService {

	/**
	 * Create a rental contract, between driver and rental agency, based on the request.
	 * @param rentalCarRequest all details for the rental, including driver info
	 * @param contingencies items that need verification and follow-up for the contract to be approved
	 * @return valid contract for rental
	 * @throws RentalValidationException thrown when finding any problems with the request during validation
	 */
	RentalContract createContract(RentalCarRequest rentalCarRequest, RentalContingencies contingencies) throws RentalValidationException;
}
