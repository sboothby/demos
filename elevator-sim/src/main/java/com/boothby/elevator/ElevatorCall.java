package com.boothby.elevator;

import java.util.Date;

public class ElevatorCall {

    private Floor fromFloor;
    private Elevator elevator;
    protected Date timeCalled;
    protected Floor toFloor;

    public ElevatorCall(Date timeCalled, Floor fromFloor, Floor toFloor, Elevator elevator) {
        this.timeCalled = timeCalled;
        this.toFloor = toFloor;
        this.fromFloor = fromFloor;
        this.elevator = elevator;
    }

    public boolean isCallUp() {
        return (toFloor.getFloorNumber() > fromFloor.getFloorNumber());
    }

    public Floor getFromFloor() {
        return fromFloor;
    }

    public void setFromFloor(Floor fromFloor) {
        this.fromFloor = fromFloor;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    public Date getTimeCalled() {
        return timeCalled;
    }

    public void setTimeCalled(Date timeCalled) {
        this.timeCalled = timeCalled;
    }

    public Floor getToFloor() {
        return toFloor;
    }

    public void setToFloor(Floor toFloor) {
        this.toFloor = toFloor;
    }
}
