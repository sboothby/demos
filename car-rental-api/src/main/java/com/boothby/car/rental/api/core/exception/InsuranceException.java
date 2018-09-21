package com.boothby.car.rental.api.core.exception;

/**
 * Thrown when insurance cannot be obtained or verified for the driver during the rental processing.
 */
public class InsuranceException extends Exception {

	public InsuranceException(String message) {
		super(message);
	}
}
