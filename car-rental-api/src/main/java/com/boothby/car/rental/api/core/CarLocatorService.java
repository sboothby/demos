package com.boothby.car.rental.api.core;

import com.boothby.car.rental.api.core.exception.CarNotFoundException;
import com.boothby.car.rental.api.core.model.RentalCar;
import com.boothby.car.rental.api.core.model.RentalCarRequest;

public interface CarLocatorService {

	/**
	 * Finds appropriate car for the rental.
	 * @param rentalCarRequest request to rent a vehicle
	 * @param searchRadiusMiles how many miles to search out
	 * @return identified car
	 * @throws CarNotFoundException thrown when car cannot be located
	 */
	RentalCar findRentalCar(RentalCarRequest rentalCarRequest, int searchRadiusMiles) throws CarNotFoundException;
}
