package com.boothby.car.rental.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

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
import com.boothby.car.rental.api.util.RentalModelBuilder;

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
	public void should_reserveCar_for_rentalRequestWithDefaultInsurance() throws Exception {
		// Mock dependent bean calls. These are the calls made from beans within DefaultRentalService.rentCar, that will return default test values,
		// or take no adverse action, etc.
		testRentalCarRequest.setDriverProvidedInsurance(null);
		when(insuranceBinderService.getDefaultInsurance(any(RentalCarRequest.class))).thenReturn(testDefaultInsurance);
		doNothing().when(driverHistoryService).verifyDriverLicense(any(DriverInfo.class));
		when(driverHistoryService.getDriverScore(any(DriverInfo.class))).thenReturn(testContingencies.getDriverScore());
		when(carLocatorService.findRentalCar(any(RentalCarRequest.class), anyInt())).thenReturn(testIdentifiedCar);
		when(rentalAgreementService.createContract(any(RentalCarRequest.class), any(RentalContingencies.class))).thenReturn(testContract);

		// Used for dependent bean call verification of parameter.
		ArgumentCaptor<RentalCarRequest> captor = ArgumentCaptor.forClass(RentalCarRequest.class);
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
		verify(insuranceBinderService, times(1)).getDefaultInsurance(captor.capture());
		// Check that the object passed to dependent bean was what we expected.
		RentalCarRequest actualRentalCarRequest = captor.getValue();
		assertEquals(testRentalCarRequest, actualRentalCarRequest);
		// All contingencies for getting the rental contract should have been met, and the returned contingencies should
		// be the same instance as the test that was mocked.
		assertNotNull(rentalContract.getContingencies());
		assertTrue(rentalContract.getContingencies().contingenciesMet());
		assertEquals(rentalContract.getContingencies(), testContingencies);
		// Confirm contract was generated with identifier.
		assertEquals("Rental contract generated with valid id #", testContract.getContractId(), rentalContract.getContractId());	
	}
	
	/**
	 * Verifies car is rented and reserved through a rental agreement, using insurance provided by the driver. 
	 */
	@Test
	public void should_reserveCar_for_rentalRequestWithDriverProvidedInsurance() throws Exception {
		// Mock dependent bean calls. These are the calls made from beans within DefaultRentalService.rentCar, that will return default test values,
		// or take no adverse action, etc.
		doNothing().when(insuranceBinderService).checkDriverProvidedInsuranceCoverage(any(RentalCarRequest.class));
		doNothing().when(driverHistoryService).verifyDriverLicense(any(DriverInfo.class));
		when(driverHistoryService.getDriverScore(any(DriverInfo.class))).thenReturn(testContingencies.getDriverScore());
		when(carLocatorService.findRentalCar(any(RentalCarRequest.class), anyInt())).thenReturn(testIdentifiedCar);
		when(rentalAgreementService.createContract(any(RentalCarRequest.class), any(RentalContingencies.class))).thenReturn(testContract);
		
		// Used for dependent bean call verification of parameter.
		ArgumentCaptor<RentalCarRequest> captor = ArgumentCaptor.forClass(RentalCarRequest.class);
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
		verify(insuranceBinderService, times(1)).checkDriverProvidedInsuranceCoverage(captor.capture());
		// Check that the object passed to dependent bean was what we expected.
		RentalCarRequest actualRentalCarRequest = captor.getValue();
		assertEquals(testRentalCarRequest, actualRentalCarRequest);		
		// All contingencies for getting the rental contract should have been met, and the returned contingencies should
		// be the same instance as the test that was mocked.
		assertNotNull(rentalContract.getContingencies());
		assertTrue(rentalContract.getContingencies().contingenciesMet());
		assertEquals(rentalContract.getContingencies(), testContingencies);
		// Confirm contract was generated with identifier.
		assertEquals("Rental contract generated with valid id #", testContract.getContractId(), rentalContract.getContractId());	
	}
	
	/*************************************************************************
	 * SAD CASES - Car rental agreement NOT created
	 ************************************************************************/
	
	@Test(expected = RentalValidationException.class)
	public void should_throwException_when_requestedDateInThePast() throws Exception {
		testRentalCarRequest.setStartDate(RentalModelBuilder.parseDate("6/15/2008"));
		rentalService.rentCar(testRentalCarRequest);
	}

	@Test(expected = RentalValidationException.class)
	public void should_throwException_when_rentalDaysGreaterThanLimit() throws Exception {
		testRentalCarRequest.setDurationDays(45);
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_throwException_when_carSearchTooFarOut() throws Exception {
		testRentalCarRequest.setSearchMilesOut(30);
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_throwException_when_minimumDepositNotMet() throws Exception {
		testRentalCarRequest.setDepositAmount(85.0f);
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_throwException_when_missingFirstName() throws Exception {
		testRentalCarRequest.getDriverInfo().setFirstName("");
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_throwException_when_missingLastName() throws Exception {
		testRentalCarRequest.getDriverInfo().setLastName("");
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_throwException_when_missingDriverLicense() throws Exception {
		testRentalCarRequest.getDriverInfo().setDriverLicenseNumber("");
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_throwException_when_driverInsurancePolicyInactive() throws Exception {
		testRentalCarRequest.getDriverProvidedInsurance().setEndDateCoverage(RentalModelBuilder.parseDate("1/1/1987"));
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalValidationException.class)
	public void should_throwException_when_missingVehicleTypeDetails() throws Exception {
		testRentalCarRequest.setMake("");
		testRentalCarRequest.setModel("");
		testRentalCarRequest.setVehicleClass(VehicleClass.UNKNOWN);
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = InsuranceException.class)
	public void should_throwException_when_defaultInsuranceNotAvailable() throws Exception {
		testRentalCarRequest.setDriverProvidedInsurance(null);
		doThrow(new InsuranceException("Default insurance not available!")).when(insuranceBinderService).getDefaultInsurance(any(RentalCarRequest.class));
		
		rentalService.rentCar(testRentalCarRequest);
	}

	@Test(expected = InsuranceException.class)
	public void should_throwException_when_driverProvidedInsuranceHasInsufficientCoverage() throws Exception {
		doThrow(new InsuranceException("Driver provided insurance was deemed insufficient coverage by rental agent insurance carrier!"))
			.when(insuranceBinderService).checkDriverProvidedInsuranceCoverage(any(RentalCarRequest.class));
		
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = DriverException.class)
	public void should_throwException_when_driverLicenseNotVerified() throws Exception {
		doThrow(new DriverException("Unable to verify customer driver license with state DMV!"))
			.when(driverHistoryService).verifyDriverLicense(any(DriverInfo.class));
		
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = DriverException.class)
	public void should_throwException_when_driverScoreNotObtainedFromDMV() throws Exception {
		doThrow(new DriverException("Error obtaining driver score from state DMV! System down!"))
			.when(driverHistoryService).getDriverScore(any(DriverInfo.class));
		
		rentalService.rentCar(testRentalCarRequest);
	}

	@Test(expected = DriverException.class)
	public void should_throwException_when_driverScoreTooLow() throws Exception {
		when(driverHistoryService.getDriverScore(any(DriverInfo.class))).thenReturn(45.6f);
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = CarNotFoundException.class)
	public void should_throwException_when_rentalCarNotFound() throws Exception {
		when(driverHistoryService.getDriverScore(any(DriverInfo.class))).thenReturn(85.0f);
		doThrow(new CarNotFoundException("Car not found with customer specs in search radius!"))
			.when(carLocatorService).findRentalCar(any(RentalCarRequest.class), anyInt());
		
		rentalService.rentCar(testRentalCarRequest);
	}
	
	@Test(expected = RentalContractException.class)
	public void should_throwException_when_unableToCreateContract() throws Exception {
		when(driverHistoryService.getDriverScore(any(DriverInfo.class))).thenReturn(85.0f);
		doThrow(new RentalContractException("Error creating rental agreement! System down!"))
			.when(rentalAgreementService).createContract(any(RentalCarRequest.class), any(RentalContingencies.class));
		
		rentalService.rentCar(testRentalCarRequest);
	}
	
	private boolean isToday(Date date) {
	    Calendar today = Calendar.getInstance();
	    Calendar specifiedDate = Calendar.getInstance();
	    specifiedDate.setTime(date);
	    return today.get(Calendar.DAY_OF_MONTH) == specifiedDate.get(Calendar.DAY_OF_MONTH)
	            &&  today.get(Calendar.MONTH) == specifiedDate.get(Calendar.MONTH)
	            &&  today.get(Calendar.YEAR) == specifiedDate.get(Calendar.YEAR);
	}
}
