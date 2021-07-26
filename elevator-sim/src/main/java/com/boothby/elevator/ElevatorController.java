package com.boothby.elevator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles all business logic and event processing for simulating elevators
 * moving in a building, servicing passengers on different floors.
 */
public class ElevatorController {

    static Logger logger = LogManager.getLogger(ElevatorController.class);

    private BuildingBuilder buildingBuilder; // builder which creates the building
    private Building building; // building created by the builder
    private MutableBoolean stopFlag; // polling flag to stop the simulation
    private ExecutorService elevatorExecutorService; // executor to run threads for each elevator
    private ScheduledExecutorService simulationExecutorService; // executor to act as a timer for the simulation
    private ScheduledExecutorService intraElevatorExecutorService; // executor used to log activity in between elevator
                                                                   // requests

    /**
     * Runs the simulation for specified duration.
     */
    public class SimulationTimer {
        private MutableBoolean stop;

        public SimulationTimer(int minutes, MutableBoolean stop) {
            this.stop = stop;
            Runnable simulationTask = () -> {
                // Simulation time is up!
                logger.info("Elevator simulation over!");
                // End the simulation.
                this.stop.setTrue();
            };
            simulationExecutorService.schedule(simulationTask, minutes, TimeUnit.MINUTES);
        }
    }

    /**
     * Used to log no activity, after each duration.
     */
    public class NoActivityTimer {

        public NoActivityTimer(int seconds) {
            Runnable noActivityTask = () -> {
                AtomicInteger numElevatorsMoving = new AtomicInteger(0);
                building.getElevatorMap().values().stream().forEach(elevator -> {
                    if ((elevator.getState() == ElevatorState.UP) || (elevator.getState() == ElevatorState.DOWN)) {
                        numElevatorsMoving.incrementAndGet();
                    }
                });
                AtomicInteger passengersInElevators = new AtomicInteger(0);
                building.getElevatorMap().values().stream().forEach(elevator -> {
                    passengersInElevators.getAndAdd(elevator.getPassengerList().size());
                });
                AtomicInteger passengersOnFloors = new AtomicInteger(0);
                building.getFloorList().stream().forEach(floor -> {
                    passengersOnFloors.getAndAdd(floor.getWaitPassengerMap().size());
                });
                boolean noActivity = buildingBuilder.getBuilding().getCallQueue().isEmpty()
                        && (numElevatorsMoving.intValue() == 0) && (passengersInElevators.intValue() == 0)
                        && (passengersOnFloors.intValue() == 0);
                if (noActivity) {
                    logger.info(
                            "** NO ACTIVITY (no call requests to service, no elevators in motion, no passengers waiting or riding elevators) ***");
                }
            };
            intraElevatorExecutorService.scheduleAtFixedRate(noActivityTask, 0, seconds, TimeUnit.SECONDS);
        }
    }

