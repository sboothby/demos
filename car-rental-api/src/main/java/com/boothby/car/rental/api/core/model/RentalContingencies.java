package com.boothby.car.rental.api.core.model;

public class RentalContingencies {

	private InsuranceBinder insurance;
	private float driverScore;
	private Car identifiedCar;

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

	public Car getIdentifiedCar() {
		return identifiedCar;
	}

	public void setIdentifiedCar(Car identifiedCar) {
		this.identifiedCar = identifiedCar;
	}
}
