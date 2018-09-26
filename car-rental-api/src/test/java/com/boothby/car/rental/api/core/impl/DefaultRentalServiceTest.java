package com.boothby.car.rental.api.core.impl;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.boothby.car.rental.api.core.CarLocatorService;
import com.boothby.car.rental.api.core.DriverHistoryService;
import com.boothby.car.rental.api.core.InsuranceBinderService;
import com.boothby.car.rental.api.core.RentalAgreementService;
import com.boothby.car.rental.api.core.RentalService;
import com.boothby.car.rental.api.core.exception.CarNotFoundException;
import com.boothby.car.rental.api.core.exception.DriverException;
import com.boothby.car.rental.api.core.exception.InsuranceException;
import com.boothby.car.rental.api.core.exception.RentalContractException;
import com.boothby.car.rental.api.core.exception.RentalValidationException;
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
	public void setUp() {
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
			.buildRentalCarRequestDriver("Sean", "Donnelly", "12/1/1945", "LA", "ABCDEFG12345")
			.buildDefaultInsurance("Atena",modelBuilder.getRentalCarRequest().getStartDate(), modelBuilder.getRentalCarRequest().getDurationDays(), 
									1000000.0f, 10000.0f, 25.0f)
			.buildRentalCarRequestDriverInsurance("WSB Insurance (Division of World Security Bureau)", "1/1/2018", "1/1/2025", 1000000f, 10000f)
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
	
	/*************************************************************************
	 * HAPPY CASES - Car rental agreement created
	 *************************************************************************/
	
	/**
	* Verifies car is rented and reserved through a rental agreement, accepting default insurance for the term of the rental.
	*/ 
	@Test
	public void should_ReserveCar_For_ValidRentalRequestWithDefaultInsurance() throws Exception {
		// Mock dependent bean calls. These are the calls made from beans within DefaultRentalService.rentCar, that will return default test values,
		// or take no adverse action, etc.
		testRentalCarRequest.setDriverProvidedInsurance(null);
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
	
	/**
	 * Verifies car is rented and reserved through a rental agreement, using insurance provided by the driver. 
	 */
	@Test
	public void should_ReserveCar_For_ValidRentalRequestWithDriverProvidedInsurance() throws Exception {
		// Mock dependent bean calls. These are the calls made from beans within DefaultRentalService.rentCar, that will return default test values,
		// or take no adverse action, etc.
		doNothing().when(insuranceBinderService).validateDriverInsurance(any(RentalCarRequest.class));
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
		// Driver provider insurance should have been validated OK.
		verify(insuranceBinderService, times(1)).validateDriverInsurance(any(RentalCarRequest.class));
		// All contingencies for getting the rental contract should have been met, and the returned contingencies should
		// be the same instance as the test that was mocked.
		assertNotNull(rentalContract.getContingencies());
		assertTrue(rentalContract.getContingencies().contingenciesMet());
		assertEquals(rentalContract.getContingencies(), testContingencies);
	}
	
	/*************************************************************************
	 * SAD CASES - Car rental agreement NOT created
	 ************************************************************************/
	
	/****
	 * Rental request validation failure test cases
	 */
	@Test(expected = RentalValidationException.class)
	public void should_ThrowException_When_RequestedDateInThePast() throws Exception {
		testRentalCarRequest.setStartDate(RentalModelBuilder.parseDate("6/15/2008"));
		rentalService.rentCar(testRentalCarRequest);
	}

	@Test(expected = RentalValidationException.class)
	public void should_ThrowException_When_RentalDaysGreaterThanLimit() throws Exception {
		testRentalCarRequest.setDurationDays(45);
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_ThrowException_When_CarSearchTooFarOut() throws Exception {
		testRentalCarRequest.setSearchMilesOut(30);
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_ThrowException_When_MinimumDepositNotMet() throws Exception {
		testRentalCarRequest.setDepositAmount(85.0f);
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_ThrowException_For_MissingFirstName() throws Exception {
		testRentalCarRequest.getDriverInfo().setFirstName("");
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_ThrowException_For_MissingLastName() throws Exception {
		testRentalCarRequest.getDriverInfo().setLastName("");
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_ThrowException_For_MissingDriverLicense() throws Exception {
		testRentalCarRequest.getDriverInfo().setDriverLicenseNumber("");
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_ThrowException_For_InactiveDriverInsurancePolicy() throws Exception {
		testRentalCarRequest.getDriverProvidedInsurance().setEndDateCoverage(RentalModelBuilder.parseDate("1/1/1987"));
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_ThrowException_When_MissingVehicleTypeDetails() throws Exception {
		testRentalCarRequest.setMake("");
		testRentalCarRequest.setModel("");
		testRentalCarRequest.setVehicleClass(VehicleClass.UNKNOWN);
		rentalService.rentCar(testRentalCarRequest);
	}
	
	/** 
	 * Rental business logic failure test cases
	 */
	@Ignore
	@Test(expected = InsuranceException.class)
	public void should_ThrowException_When_DefaultInsuranceNotAvailable() throws Exception {
		//TODO
	}

	@Ignore
	@Test(expected = InsuranceException.class)
	public void should_ThrowException_When_DriverProvidedInsuranceInvalid() throws Exception {
		//TODO
	}
	
	@Ignore
	@Test(expected = DriverException.class)
	public void should_ThrowException_When_DriverLicenseNotVerified() throws Exception {
		//TODO
	}
	
	@Ignore
	@Test(expected = DriverException.class)
	public void should_ThrowException_When_DriverScoreNotObtainedFromDMV() throws Exception {
		//TODO
	}

	@Ignore
	@Test(expected = DriverException.class)
	public void should_ThrowException_When_DriverScoreTooLow() throws Exception {
		//TODO
	}
	
	@Ignore
	@Test(expected = CarNotFoundException.class)
	public void should_ThrowException_When_RentalCarNotFound() throws Exception {
		//TODO
	}
	
	@Ignore
	@Test(expected = RentalContractException.class)
	public void should_ThrowException_When_UnableToCreateContract() throws Exception {
		//TODO
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
