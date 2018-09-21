package com.boothby.car.rental.api.core.impl;

import org.springframework.stereotype.Service;

import com.boothby.car.rental.api.core.RentalService;
import com.boothby.car.rental.api.core.exception.CarNotFoundException;
import com.boothby.car.rental.api.core.exception.DriverException;
import com.boothby.car.rental.api.core.exception.InsuranceException;
import com.boothby.car.rental.api.core.exception.RentalValidationException;
import com.boothby.car.rental.api.core.model.RentalCarRequest;
import com.boothby.car.rental.api.core.model.RentalContract;

@Service("defaultRentalService")
public class DefaultRentalService implements RentalService {

	@Override
	public RentalContract rentCar(RentalCarRequest rentalCarRequest) throws RentalValidationException, DriverException, 
			CarNotFoundException, InsuranceException {
		// TODO Auto-generated method stub
		return null;
	}

	private void validateRentalRequest(RentalCarRequest rentalCarRequest) throws RentalValidationException {
	   //TODO Check car rental dates

	   //TODO Check deposit
	   if (rentalCarRequest.getDepositAmount() <= 0) {
	      throw new RentalValidationException("No deposit was given by the driver.  No deposit, no rental!");  
	    }

	   //TODO Check name fields

	   //TODO Check date ranges
	}
}
