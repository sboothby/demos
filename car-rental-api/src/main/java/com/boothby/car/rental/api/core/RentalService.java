package com.boothby.car.rental.api.core;

import com.boothby.car.rental.api.core.model.RentalCarRequest;
import com.boothby.car.rental.api.core.model.RentalContract;

public interface RentalService {

	/**
	 * Rents a new car.
	 * @param rentalCarRequest inquiry to rent car for a duration by specific driver
	 * @return new rental contract with all details of the rental
	 */
	RentalContract rentCar(RentalCarRequest rentalCarRequest);
}
