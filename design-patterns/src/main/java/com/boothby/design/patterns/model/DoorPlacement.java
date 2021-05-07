package com.boothby.design.patterns.model;

public class DoorPlacement {

    public enum DoorWall {
        FRONT_WALL, BACK_WALL, RIGHT_WALLL, LEFT_WALL
    };

    
    private Door door;
    private DoorWall doorWall;
    private float leftOffsetFt; // looking at the wall, how many feet to shift door to the left
    private float rightOffsetFt; // looking at the wall, how many feet to shift door to the right
    
    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public DoorWall getDoorWall() {
        return doorWall;
    }

    public void setDoorWall(DoorWall doorWall) {
        this.doorWall = doorWall;
    }

    public float getLeftOffsetFt() {
        return leftOffsetFt;
    }

    public void setLeftOffsetFt(float leftOffsetFt) {
        this.leftOffsetFt = leftOffsetFt;
    }

    public float getRightOffsetFt() {
        return rightOffsetFt;
    }

    public void setRightOffsetFt(float rightOffsetFt) {
        this.rightOffsetFt = rightOffsetFt;
    }
}
