package com.boothby.car.rental.api.core;

import com.boothby.car.rental.api.core.exception.InsuranceException;
import com.boothby.car.rental.api.core.model.InsuranceBinder;
import com.boothby.car.rental.api.core.model.RentalCarRequest;

public interface InsuranceBinderService {
	
	/**
	 * Gets a default insurance policy for the rental request.
	 * @param rentalCarRequest rental request details
	 * @return default insurance policy
	 * @throws InsuranceException thrown when driver can't qualify for default insurance
	 */
	InsuranceBinder getDefaultInsurance(RentalCarRequest rentalCarRequest) throws InsuranceException;
	
	/**
	 * Confirm existing insurance on the request.
	 * @param rentalCarRequest
	 * @throws InsuranceException thrown when driver existing insurance is invalid for the rental terms
	 */
	void validateDriverInsurance(RentalCarRequest rentalCarRequest) throws InsuranceException;
}
