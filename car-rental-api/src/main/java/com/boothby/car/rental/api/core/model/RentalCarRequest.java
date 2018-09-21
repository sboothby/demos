package com.boothby.car.rental.api.core.model;

public class RentalCarRequest {

	private String make;
	private String model;
	private DriverInfo driverInfo;
	private int durationDays;
	private InsuranceBinder driverProvidedInsurance;
	private float depositAmount;
	
	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getDurationDays() {
		return durationDays;
	}

	public void setDurationDays(int durationDays) {
		this.durationDays = durationDays;
	}

	public DriverInfo getDriverInfo() {
		return driverInfo;
	}

	public void setDriverInfo(DriverInfo driverInfo) {
		this.driverInfo = driverInfo;
	}

	public InsuranceBinder getDriverProvidedInsurance() {
		return driverProvidedInsurance;
	}

	public void setDriverProvidedInsurance(InsuranceBinder driverProvidedInsurance) {
		this.driverProvidedInsurance = driverProvidedInsurance;
	}

	public float getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(float depositAmount) {
		this.depositAmount = depositAmount;
	}
}
