package com.boothby.car.rental.api.service;

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
	 * Confirm existing driver insurance has adequate coverage for the rental request.
	 * @param rentalCarRequest
	 * @throws InsuranceException thrown when driver-provided insurance is invalid for the rental terms
	 */
	void checkDriverProvidedInsuranceCoverage(RentalCarRequest rentalCarRequest) throws InsuranceException;
}
