package com.boothby.elevator;

public class ElevatorMain {

    private static final int NUM_MINUTES_SIMULATION = 5;

    public static void main(String[] args) {
        // Create the building with floors and elevators.
        BuildingBuilder buildingBuilder = BuildingBuilder.buildSimpleBuilding();
        ElevatorController elevatorController = new ElevatorController(buildingBuilder);
        // Start the simulation which will run for the given duration
        elevatorController.startSimulation(NUM_MINUTES_SIMULATION);
    }
}
