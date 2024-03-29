package com.boothby.car.rental.api.core.model;

import java.util.Date;

public class RentalContract {

	private String contractId;
	private Date startDate;
	private int durationDays;
	private DriverInfo driverInfo;
	private RentalContingencies contingencies;

	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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
	public RentalContingencies getContingencies() {
		return contingencies;
	}
	public void setContingencies(RentalContingencies contingencies) {
		this.contingencies = contingencies;
	}
}
