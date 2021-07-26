package com.boothby.elevator;

import java.util.Date;

/**
 * A person waiting for or traveling in an elevator.
 */
public class Passenger {

    private String passengerId;
    private int weightLb;
    private PassengerState state;
    private int fromFloorNum; // the floor number the passenger requested an elevator
    private int toFloorNum; // the floor number passenger called for
    private float waitTimeSec; // total time passenger has waited for an elevator after making the call
    private float rideTimeSec; // total time passenger has been riding in the elevator
    private int floorsVisited; // total floors the passenger has visited while riding in the elevator (excludes
                               // start floor)
    private Date timeCalled; // when the passenger first called for an elevator

    public Passenger(String passengerId, int weightLb, PassengerState state, Date timeCalled) {
        this.passengerId = passengerId;
        this.weightLb = weightLb;
        this.state = state;
        this.setTimeCalled(timeCalled);
    }

    public int getFloorsVisited() {
        return floorsVisited;
    }

    public void setFloorsVisited(int floorsVisited) {
        this.floorsVisited = floorsVisited;
    }

    public int getWeightLb() {
        return weightLb;
    }

    public void setWeightLb(int weightLb) {
        this.weightLb = weightLb;
    }

    public PassengerState getState() {
        return state;
    }

    public void setState(PassengerState state) {
        this.state = state;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    /**
     * Returns total amount of wait time for the passenger.
     * 
     * @return
     */
    public float getWaitTimeSec() {
        return waitTimeSec;
    }

    public void setWaitTimeSec(float waitTimeSec) {
        this.waitTimeSec = waitTimeSec;
    }

    public float getRideTimeSec() {
        return rideTimeSec;
    }

    public void setRideTimeSec(float rideTimeSec) {
        this.rideTimeSec = rideTimeSec;
    }

    public int getToFloorNum() {
        return toFloorNum;
    }

    public void setToFloorNum(int toFloorNum) {
        this.toFloorNum = toFloorNum;
    }

    public Date getTimeCalled() {
        return timeCalled;
    }

    public void setTimeCalled(Date timeCalled) {
        this.timeCalled = timeCalled;
    }

    public int getFromFloorNum() {
        return fromFloorNum;
    }

    public void setFromFloorNum(int fromFloorNum) {
        this.fromFloorNum = fromFloorNum;
    }
}
