package com.boothby.car.rental.api.core.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.boothby.car.rental.api.core.CarLocatorService;
import com.boothby.car.rental.api.core.DriverHistoryService;
import com.boothby.car.rental.api.core.InsuranceBinderService;
import com.boothby.car.rental.api.core.RentalAgreementService;
import com.boothby.car.rental.api.core.RentalService;
import com.boothby.car.rental.api.core.model.DriverInfo;
import com.boothby.car.rental.api.core.model.InsuranceBinder;
import com.boothby.car.rental.api.core.model.RentalCar;
import com.boothby.car.rental.api.core.model.RentalCarRequest;
import com.boothby.car.rental.api.core.model.RentalContingencies;
import com.boothby.car.rental.api.core.model.RentalContract;
import com.boothby.car.rental.api.core.model.VehicleClass;
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
	private RentalCar testIdentifiedCar;
	private RentalContingencies testContingencies;
	private RentalContract testContract;
	
	@Before
	public void init() {
		// Create mocks of the dependent beans.  We'll have the methods return mock data.
		driverHistoryService = mock(DriverHistoryService.class);
		carLocatorService = mock(CarLocatorService.class);
		insuranceBinderService = mock(InsuranceBinderService.class);
		rentalAgreementService = mock(RentalAgreementService.class);

		// Create object under test.
		rentalService = new DefaultRentalService(driverHistoryService, carLocatorService, 
				insuranceBinderService, rentalAgreementService);
		
		// Create test data objects.
		RentalModelBuilder modelBuilder = new RentalModelBuilder();
		modelBuilder
			.buildRentalCarRequestByClass(VehicleClass.SUV, 5, 100.0f, 10)
			.buildRentalCarRequestDriver("Sean", "Donnelly", "12/1/1974", "VT", "ABCDEFG12345")
			.buildDefaultInsurance("Atena",modelBuilder.getRentalCarRequest().getStartDate(), modelBuilder.getRentalCarRequest().getDurationDays(), 
									1000000.0f, 10000.0f, 25.0f)
			.buildRentalCar("MDX1020423212", "Acura", "MDX", modelBuilder.getRentalCarRequest().getVehicleClass(), 2014, 40000, "Enterprise-BVT-123")
			.buildContingencies(modelBuilder.getDefaultInsurance(), 78.0f, modelBuilder.getRentalCar())
			.buildRentalContract("CON-232325561",
								modelBuilder.getRentalCarRequest().getStartDate(), 
								modelBuilder.getRentalCarRequest().getDurationDays(), 
								modelBuilder.getRentalCarRequest().getDriverInfo(), 
								modelBuilder.getContingencies());
		testRentalCarRequest = modelBuilder.getRentalCarRequest();
		testDefaultInsurance = modelBuilder.getDefaultInsurance();
		testIdentifiedCar = modelBuilder.getRentalCar();
		testContingencies = modelBuilder.getContingencies();
		testContract = modelBuilder.getRentalContract();
	}
	
	/**
	* Happy path test that car is rented and reserved through a rental agreement.
	*/ 
	@Test
	public void Should_ReserveCar_For_ValidRentalRequestWithDefaultInsurance() throws Exception {
		// Mock dependent bean calls. These are the calls made from beans within DefaultRentalService.rentCar, that will return default test values,
		// or take no adverse action, etc.
		when(insuranceBinderService.getDefaultInsurance(any(RentalCarRequest.class))).thenReturn(testDefaultInsurance);
		doNothing().when(driverHistoryService).verifyDriverLicense(any(DriverInfo.class));
		when(driverHistoryService.getDriverScore(any(DriverInfo.class))).thenReturn(testContingencies.getDriverScore());
		when(carLocatorService.findRentalCar(any(RentalCarRequest.class), anyInt())).thenReturn(testIdentifiedCar);
		when(rentalAgreementService.createContract(any(RentalCarRequest.class), any(RentalContingencies.class))).thenReturn(testContract);
		// Invoke the class method under test, obtaining a contract, that we'll subsequently verify is correct.
		RentalContract rentalContract = rentalService.rentCar(testRentalCarRequest);
		// A contract should have been returned.
		assertNotNull(rentalContract);
		// The contract returned should be the same contract we had mockito mock out in the RentalAgreementService.createContract call.
		assertEquals(rentalContract, testContract);
		// Check a few fields of the contract.
		assertNotNull(rentalContract.getContractId());
		assertNotNull(rentalContract.getStartDate());
		assertTrue(isToday(rentalContract.getStartDate()));
		// Default insurance should have been obtained, since driver provided insurance was not set.
		verify(insuranceBinderService, times(1)).getDefaultInsurance(any(RentalCarRequest.class));
		// All contingencies for getting the rental contract should have been met, and the returned contingencies should
		// be the same instance as the test that was mocked.
		assertNotNull(rentalContract.getContingencies());
		assertTrue(rentalContract.getContingencies().contingenciesMet());
		assertEquals(rentalContract.getContingencies(), testContingencies);
	}
	
	public static boolean isToday(Date date) {
	    Calendar today = Calendar.getInstance();
	    Calendar specifiedDate = Calendar.getInstance();
	    specifiedDate.setTime(date);
	    return today.get(Calendar.DAY_OF_MONTH) == specifiedDate.get(Calendar.DAY_OF_MONTH)
	            &&  today.get(Calendar.MONTH) == specifiedDate.get(Calendar.MONTH)
	            &&  today.get(Calendar.YEAR) == specifiedDate.get(Calendar.YEAR);
	}
}
