package com.boothby.car.rental.api.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.boothby.car.rental.api.core.model.DriverInfo;
import com.boothby.car.rental.api.core.model.InsuranceBinder;
import com.boothby.car.rental.api.core.model.RentalCar;
import com.boothby.car.rental.api.core.model.RentalCarRequest;
import com.boothby.car.rental.api.core.model.RentalContingencies;
import com.boothby.car.rental.api.core.model.RentalContract;
import com.boothby.car.rental.api.core.model.VehicleClass;

public class RentalModelBuilder {

	private static final String SDF_FORMAT = "MM/dd/yyyy";
	private static DateFormat df = new SimpleDateFormat(SDF_FORMAT);
	
	private RentalCarRequest request;
	private InsuranceBinder defaultInsurance;
	private RentalCar rentalCar;
	private RentalContract rentalContract;
	private RentalContingencies contingencies;
	
	public RentalModelBuilder buildRentalCarRequestByClass(VehicleClass vehicleClass, int durationDays, float depositAmount, int searchMilesOut) {
		request = new RentalCarRequest();
		request.setVehicleClass(vehicleClass);
		request.setStartDate(new Date());	// this will end up being the start date on the contract
		request.setDurationDays(durationDays);
		request.setDepositAmount(depositAmount);
		request.setSearchMilesOut(searchMilesOut);
		return this;
	}
	
	public RentalModelBuilder buildRentalCarRequest(String make, String model, int durationDays, float depositAmount, int searchMilesOut) {
		request = new RentalCarRequest();
		request.setMake(make);
		request.setModel(model);
		request.setStartDate(new Date());	// this will end up being the start date on the contract
		request.setDurationDays(durationDays);
		request.setDepositAmount(depositAmount);
		request.setSearchMilesOut(searchMilesOut);
		return this;
	}
	
	public RentalModelBuilder buildRentalCarRequestDriver(String firstName, String lastName, String dob, String stateCode, String driverLicenseNumber) {
		if (request != null) {
			DriverInfo driverInfo = new DriverInfo();
			driverInfo.setFirstName(firstName);
			driverInfo.setLastName(lastName);
			driverInfo.setDob(parseDate(dob));
			driverInfo.setStateCode(stateCode);
			driverInfo.setDriverLicenseNumber(driverLicenseNumber);
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

	public RentalModelBuilder buildDefaultInsurance(String carrier, Date startDateCoverage, int numDaysRental, 
												float personalLiabilityAmount, float collisionAmount, float policyCost) {
		defaultInsurance = new InsuranceBinder();
		defaultInsurance.setCarrier(carrier);
		defaultInsurance.setPersonalLiabilityAmount(personalLiabilityAmount);
		defaultInsurance.setCollisionAmount(collisionAmount);
		defaultInsurance.setPolicyCost(policyCost);
		
		defaultInsurance.setStartDateCoverage(startDateCoverage);
		
		Calendar c = Calendar.getInstance();
		c.setTime(startDateCoverage);
		c.add(Calendar.DATE, numDaysRental);
		Date endDateCoverage = c.getTime();
		defaultInsurance.setEndDateCoverage(endDateCoverage);
		
		defaultInsurance.setInsuranceValidated(true);
		return this;
	}

	public RentalModelBuilder buildRentalCar(String carId, String make, String model, VehicleClass vehicleClass, int year, int miles, String locationCode) {
		rentalCar = new RentalCar();
		rentalCar.setCarId(carId);
		rentalCar.setMake(make);
		rentalCar.setModel(model);
		rentalCar.setVehicleClass(vehicleClass);
		rentalCar.setYear(year);
		rentalCar.setMiles(miles);
		rentalCar.setLocationCode(locationCode);
		return this;
	}
	
	public RentalModelBuilder buildContingencies(InsuranceBinder insurance, float driverScore, RentalCar identifiedCar) {
		contingencies = new RentalContingencies();
		contingencies.setDriverScore(driverScore);
		contingencies.setIdentifiedCar(identifiedCar);
		contingencies.setInsurance(insurance);
		return this;
	}
	
	public RentalModelBuilder buildRentalContract(String contractId, Date startDate, int durationDays, DriverInfo driverInfo, RentalContingencies contingencies) {
		rentalContract = new RentalContract();
		rentalContract.setContractId(contractId);
		rentalContract.setStartDate(startDate);
		rentalContract.setDurationDays(durationDays);
		rentalContract.setDriverInfo(driverInfo);
		rentalContract.setContingencies(contingencies);
		return this;
	}
	
	public RentalCarRequest getRentalCarRequest() {
		return request;
	}
	
	public InsuranceBinder getDefaultInsurance() {
		return defaultInsurance;
	}
	
	public RentalCar getRentalCar() {
		return rentalCar;
	}
	
	public RentalContract getRentalContract() {
		return rentalContract;
	}
	
	public RentalContingencies getContingencies() {
		return contingencies;
	}
	
	public static Date parseDate(String dateStr) {
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
