package com.boothby.car.rental.api.core.model;

import java.util.stream.Stream;

public enum VehicleClass {
	COMPACT,
	SEDAN,
	SUV,
	UNKNOWN;

	public static String getValues() {
	    String[] values = Stream.of(VehicleClass.values()).map(VehicleClass::name).toArray(String[]::new);
	    return String.join(",", values);
	}
}
