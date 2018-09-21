package com.boothby.car.rental.api.core.exception;

/**
 * Thrown when a rental car cannot be located by the driver preferences.
 */
public class CarNotFoundException extends Exception {

	public CarNotFoundException(String message) {
		super(message);
	}
}
