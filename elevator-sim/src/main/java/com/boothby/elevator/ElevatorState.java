package com.boothby.elevator;

/**
 * The status of a current elevator, where it's at, what it's doing.
 */
public enum ElevatorState {

    UP("Up"), DOWN("Down"), IDLE("Idle"), IDLE_DOOR_OPEN("Idle - door open"), IDLE_MAINTENANCE("Idle - maintenance");

    private String value;

    ElevatorState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
