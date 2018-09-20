package com.boothby.car.rental.api.core;

import com.boothby.car.rental.api.core.exception.DriverNotFoundException;
import com.boothby.car.rental.api.core.model.DriverInfo;

public interface DriverHistoryService {

	/**
	 * Find the driver at state DMV, and calculate score for car rental.
	 * @param driverInfo all driver details
	 * @return score of driver history
	 * @throws DriverNotFoundException
	 */
	float verifyDMVScore(DriverInfo driverInfo) throws DriverNotFoundException;
	
}

	