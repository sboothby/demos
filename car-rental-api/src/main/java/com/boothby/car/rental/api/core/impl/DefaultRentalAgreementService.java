package com.boothby.car.rental.api.core.impl;

import org.springframework.stereotype.Service;

import com.boothby.car.rental.api.core.RentalAgreementService;
import com.boothby.car.rental.api.core.exception.RentalValidationException;
import com.boothby.car.rental.api.core.model.RentalCarRequest;
import com.boothby.car.rental.api.core.model.RentalContingencies;
import com.boothby.car.rental.api.core.model.RentalContract;

@Service("defaultRentalAgreementService")
public class DefaultRentalAgreementService implements RentalAgreementService {

	@Override
	public RentalContract createContract(RentalCarRequest rentalCarRequest, RentalContingencies contingencies)
			throws RentalValidationException {
		// TODO Auto-generated method stub
		return null;
	}

}
