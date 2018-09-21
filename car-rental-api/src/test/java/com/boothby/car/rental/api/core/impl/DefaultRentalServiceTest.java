package com.boothby.car.rental.api.core.impl;

import static org.mockito.Mockito.mock;

import org.junit.Before;

import com.boothby.car.rental.api.core.CarLocatorService;
import com.boothby.car.rental.api.core.DriverHistoryService;
import com.boothby.car.rental.api.core.InsuranceBinderService;
import com.boothby.car.rental.api.core.RentalAgreementService;
import com.boothby.car.rental.api.core.RentalService;
import com.boothby.car.rental.api.core.model.InsuranceBinder;
import com.boothby.car.rental.api.core.model.RentalCar;
import com.boothby.car.rental.api.core.model.RentalCarRequest;
import com.boothby.car.rental.api.core.model.RentalContract;
import com.boothby.car.rental.api.utility.RentalModelBuilder;

public class DefaultRentalServiceTest {

	// Mock dependencies
	private DriverHistoryService driverHistoryService;
	private CarLocatorService carLocatorService;
	private InsuranceBinderService insuranceBinderService;	
	private RentalAgreementService rentalAgreementService;
	
	// Class under test
	private RentalService rentalService;
	
	// Test objects
	private RentalCarRequest testRentalCarRequest;
	private InsuranceBinder testDefaultInsurance;
	private float testDriverScore;
	private RentalCar testIdentifiedCar;
	private RentalContract testContract;
	
	@Before
	public void init() {
		// Create mocks
		driverHistoryService = mock(DriverHistoryService.class);
		carLocatorService = mock(CarLocatorService.class);
		insuranceBinderService = mock(InsuranceBinderService.class);
		rentalAgreementService = mock(RentalAgreementService.class);

		// Create object under test
		rentalService = new DefaultRentalService(driverHistoryService, carLocatorService, 
				insuranceBinderService, rentalAgreementService);
		
		// Create test data
		RentalModelBuilder modelBuilder = new RentalModelBuilder();
		testRentalCarRequest = modelBuilder
			.buildRentalCarRequest("Acura", "MDX", 5, 100.0f, 10)
			.buildRentalCarRequestDriver("Jeremy", "Bull", "12/1/1974", "VT")
			.buildRentalCarRequestDriverInsurance("Atena", "1/1/2018", "1/1/2025", 100000.0f, 10000.f)
			.getRentalCarRequest();
		testDefaultInsurance = modelBuilder.buildDefaultInsurance("Atena", "1/1/2018", "1/1/2025", 1000000.0f, 10000.0f, 25.0f).getDefaultInsurance();
		//TODO other test data objects
	}
}
