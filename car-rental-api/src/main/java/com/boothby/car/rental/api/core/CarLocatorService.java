package com.boothby.car.rental.api.core;

import com.boothby.car.rental.api.core.exception.CarNotFoundException;
import com.boothby.car.rental.api.core.model.Car;
import com.boothby.car.rental.api.core.model.RentalContract;

public interface CarLocatorService {

	/**
	 * Finds appropriate car for the rental.
	 * @param rentalContract describes all aspects of the rental
	 * @return identified car
	 * @throws CarNotFoundException thrown when car cannot be located
	 */
	Car findRentalCar(RentalContract rentalContract) throws CarNotFoundException;
}
