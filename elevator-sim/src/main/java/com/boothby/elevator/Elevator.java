package com.boothby.elevator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A public elevator in the building which passengers board on different floors.
 */
public class Elevator {

    private String elevatorId; // unqiue identifier of this elevator
    private ElevatorState state; // what the elevator is currently doing, moving up, down, stationary, etc
    private Floor currentFloor; // an idle elevator on this floor
    private Floor destinationFloor; // an elevator in-transit headed to this floor
    private ElevatorProperties properties; // elevator properties like max weight, speed, etc
    private Date departureTime; // when the elevator left it's last floor.
    private List<Passenger> passengerList; // all passengers currently on this elevator

    public Elevator(String elevatorId, ElevatorState state, Floor currentFloor, ElevatorProperties properties) {
        this.elevatorId = elevatorId;
        this.state = state;
        this.currentFloor = currentFloor;
        this.properties = properties;
        passengerList = new ArrayList<Passenger>();
    }

    public String getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(String elevatorId) {
        this.elevatorId = elevatorId;
    }

    public ElevatorState getState() {
        return state;
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(Floor currentFloor) {
        this.currentFloor = currentFloor;
    }

    public ElevatorProperties getProperties() {
        return properties;
    }

    public void setProperties(ElevatorProperties properties) {
        this.properties = properties;
    }

    public Floor getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(Floor destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }
}
