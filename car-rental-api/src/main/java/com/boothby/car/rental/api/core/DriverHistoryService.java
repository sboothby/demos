package com.boothby.car.rental.api.core;

import com.boothby.car.rental.api.core.exception.DriverException;
import com.boothby.car.rental.api.core.model.DriverInfo;

public interface DriverHistoryService {

	/**
	 * Checks with the state DMV that the drivers license number is valid and current.
	 * @param driverInfo
	 * @return
	 * @throws DriverException thrown if driver license is invalid, out of date, etc.
	 */
	void verifyDriverLicense(DriverInfo driverInfo) throws DriverException;
	
	/**
	 * Calculates score for car rental, at the state DMV.
	 * @param driverInfo all driver details
	 * @return score of driver history
	 * @throws DriverException thrown if can't obtain the driver score from state DMV
	 */
	float getDriverScore(DriverInfo driverInfo) throws DriverException;
}

	