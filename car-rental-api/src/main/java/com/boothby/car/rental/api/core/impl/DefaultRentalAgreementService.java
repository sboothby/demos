package com.boothby.car.rental.api.core.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boothby.car.rental.api.core.RentalAgreementService;
import com.boothby.car.rental.api.core.exception.RentalContractException;
import com.boothby.car.rental.api.core.model.RentalCarRequest;
import com.boothby.car.rental.api.core.model.RentalContingencies;
import com.boothby.car.rental.api.core.model.RentalContract;
import com.boothby.car.rental.api.ports.rental_repository.RentalRepository;

@Service("defaultRentalAgreementService")
public class DefaultRentalAgreementService implements RentalAgreementService {

    private RentalRepository rentalRepository;  // for storing new rental agreements
    
    @Autowired
    public DefaultRentalAgreementService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }
    
	@Override
	public RentalContract createContract(RentalCarRequest rentalCarRequest, RentalContingencies contingencies)
			throws RentalContractException {
		//TODO perform checks on rental contingencies, fail anything that is invalid
	    //TODO persist rental agreement in repository 
		//TODO return new rental contract with new unique identifier
	    return null;
	}

}
