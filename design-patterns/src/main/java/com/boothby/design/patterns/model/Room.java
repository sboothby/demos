package com.boothby.design.patterns.model;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private String name;
    private Dimensions dimensions;
    private Ceiling ceiling;
    private AbstractFloor floor;
    private List<DoorPlacement> doors;
    private List<WindowPlacement> windows;
    private List<WallPlacement> walls;

    public Room() {
        doors = new ArrayList<>();
        windows = new ArrayList<>();
        walls = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public Ceiling getCeiling() {
        return ceiling;
    }

    public void setCeiling(Ceiling ceiling) {
        this.ceiling = ceiling;
    }

    public AbstractFloor getFloor() {
        return floor;
    }

    public void setFloor(AbstractFloor floor) {
        this.floor = floor;
    }

    public List<DoorPlacement> getDoors() {
        return doors;
    }

    public void setDoors(List<DoorPlacement> doors) {
        this.doors = doors;
    }

    public List<WindowPlacement> getWindows() {
        return windows;
    }

    public void setWindows(List<WindowPlacement> windows) {
        this.windows = windows;
    }

    public List<WallPlacement> getWalls() {
        return walls;
    }

    public void setWalls(List<WallPlacement> walls) {
        this.walls = walls;
    }
    
    @Override
    public String toString() {
        return "Room [name=" + name + ", dimensions=" + dimensions + ", ceiling=" + ceiling + ", floor=" + floor
                + ", doors=" + doors + ", windows=" + windows + ", walls=" + walls + "]";
    }
}
