package com.boothby.car.rental.api.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.boothby.car.rental.api.core.model.DriverInfo;
import com.boothby.car.rental.api.core.model.InsuranceBinder;
import com.boothby.car.rental.api.core.model.RentalCarRequest;

public class RentalModelBuilder {

	private static final String SDF_FORMAT = "MM/dd/yyyy";
	private DateFormat df;
	
	private RentalCarRequest request;
	private InsuranceBinder defaultInsurance;
	
	public RentalModelBuilder() {
		this.df = new SimpleDateFormat(SDF_FORMAT);
	}
	
	public RentalModelBuilder buildRentalCarRequest(String make, String model, int durationDays, float depositAmount, int searchMilesOut) {
		request = new RentalCarRequest();
		request.setMake(make);
		request.setModel(model);
		request.setStartDate(new Date());
		request.setDepositAmount(depositAmount);
		request.setSearchMilesOut(searchMilesOut);
		return this;
	}
	
	public RentalModelBuilder buildRentalCarRequestDriver(String firstName, String lastName, String dob, String stateCode) {
		if (request != null) {
			DriverInfo driverInfo = new DriverInfo();
			driverInfo.setFirstName(firstName);
			driverInfo.setLastName(lastName);
			driverInfo.setDob(parseDate(dob));
			driverInfo.setStateCode(stateCode);
			request.setDriverInfo(driverInfo);
		}
		return this;
	}
	
	public RentalModelBuilder buildRentalCarRequestDriverInsurance(String carrier, String startDateCoverage, String endDateCoverage, 
																   float personalLiabilityAmount, float collisionAmount) {
		if (request != null) {
			InsuranceBinder insBinder = new InsuranceBinder();
			insBinder.setCarrier(carrier);
			insBinder.setStartDateCoverage(parseDate(startDateCoverage));
			insBinder.setEndDateCoverage(parseDate(endDateCoverage));
			insBinder.setPersonalLiabilityAmount(personalLiabilityAmount);
			insBinder.setCollisionAmount(collisionAmount);
			request.setDriverProvidedInsurance(insBinder);
		}
		return this;
	}
	
	public RentalCarRequest getRentalCarRequest() {
		return request;
	}
	
	public RentalModelBuilder buildDefaultInsurance(String carrier, String startDateCoverage, String endDateCoverage, 
												float personalLiabilityAmount, float collisionAmount, float policyCost) {
		defaultInsurance = new InsuranceBinder();
		defaultInsurance.setCarrier(carrier);
		defaultInsurance.setPersonalLiabilityAmount(personalLiabilityAmount);
		defaultInsurance.setCollisionAmount(collisionAmount);
		defaultInsurance.setPolicyCost(policyCost);
		defaultInsurance.setStartDateCoverage(parseDate(startDateCoverage));
		defaultInsurance.setEndDateCoverage(parseDate(endDateCoverage));
		defaultInsurance.setInsuranceValidated(true);
		return this;
	}
	
	public InsuranceBinder getDefaultInsurance() {
		return defaultInsurance;
	}
	
	private Date parseDate(String dateStr) {
		Date date = null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}	
}
