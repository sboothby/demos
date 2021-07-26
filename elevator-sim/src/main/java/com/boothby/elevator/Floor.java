package com.boothby.elevator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A floor in the building which an elevator can stop at.
 */
public class Floor {

    private boolean groundFloor; // true, if on the ground floor
    private int floorNumber; // 1 - X floors
    private boolean upCall; // true, if has up call button for higher level floor
    private boolean downCall; // true, if has down call button for lower level floor
    private Map<String, Passenger> waitPassengerMap; // map of passenger ids to waiting passengers, those passengers
                                                     // waiting

    // for an elevator on this floor
    public Floor(int floorNumber, boolean upCall, boolean downCall) {
        waitPassengerMap = new ConcurrentHashMap<String, Passenger>();
        this.floorNumber = floorNumber;
        this.upCall = upCall;
        this.downCall = downCall;
        this.groundFloor = (floorNumber == 1);
    }

    public boolean isGroundFloor() {
        return groundFloor;
    }

    public void setGroundFloor(boolean groundFloor) {
        this.groundFloor = groundFloor;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public boolean isUpCall() {
        return upCall;
    }

    public void setUpCall(boolean upCall) {
        this.upCall = upCall;
    }

    public boolean isDownCall() {
        return downCall;
    }

    public void setDownCall(boolean downCall) {
        this.downCall = downCall;
    }

    public Map<String, Passenger> getWaitPassengerMap() {
        return waitPassengerMap;
    }
}
