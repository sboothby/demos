package com.boothby.car.rental.api.core.model;

import java.util.Date;

public class InsuranceBinder {

	private String insuranceBinderId;
	private String carrier;
	private Date startDateCoverage;
	private Date endDateCoverage;
	private float policyCost;
	private float personalLiabilityAmount;
	private float collissionAmount;
	private boolean insuranceValidated;

	public String getInsuranceBinderId() {
		return insuranceBinderId;
	}

	public void setInsuranceBinderId(String insuranceBinderId) {
		this.insuranceBinderId = insuranceBinderId;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public Date getStartDateCoverage() {
		return startDateCoverage;
	}

	public void setStartDateCoverage(Date startDateCoverage) {
		this.startDateCoverage = startDateCoverage;
	}

	public Date getEndDateCoverage() {
		return endDateCoverage;
	}

	public void setEndDateCoverage(Date endDateCoverage) {
		this.endDateCoverage = endDateCoverage;
	}

	public float getPolicyCost() {
		return policyCost;
	}

	public void setPolicyCost(float policyCost) {
		this.policyCost = policyCost;
	}

	public float getPersonalLiabilityAmount() {
		return personalLiabilityAmount;
	}

	public void setPersonalLiabilityAmount(float personalLiabilityAmount) {
		this.personalLiabilityAmount = personalLiabilityAmount;
	}

	public float getCollissionAmount() {
		return collissionAmount;
	}

	public void setCollissionAmount(float collissionAmount) {
		this.collissionAmount = collissionAmount;
	}

	public boolean isInsuranceValidated() {
		return insuranceValidated;
	}

	public void setInsuranceValidated(boolean insuranceValidated) {
		this.insuranceValidated = insuranceValidated;
	}
}