    /**
     * Constructor Create the controller for the simulation, for the building built
     * by builder.
     * 
     * @param buildingBuilder
     */
    public ElevatorController(BuildingBuilder buildingBuilder) {
        this.buildingBuilder = buildingBuilder;
        elevatorExecutorService = Executors.newFixedThreadPool(buildingBuilder.getBuilding().getElevatorMap().size());
        simulationExecutorService = Executors.newSingleThreadScheduledExecutor();
        intraElevatorExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Initialize and run the simulation for the building.
     * 
     * @param numMinutes how long to run the simulation
     */
    public void startSimulation(int numMinutes) {
        // Get the building which should already be created.
        building = buildingBuilder.getBuilding();
        logger.info(String.format("Starting simulation for %s, %d floor(s), %d elevator(s) per floor...",
                building.getBuildingId(), building.getFloorList().size(), building.getElevatorMap().size()));
        // Run simulation for the duration.
        stopFlag = new MutableBoolean(false);
        NoActivityTimer noActivityTimer = new NoActivityTimer(5);
        SimulationTimer simTimer = new SimulationTimer(numMinutes, stopFlag);
        schedulingLoop();
    }

    private boolean isElevatorMovingTowardsCall(Elevator elevator, ElevatorCall call) {
        // Elevator above call floor and elevator moving down OR
        // Elevator below call floor and moving up
        return ((elevator.getCurrentFloor().getFloorNumber() > call.getFromFloor().getFloorNumber())
                && (elevator.getState() == ElevatorState.DOWN))
                || ((elevator.getCurrentFloor().getFloorNumber() < call.getFromFloor().getFloorNumber())
                        && (elevator.getState() == ElevatorState.UP));
    }

    private boolean isElevatorSameDirectionAsCall(Elevator elevator, ElevatorCall call) {
        // Both must be going up or down.
        return ((elevator.getState() == ElevatorState.UP) && call.isCallUp())
                || ((elevator.getState() == ElevatorState.DOWN) && !call.isCallUp());
    }

    private int distanceBetweenFloors(Elevator elevator, ElevatorCall call) {
        return Math.abs(elevator.getCurrentFloor().getFloorNumber() - call.getFromFloor().getFloorNumber());
    }

    private boolean isExtremeEndOfShaft(int floorNumber) {
        return (floorNumber == 1) || (floorNumber == building.getFloorList().size());
    }

    /**
     * Main scheduling loop of the elevator simulation. Sends elevators to floors
     * based on call queue. Processing stops when the stopFlag is triggered by the
     * external timer.
     * 
     * @param stopFlag
     */
    private void schedulingLoop() {
        while (!this.stopFlag.booleanValue()) {
            // Process all pending call requests.
            if (!buildingBuilder.getBuilding().getCallQueue().isEmpty()) {
                // Pop (remove next waiting) call request from FIFO queue.
                ElevatorCall nextCall = buildingBuilder.getBuilding().getCallQueue().pop();
                logger.info(String.format(
                        "Servicing next call request.  %d people waiting on floor %d.  Finding best elevator...",
                        building.getFloorByNumber(nextCall.getFromFloor().getFloorNumber()).getWaitPassengerMap()
                                .size(),
                        nextCall.getFromFloor().getFloorNumber()));
                // Go through all elevators to get nearest car based on figure of suitability
                // (FS).
                /*
                 * N is one less than the number of floors in the building. d is distance in
                 * floors between elevator and passenger call.
                 */
                // Dispatch elevator with highest FS to answer the call.
                Elevator highestFsElevator = null;
                int n = building.getFloorList().size() - 1;
                int highestFs = 0;
                for (Elevator elevator : building.getElevatorMap().values()) {
                    // Skip any elevator in maintenance.
                    if (elevator.getState() != ElevatorState.IDLE_MAINTENANCE) {
                        int fs = 0;
                        int d = distanceBetweenFloors(elevator, nextCall);
                        // If elevator moving towards call, and call in same direction
                        if (isElevatorMovingTowardsCall(elevator, nextCall)
                                && isElevatorSameDirectionAsCall(elevator, nextCall)) {
                            // FS = (N+2) - d
                            fs = (n + 2) - d;
                        } // Else if (elevator moving towards call, and call in opposite direction) OR
                          // elevator is idle
                        else if ((isElevatorMovingTowardsCall(elevator, nextCall)
                                && !isElevatorSameDirectionAsCall(elevator, nextCall))
                                || (elevator.getState() == ElevatorState.IDLE)) {
                            // FS = (N+1) - d
                            fs = (n + 1) - d;
                        } // Else if elevator and point of call at extreme ends of shaft
                        else if (isExtremeEndOfShaft(elevator.getCurrentFloor().getFloorNumber())
                                && isExtremeEndOfShaft(nextCall.getFromFloor().getFloorNumber())) {
                            // FS = 2
                            fs = 2;
                        } // Else if elevator moving away from point of call
                        else if (!isElevatorMovingTowardsCall(elevator, nextCall)) {
                            // FS = 1
                            fs = 1;
                        }
                        // Set highest fs and elevator.
                        if (fs > highestFs) {
                            highestFs = fs;
                            highestFsElevator = elevator;
                        }
                    }
                }
                // Dispatch target elevator to the floor answering the call.
                if (highestFsElevator != null) {
                    final Elevator targetElevator = highestFsElevator;
                    logger.info(String.format("Choosing %s, currently on floor %d, to service call on floor %d...",
                            targetElevator.getElevatorId(), targetElevator.getCurrentFloor().getFloorNumber(),
                            nextCall.getFromFloor().getFloorNumber()));
                    dispatchElevatorToFloor(targetElevator, nextCall.getFromFloor());
                }
            }
        }
    }

    /**
     * Sends an elevator to a target floor. Called recursively for other passengers
     * that requested other floors, for when elevator was diverted.
     */
    private void dispatchElevatorToFloor(Elevator elevator, Floor toFloor) {
        // This is asynchronous processing on a thread, until the elevator reaches a new
        // floor.
        Runnable elevatorCallTask = () -> {
            // Depart from the current floor to target floor.
            departFloor(elevator, toFloor);
            while (elevator.getCurrentFloor() != toFloor) {
                logger.info(String.format("%s moving from floor %d to floor %d...", elevator.getElevatorId(),
                        elevator.getCurrentFloor().getFloorNumber(), toFloor.getFloorNumber()));
                // Wait it out - elevator is moving between a floor during this period.
                try {
                    TimeUnit.SECONDS.sleep((long) elevator.getProperties().getIntraFloorTravelTimeSec());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Move elevator up or down a floor.
                Floor currFloor = elevator.getCurrentFloor();
                Floor nextFloor = null;
                if ((elevator.getState() == ElevatorState.UP)
                        && ((currFloor.getFloorNumber() + 1) <= building.getFloorList().size())) {
                    nextFloor = building.getFloorByNumber(currFloor.getFloorNumber() + 1);
                } else if ((elevator.getState() == ElevatorState.DOWN) && ((currFloor.getFloorNumber() - 1) >= 1)) {
                    nextFloor = building.getFloorByNumber(currFloor.getFloorNumber() - 1);
                }
                // Arrive at the next floor.
                arriveFloor(elevator, nextFloor);
            }
            // If passengers are on-board, dispatch them to their unique target floors.
            if (!elevator.getPassengerList().isEmpty()) {
                List<Passenger> uniquePassengersRemainingFloors = elevator.getPassengerList().stream()
                        .filter(distinctByKey(Passenger::getToFloorNum)).collect(Collectors.toList());
                if ((uniquePassengersRemainingFloors != null) && !uniquePassengersRemainingFloors.isEmpty()) {
                    for (Passenger passenger : uniquePassengersRemainingFloors) {
                        dispatchElevatorToFloor(elevator, building.getFloorByNumber(passenger.getToFloorNum()));
                    }
                }
            }
        };
        elevatorExecutorService.execute(elevatorCallTask);
    }

    /**
     * Used to determine uniqueness of key in a map.
     * http://www.lambdafaq.org/how-can-i-get-distinct-to-compare-some-derived-value-instead-of-the-stream-elements-themselves/
     * 
     * @param keyExtractor
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * Handles an elevator leaving the floor.
     * 
     * @param elevator
     * @param toFloor
     */
    private void departFloor(Elevator elevator, Floor toFloor) {
        logger.info(String.format("%s departing floor %d headed to floor %d...", elevator.getElevatorId(),
                elevator.getCurrentFloor().getFloorNumber(), toFloor.getFloorNumber()));
        String msg = String.format("%s door closing...", elevator.getElevatorId());
        waitDuration((int) elevator.getProperties().getDoorCloseTimeSec(), msg);
        // Update elevator state and current floor.
        if (toFloor.getFloorNumber() > elevator.getCurrentFloor().getFloorNumber()) {
            elevator.setState(ElevatorState.UP);
        } else if (toFloor.getFloorNumber() < elevator.getCurrentFloor().getFloorNumber()) {
            elevator.setState(ElevatorState.DOWN);
        }
        // Update destination state departure time.
        elevator.setDestinationFloor(toFloor);
        elevator.setDepartureTime(new Date());
    }

    /**
     * Handles elevator arriving (or passing by) this floor.
     * 
     * @param elevator
     */
    private void arriveFloor(Elevator elevator, Floor arriveFloor) {
        logger.info(
                String.format("%s arriving at floor %d...", elevator.getElevatorId(), arriveFloor.getFloorNumber()));
        // Update the current floor.
        elevator.setCurrentFloor(arriveFloor);
        // Update total passenger travel time and floors visited for all passengers.
        elevator.getPassengerList().stream().forEach(passenger -> {
            float secondsTravelledSinceElevatorDeparture = ((System.currentTimeMillis()
                    - elevator.getDepartureTime().getTime()) / 1000.0f);
            passenger.setRideTimeSec(secondsTravelledSinceElevatorDeparture);
            passenger.setFloorsVisited(passenger.getFloorsVisited() + 1);
        });
        // At destination floor?
        if (elevator.getDestinationFloor().equals(arriveFloor)) {
            logger.info(String.format("%s arrived at destination floor %d.", elevator.getElevatorId(),
                    arriveFloor.getFloorNumber()));
            // Open the door, update elevator state, and disembark any passengers on the
            // elevator who called for this floor.
            String msg = String.format("%s door opening...", elevator.getElevatorId());
            waitDuration((int) elevator.getProperties().getDoorOpenTimeSec(), msg);
            elevator.setState(ElevatorState.IDLE);
            if (!elevator.getPassengerList().isEmpty()) {
                List<Passenger> removePassengerList = new ArrayList<Passenger>();
                elevator.getPassengerList().stream().forEach(passenger -> {
                    if (arriveFloor.getFloorNumber() == passenger.getToFloorNum()) {
                        passengerDisembarks(elevator, passenger);
                        removePassengerList.add(passenger);
                    }
                });
                removePassengerList.stream().forEach(passenger -> {
                    elevator.getPassengerList().remove(passenger);
                });
            }
            // Board any waiting passengers, and clear the list.
            arriveFloor.getWaitPassengerMap().entrySet().stream().forEach(waitingPassenger -> {
                passsengerBoards(elevator, waitingPassenger.getValue());
            });
            arriveFloor.getWaitPassengerMap().clear();
            // No current destination floor.
            elevator.setDestinationFloor(null);
        }
    }

    /**
     * Handles a passenger leaving an elevator on a floor.
     * 
     * @param elevator
     * @param passenger
     */
    private void passengerDisembarks(Elevator elevator, Passenger passenger) {
        String msg = String.format("%s leaving %s on floor %d...", passenger.getPassengerId(), elevator.getElevatorId(),
                elevator.getCurrentFloor().getFloorNumber());
        waitDuration((int) elevator.getProperties().getPassengerDisembarkTimeSec(), msg);
        // Accumulate total passenger ride time, which includes time to open the door
        // and time for passengers to disembark.
        passenger.setRideTimeSec(passenger.getRideTimeSec() + elevator.getProperties().getDoorOpenTimeSec()
                + elevator.getProperties().getPassengerDisembarkTimeSec());
        // Update passenger state
        passenger.setState(PassengerState.DISEMBARKED);
    }

    /**
     * Handles a waiting passenger entering the elevator on a floor.
     * 
     * @param passenger
     */
    private void passsengerBoards(Elevator elevator, Passenger waitingPassenger) {
        String msg = String.format("%s boarding %s on floor %d...", waitingPassenger.getPassengerId(),
                elevator.getElevatorId(), elevator.getCurrentFloor().getFloorNumber());
        waitDuration((int) elevator.getProperties().getPassengerBoardTimeSec(), msg);
        // Update passenger state, accumulating their total wait time so far, which
        // includes time to open the door,
        // and time to board passengers.
        waitingPassenger.setState(PassengerState.IN_ELEVATOR);
        waitingPassenger.setWaitTimeSec(getCurrentWaitTimeSeconds(waitingPassenger)
                + elevator.getProperties().getDoorOpenTimeSec() + elevator.getProperties().getPassengerBoardTimeSec());
        // Add passenger to the elevator passenger list.
        elevator.getPassengerList().add(waitingPassenger);
    }

    private void waitDuration(int seconds, String message) {
        logger.info(message);
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates how long the passenger has been waiting since the call, from
     * current time.
     * 
     * @return wait time in seconds
     */
    public float getCurrentWaitTimeSeconds(Passenger passenger) {
        float currentWaitTimeSec = (float) ((System.currentTimeMillis() - passenger.getTimeCalled().getTime())
                / 1000.0f);
        return currentWaitTimeSec;
    }
}
