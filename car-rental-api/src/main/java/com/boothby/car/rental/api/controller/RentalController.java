package com.boothby.car.rental.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.boothby.car.rental.api.core.exception.CarNotFoundException;
import com.boothby.car.rental.api.core.exception.DriverException;
import com.boothby.car.rental.api.core.exception.InsuranceException;
import com.boothby.car.rental.api.core.exception.RentalContractException;
import com.boothby.car.rental.api.core.exception.RentalValidationException;
import com.boothby.car.rental.api.core.model.RentalCarRequest;
import com.boothby.car.rental.api.core.model.RentalContract;
import com.boothby.car.rental.api.service.RentalService;

@RestController
public class RentalController {

    private RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rental-cars")
    public ResponseEntity<RentalContract> rentCar(@RequestBody RentalCarRequest rentalCarRequest)
            throws RentalValidationException, DriverException, CarNotFoundException, InsuranceException,
            RentalContractException {
        // Create new rental car reservation.
        RentalContract rentalContract = rentalService.rentCar(rentalCarRequest);
        return new ResponseEntity<RentalContract>(rentalContract, HttpStatus.CREATED);
    }
    
    @ExceptionHandler(RentalValidationException.class)
    public final ResponseEntity<String> handleRentalValidationException(RentalValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RentalContractException.class)
    public final ResponseEntity<String> handleRentalContractException(RentalContractException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    @ExceptionHandler(DriverException.class)
    public final ResponseEntity<String> handleInternalServiceException(DriverException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InsuranceException.class)
    public final ResponseEntity<String> handleInternalServiceException(InsuranceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(RentalContractException.class)
    public final ResponseEntity<String> handleInternalServiceException(RentalContractException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
