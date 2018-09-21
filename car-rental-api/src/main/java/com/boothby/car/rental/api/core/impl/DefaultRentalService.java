package com.boothby.car.rental.api.core.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boothby.car.rental.api.core.CarLocatorService;
import com.boothby.car.rental.api.core.DriverHistoryService;
import com.boothby.car.rental.api.core.InsuranceBinderService;
import com.boothby.car.rental.api.core.RentalAgreementService;
import com.boothby.car.rental.api.core.RentalService;
import com.boothby.car.rental.api.core.exception.CarNotFoundException;
import com.boothby.car.rental.api.core.exception.DriverException;
import com.boothby.car.rental.api.core.exception.InsuranceException;
import com.boothby.car.rental.api.core.exception.RentalValidationException;
import com.boothby.car.rental.api.core.model.RentalCar;
import com.boothby.car.rental.api.core.model.InsuranceBinder;
import com.boothby.car.rental.api.core.model.RentalCarRequest;
import com.boothby.car.rental.api.core.model.RentalContingencies;
import com.boothby.car.rental.api.core.model.RentalContract;

@Service("defaultRentalService")
public class DefaultRentalService implements RentalService {

	private static final String ERROR_RENTAL_START_DAY_IN_PAST = "The rental start day (%s) was in the past!";
	private static final String ERROR_RENTAL_SEARCH_MILES_TOO_FAR = "The miles out to find car (%d) exceeds max miles out (%d) for age!";
	private static final String ERROR_RENTAL_DAYS_EXCEEDED = "The requested rental days (%d) exceeds max # days (%d)!";
	private static final String ERROR_MIN_DEPOSIT_NOT_MET = "The driver deposit (%3.2f) does not meet the minimum dollars (%3.2f)!";
	private static final String ERROR_DRIVER_SCORE_TOO_LOW = "Driver DMV score obtained from state (%s) was too low (%3.2f), and does not meet minimum score (%3.2f)!";
	private static final String ERROR_NAME_FIELDS_REQUIRED = "All name fields required!";
	
	private static final float DRIVER_BASE_SCORE = 50.0f;
	private static final int RENTAL_SEARCH_MAX_MILES = 25;
	private static final int MAX_RENTAL_DAYS = 30;
	private static final float RENTAL_MIN_DEPOSIT = 100.0f;
	
	private DriverHistoryService driverHistoryService;
	private CarLocatorService carLocatorService;
	private InsuranceBinderService insuranceBinderService;
	private RentalAgreementService rentalAgreementService;
	
	@Autowired
	public DefaultRentalService(DriverHistoryService driverHistoryService, CarLocatorService carLocatorService, 
								InsuranceBinderService insuranceBinderService, RentalAgreementService rentalAgreementService) {
		this.driverHistoryService = driverHistoryService;
		this.carLocatorService = carLocatorService;
		this.insuranceBinderService = insuranceBinderService;
		this.rentalAgreementService = rentalAgreementService;
	}

	@Override
	public RentalContract rentCar(RentalCarRequest rentalCarRequest)
			throws RentalValidationException, DriverException, CarNotFoundException, InsuranceException {
		// Validate the request, if anything is invalid, terminate processing with
		// exception.
		validateRentalRequest(rentalCarRequest);
		// Resolve all contingencies required for the contract to be approved and
		// created.
		RentalContingencies contingencies = new RentalContingencies();
		// Confirm insurance.
		if (!rentalCarRequest.hasInsurance()) {
			// No insurance, so get default insurance.
			InsuranceBinder defaultInsurance = insuranceBinderService.getDefaultInsurance(rentalCarRequest);
			contingencies.setInsurance(defaultInsurance);
		} else {
			// Validate existing insurance provided by the driver.
			insuranceBinderService.validateDriverInsurance(rentalCarRequest);
			contingencies.setInsurance(rentalCarRequest.getDriverProvidedInsurance());
		}
	    // Check driver/DMV history.
	    float driverScore = driverHistoryService.verifyDMVScore(rentalCarRequest.getDriverInfo());
	    if (driverScore < DRIVER_BASE_SCORE) {
	        throw new DriverException(String.format(ERROR_DRIVER_SCORE_TOO_LOW, rentalCarRequest.getDriverInfo().getStateCode(), 
	        		driverScore, DRIVER_BASE_SCORE));
	    } else {
	    	contingencies.setDriverScore(driverScore); 
	    }
	    // Identify car based on preferences and availability and maximum miles out for agencies.
	    RentalCar identifiedCar = carLocatorService.findRentalCar(rentalCarRequest, RENTAL_SEARCH_MAX_MILES);
	    contingencies.setIdentifiedCar(identifiedCar);	    
	    // Create a rental contract from the rental request and verified contnigent data.
	    RentalContract rentalContract = rentalAgreementService.createContract(rentalCarRequest, contingencies);
	    return rentalContract;	    
	}

	/**
	 * Check for required fields and ranges.
	 * @param rentalCarRequest current car rental request
	 * @throws RentalValidationException thrown when field missing or range exceeded
	 */
	private void validateRentalRequest(RentalCarRequest rentalCarRequest) throws RentalValidationException {
		// Date requested for vehicle can't be in the past.
		Date today = DateUtils.truncate(new Date(), Calendar.DATE);
		if (rentalCarRequest.getStartDate().before(today)) {
			throw new RentalValidationException(String.format(ERROR_RENTAL_START_DAY_IN_PAST, rentalCarRequest.getStartDate().toString()));
		}
		// Duration for rental can't be exceeded.
		if (rentalCarRequest.getDurationDays() > MAX_RENTAL_DAYS) {
			throw new RentalValidationException(String.format(ERROR_RENTAL_DAYS_EXCEEDED, rentalCarRequest.getDurationDays(), MAX_RENTAL_DAYS));
		}
		// Can't find vehicle beyond typical agent search radius.
		if (rentalCarRequest.getSearchMilesOut() > RENTAL_SEARCH_MAX_MILES) {
			throw new RentalValidationException(String.format(ERROR_RENTAL_SEARCH_MILES_TOO_FAR, rentalCarRequest.getSearchMilesOut(), RENTAL_SEARCH_MAX_MILES));
		}
		// Must meet minimum deposit.
		if (rentalCarRequest.getDepositAmount() < RENTAL_MIN_DEPOSIT) {
			throw new RentalValidationException(String.format(ERROR_MIN_DEPOSIT_NOT_MET, rentalCarRequest.getDepositAmount(), ERROR_MIN_DEPOSIT_NOT_MET));
		}
		// Check name fields.
		if (StringUtils.isBlank(rentalCarRequest.getDriverInfo().getFirstName())) {
			throw new RentalValidationException(ERROR_NAME_FIELDS_REQUIRED);
		}
	}
}
