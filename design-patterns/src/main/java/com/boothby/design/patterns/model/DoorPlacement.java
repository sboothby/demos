package com.boothby.design.patterns.model;

public class DoorPlacement {

    public enum DoorLocation {
        FRONT_LEFT, FRONT_CENTER, FRONT_RIGHT, RIGHT_CENTER, BACK_RIGHT, BACK_CENTER, BACK_LEFT, LEFT_CENTER
    };

    private Door door;
    private DoorLocation doorLocation;

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public DoorLocation getDoorLocation() {
        return doorLocation;
    }

    public void setDoorLocation(DoorLocation doorLocation) {
        this.doorLocation = doorLocation;
    }
}
