package com.boothby.elevator;

/**
 * Common properties to all elevators in the building.
 */
public class ElevatorProperties {

    private float maxOccupants; // total number of passengers the elevator may carry
    private float maxWeightLb; // total weight of passengers the elevator may carry
    private float intraFloorTravelTimeSec; // time in seconds to travel before floors
    private float doorOpenTimeSec; // time in seconds for the door to fully open
    private float doorCloseTimeSec; // time in seconds for the door to fully close
    private float passengerBoardTimeSec; // time in seconds for a single passenger to board an elevator
    private float passengerDisembarkTimeSec; // time in seconds for a single passenger to leave an elevator

    public ElevatorProperties(float maxOccupants, float maxWeightLb, float intraFloorTravelTimeSec,
            float doorOpenTimeSec, float doorCloseTimeSec, float passengerBoardTimeSec,
            float passengerDisembarkTimeSec) {
        this.maxOccupants = maxOccupants;
        this.maxWeightLb = maxWeightLb;
        this.intraFloorTravelTimeSec = intraFloorTravelTimeSec;
        this.doorOpenTimeSec = doorOpenTimeSec;
        this.doorCloseTimeSec = doorCloseTimeSec;
        this.passengerBoardTimeSec = passengerBoardTimeSec;
        this.passengerDisembarkTimeSec = passengerDisembarkTimeSec;
    }

    public float getMaxOccupants() {
        return maxOccupants;
    }

    public void setMaxOccupants(float maxOccupants) {
        this.maxOccupants = maxOccupants;
    }

    public float getMaxWeight() {
        return maxWeightLb;
    }

    public void setMaxWeight(float maxWeight) {
        this.maxWeightLb = maxWeight;
    }

    public float getDoorOpenTimeSec() {
        return doorOpenTimeSec;
    }

    public void setDoorOpenTimeSec(float doorOpenTimeSec) {
        this.doorOpenTimeSec = doorOpenTimeSec;
    }

    public float getDoorCloseTimeSec() {
        return doorCloseTimeSec;
    }

    public void setDoorCloseTimeSec(float doorCloseTimeSec) {
        this.doorCloseTimeSec = doorCloseTimeSec;
    }

    public float getPassengerBoardTimeSec() {
        return passengerBoardTimeSec;
    }

    public void setPassengerBoardTimeSec(float passengerBoardTimeSec) {
        this.passengerBoardTimeSec = passengerBoardTimeSec;
    }

    public float getPassengerDisembarkTimeSec() {
        return passengerDisembarkTimeSec;
    }

    public void setPassengerDisembarkTimeSec(float passengerDisembarkTimeSec) {
        this.passengerDisembarkTimeSec = passengerDisembarkTimeSec;
    }

    public float getIntraFloorTravelTimeSec() {
        return intraFloorTravelTimeSec;
    }

    public void setIntraFloorTravelTimeSec(float intraFloorTravelTimeSec) {
        this.intraFloorTravelTimeSec = intraFloorTravelTimeSec;
    }
}
