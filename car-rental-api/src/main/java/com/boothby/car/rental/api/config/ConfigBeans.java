package com.boothby.car.rental.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.boothby.car.rental.api.repository.DynamoRentalRepository;
import com.boothby.car.rental.api.repository.RentalRepository;
import com.boothby.car.rental.api.service.CarLocatorService;
import com.boothby.car.rental.api.service.DefaultCarLocatorService;
import com.boothby.car.rental.api.service.DefaultDriverHistoryService;
import com.boothby.car.rental.api.service.DefaultInsuranceBinderService;
import com.boothby.car.rental.api.service.DefaultRentalAgreementService;
import com.boothby.car.rental.api.service.DefaultRentalService;
import com.boothby.car.rental.api.service.DriverHistoryService;
import com.boothby.car.rental.api.service.InsuranceBinderService;
import com.boothby.car.rental.api.service.RentalAgreementService;
import com.boothby.car.rental.api.service.RentalService;

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
