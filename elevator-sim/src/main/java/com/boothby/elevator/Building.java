package com.boothby.elevator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The building with the floors and elevators, etc.
 */
public class Building {

    private String buildingId;
    private List<Floor> floorList;
    private Map<String, Elevator> elevatorMap;
    private LinkedList<ElevatorCall> callQueue;

    public Building(String buildingId, int numFloors, int numElevators) {
        this.buildingId = buildingId;
        floorList = new ArrayList<Floor>(numFloors);
        elevatorMap = new ConcurrentHashMap<String, Elevator>();
        callQueue = new LinkedList<ElevatorCall>();
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    /**
     * Find the floor by it's 1-based number.
     * 
     * @param floorNumber
     * @return
     */
    public Floor getFloorByNumber(int floorNumber) {
        return floorList.get(floorNumber - 1);
    }

    public List<Floor> getFloorList() {
        return floorList;
    }

    public void setFloorList(List<Floor> floorList) {
        this.floorList = floorList;
    }

    public Map<String, Elevator> getElevatorMap() {
        return elevatorMap;
    }

    public void setElevatorMap(Map<String, Elevator> elevatorMap) {
        this.elevatorMap = elevatorMap;
    }

    public LinkedList<ElevatorCall> getCallQueue() {
        return callQueue;
    }

    public void setCallQueue(LinkedList<ElevatorCall> callQueue) {
        this.callQueue = callQueue;
    }
}
