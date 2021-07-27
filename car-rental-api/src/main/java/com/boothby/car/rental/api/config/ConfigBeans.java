package com.boothby.car.rental.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.boothby.car.rental.api.core.CarLocatorService;
import com.boothby.car.rental.api.core.DriverHistoryService;
import com.boothby.car.rental.api.core.InsuranceBinderService;
import com.boothby.car.rental.api.core.RentalAgreementService;
import com.boothby.car.rental.api.core.RentalService;
import com.boothby.car.rental.api.core.impl.DefaultCarLocatorService;
import com.boothby.car.rental.api.core.impl.DefaultDriverHistoryService;
import com.boothby.car.rental.api.core.impl.DefaultInsuranceBinderService;
import com.boothby.car.rental.api.core.impl.DefaultRentalAgreementService;
import com.boothby.car.rental.api.core.impl.DefaultRentalService;
import com.boothby.car.rental.api.ports.rental_repository.RentalRepository;
import com.boothby.car.rental.api.ports.rental_repository.dynamo.DynamoRentalRepository;

/**
 * Performs Spring DI of beans.  Does any custom bean setup.  Loosely-coupled shared beans are centrally initialized
 * here through Spring.
 */
@Configuration
public class ConfigBeans {

    @Bean
    public DriverHistoryService driverHistoryService() {
        return new DefaultDriverHistoryService();
    }
    
    @Bean
    public CarLocatorService carLocatorService() {
        return new DefaultCarLocatorService();
    }
    
    @Bean
    public InsuranceBinderService insuranceBinderService() {
        return new DefaultInsuranceBinderService();
    }
    
    @Bean
    public RentalAgreementService rentalAgreementService(RentalRepository rentalRepository) {
        return new DefaultRentalAgreementService(rentalRepository);
    }
    
    @Bean
    RentalRepository rentalRepository() {
        return new DynamoRentalRepository();
    }
    
    @Bean
    public RentalService rentalService(DriverHistoryService driverHistoryService, CarLocatorService carLocatorService,
            InsuranceBinderService insuranceBinderService, RentalAgreementService rentalAgreementService) {
        return new DefaultRentalService(driverHistoryService, carLocatorService, insuranceBinderService, rentalAgreementService);
    }
}
