package com.boothby.elevator;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Builder design pattern - creates all parts of the building (building,
 * elevators, floors, etc)
 */
public class BuildingBuilder {

    private static final int MIN_PASSENGER_WEIGHT = 100;
    private static final int MAX_PASSENGER_WEIGHT = 300;
    private Building building;

    public Building getBuilding() {
        return building;
    }

    /**
     * Creates the building object for floors and elevators.
     * 
     * @param buildingId
     * @param numFloors
     * @param numElevators
     * @return
     */
    public BuildingBuilder buildBuilding(String buildingId, int numFloors, int numElevators) {
        building = new Building(buildingId, numFloors, numElevators);
        return this;
    }

    /**
     * Creates a floor.
     * 
     * @param floorNumber
     * @param upCall
     * @param downCall
     * @return
     */
    public BuildingBuilder buildFloor(int floorNumber, boolean upCall, boolean downCall) {
        Floor floor = new Floor(floorNumber, upCall, downCall);
        building.getFloorList().add(floor);
        return this;
    }

    /**
     * Creates an elevator set to a current floor.
     * 
     * @param elevatorId unique identifier of the elevator
     * @param floorNum   floor which elevator starts on
     * @param properties general elevator attributes
     * @return
     */
    public BuildingBuilder buildElevator(String elevatorId, int floorNum, int maxOccupants, float maxWeightLb,
            float intraFloorTravelTimeSec, float doorOpenTimeSec, float doorCloseTimeSec,
            float avgPassengerBoardTimeSec, float avgPassengerDisembarkTimeSec) {
        Floor currFloor = building.getFloorByNumber(floorNum);
        assert (currFloor != null);
        if (currFloor != null) {
            ElevatorProperties props = new ElevatorProperties(maxOccupants, maxWeightLb, intraFloorTravelTimeSec,
                    doorOpenTimeSec, doorCloseTimeSec, avgPassengerBoardTimeSec, avgPassengerDisembarkTimeSec);
            Elevator elevator = new Elevator(elevatorId, ElevatorState.IDLE, currFloor, props);
            building.getElevatorMap().put(elevatorId, elevator);
        }
        return this;
    }

    /**
     * Creates some passengers waiting on a floor.
     * 
     * @param numPassengers
     * @param floorNum
     * @return
     */
    public BuildingBuilder buildPassengers(int numPassengers, int floorNum) {
        for (int p = 1; p <= numPassengers; p++) {
            Passenger passenger = new Passenger(String.format("Passenger %d", p),
                    ThreadLocalRandom.current().nextInt(MIN_PASSENGER_WEIGHT, MAX_PASSENGER_WEIGHT + 1),
                    PassengerState.WAITING, new Date());
            Floor floor = building.getFloorByNumber(floorNum);
            floor.getWaitPassengerMap().put(passenger.getPassengerId(), passenger);
        }
        return this;
    }

    /**
     * Creates a request for an elevator (call), for passengers waiting on a floor.
     */
    public BuildingBuilder buildCall(int fromFloorNum, int toFloorNum, String waitingElevatorId, Date timeCalled) {
        // Build the call for the floor, then set each passengers destination floor.
        Elevator waitingElevator = building.getElevatorMap().get(waitingElevatorId);
        Floor fromFloor = building.getFloorByNumber(fromFloorNum);
        Floor toFloor = building.getFloorByNumber(toFloorNum);
        ElevatorCall call = new ElevatorCall(timeCalled, fromFloor, toFloor, waitingElevator);
        fromFloor.getWaitPassengerMap().values().stream().forEach(passenger -> {
            passenger.setFromFloorNum(fromFloorNum);
            passenger.setToFloorNum(toFloorNum);
            passenger.setTimeCalled(timeCalled);
        });
        building.getCallQueue().push(call);
        return this;
    }

    /**
     * Builder to create simple building with single elevator and few floors.
     * 
     * @return
     */
    public static BuildingBuilder buildSimpleBuilding() {
        BuildingBuilder builder = new BuildingBuilder();
        /**
         * 8 floors, 1 elevator starting on 4th floor, 3 passengers on first floor,
         * traveling to 6th floor.
         */
        builder.buildBuilding("building-simple", 8, 1).buildFloor(1, true, false).buildFloor(2, true, true)
                .buildFloor(3, true, true).buildFloor(4, true, true).buildFloor(5, true, true).buildFloor(6, true, true)
                .buildFloor(7, true, true).buildFloor(8, false, true)
                .buildElevator("elevator-1", 4, 10, 3000.0f, 2.0f, 3.0f, 3.0f, 2.0f, 2.0f).buildPassengers(3, 1)
                .buildCall(1, 6, "elevator-1", new Date());
        return builder;
    }
}
