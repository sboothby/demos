package com.boothby.car.rental.api.core;

import com.boothby.car.rental.api.core.exception.CarNotFoundException;
import com.boothby.car.rental.api.core.exception.DriverException;
import com.boothby.car.rental.api.core.exception.InsuranceException;
import com.boothby.car.rental.api.core.exception.RentalContractException;
import com.boothby.car.rental.api.core.exception.RentalValidationException;
import com.boothby.car.rental.api.core.model.RentalCarRequest;
import com.boothby.car.rental.api.core.model.RentalContract;

public interface RentalService {

	/**
	 * Rents a new vehicle
	 * @param rentalCarRequest inquiry to rent car for a duration and driver
	 * @return verified new rental contract with all details of the rental
	 * @throws RentalValidationException
	 * @throws DriverException
	 * @throws CarNotFoundException
	 * @throws InsuranceException
	 * @throws RentalContractException
	 */
	RentalContract rentCar(RentalCarRequest rentalCarRequest) throws RentalValidationException, DriverException, CarNotFoundException, 
		InsuranceException, RentalContractException;
}
