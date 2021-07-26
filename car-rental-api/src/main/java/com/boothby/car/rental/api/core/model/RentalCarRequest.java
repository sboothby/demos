package com.boothby.car.rental.api.core.model;

import java.util.Date;

public class RentalCarRequest {

	private String make;
	private String model;
	private DriverInfo driverInfo;
	private int durationDays;
	private Date startDate;
	private InsuranceBinder driverProvidedInsurance;
	private float depositAmount;
	private int searchMilesOut;
	private VehicleClass vehicleClass;
	
	public RentalCarRequest() {
		vehicleClass = VehicleClass.UNKNOWN;
	}
	
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

	public boolean hasInsurance() {
		return (getDriverProvidedInsurance() != null);
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getSearchMilesOut() {
		return searchMilesOut;
	}

	public void setSearchMilesOut(int searchMilesOut) {
		this.searchMilesOut = searchMilesOut;
	}

	public VehicleClass getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(VehicleClass vehicleClass) {
		this.vehicleClass = vehicleClass;
	}
}
