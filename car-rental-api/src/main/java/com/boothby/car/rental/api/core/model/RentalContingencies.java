package com.boothby.car.rental.api.core.model;

public class RentalContingencies {

	private InsuranceBinder insurance;		// insurance MUST be determined
	private float driverScore;				// driver score MUST be high enough
	private RentalCar identifiedCar;				// car MUST be located

	public InsuranceBinder getInsurance() {
		return insurance;
	}

	public void setInsurance(InsuranceBinder insurance) {
		this.insurance = insurance;
	}

	public float getDriverScore() {
		return driverScore;
	}

	public void setDriverScore(float driverScore) {
		this.driverScore = driverScore;
	}

	public RentalCar getIdentifiedCar() {
		return identifiedCar;
	}

	public void setIdentifiedCar(RentalCar identifiedCar) {
		this.identifiedCar = identifiedCar;
	}
}
