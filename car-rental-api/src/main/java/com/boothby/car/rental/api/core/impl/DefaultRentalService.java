package com.boothby.car.rental.api.core.impl;

import org.springframework.stereotype.Service;

import com.boothby.car.rental.api.core.RentalService;
import com.boothby.car.rental.api.core.model.RentalCarRequest;
import com.boothby.car.rental.api.core.model.RentalContract;

@Service("defaultRentalService")
public class DefaultRentalService implements RentalService {

	@Override
	public RentalContract rentCar(RentalCarRequest rentalCarRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
