package com.boothby.car.rental.api.core.impl;

import org.springframework.stereotype.Service;

import com.boothby.car.rental.api.core.InsuranceBinderService;
import com.boothby.car.rental.api.core.exception.InsuranceException;
import com.boothby.car.rental.api.core.model.InsuranceBinder;
import com.boothby.car.rental.api.core.model.RentalCarRequest;

@Service("defaultInsuranceBinderService")
public class DefaultInsuranceBinderService implements InsuranceBinderService {

	@Override
	public InsuranceBinder getDefaultInsurance(RentalCarRequest rentalCarRequest) throws InsuranceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkDriverProvidedInsuranceCoverage(RentalCarRequest rentalCarRequest) throws InsuranceException {
		// TODO Auto-generated method stub

	}

}
