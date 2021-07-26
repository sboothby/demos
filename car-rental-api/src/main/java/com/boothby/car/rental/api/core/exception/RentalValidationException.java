package com.boothby.car.rental.api.core.exception;

/**
 * Thrown when the rental request has invalid data.
 */
public class RentalValidationException extends Exception {

	public RentalValidationException(String message) {
		super(message);
	}
}
