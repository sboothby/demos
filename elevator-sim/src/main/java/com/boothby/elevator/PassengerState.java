package com.boothby.elevator;

public enum PassengerState {

    WAITING("Waiting for elevator"), IN_ELEVATOR("In elevator"), DISEMBARKED("Disembarked");

    private String value;

    PassengerState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
