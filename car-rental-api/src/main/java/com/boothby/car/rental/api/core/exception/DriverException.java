package com.boothby.car.rental.api.core.exception;

/**
 * Thrown when a driver cannot be found or identified during the car rental processing.
 */
public class DriverException extends Exception {

	public DriverException(String message) {
		super(message);
	}
}
