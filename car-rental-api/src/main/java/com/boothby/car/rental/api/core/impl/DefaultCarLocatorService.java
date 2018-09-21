package com.boothby.car.rental.api.core.impl;

import org.springframework.stereotype.Service;

import com.boothby.car.rental.api.core.CarLocatorService;
import com.boothby.car.rental.api.core.exception.CarNotFoundException;
import com.boothby.car.rental.api.core.model.RentalCar;
import com.boothby.car.rental.api.core.model.RentalCarRequest;

@Service("defaultCarLocatorService")
public class DefaultCarLocatorService implements CarLocatorService {

	@Override
	public RentalCar findRentalCar(RentalCarRequest rentalCarRequest, int searchRadiusMiles)
			throws CarNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
