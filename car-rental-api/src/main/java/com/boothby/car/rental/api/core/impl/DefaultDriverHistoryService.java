package com.boothby.car.rental.api.core.impl;

import org.springframework.stereotype.Service;

import com.boothby.car.rental.api.core.DriverHistoryService;
import com.boothby.car.rental.api.core.exception.DriverException;
import com.boothby.car.rental.api.core.model.DriverInfo;

@Service("defaultDriverHistoryService")
public class DefaultDriverHistoryService implements DriverHistoryService {

	@Override
	public float verifyDMVScore(DriverInfo driverInfo) throws DriverException {
		// TODO Auto-generated method stub
		return 0;
	}

}
