package com.boothby.design.patterns.model;

public class Wall {

    public enum WallLocation {
        FRONT, BACK, RIGHT, LEFT
    };

    private WallLocation location;
    private Dimensions dimensions;

    public Wall() {
    }
    
    public WallLocation getLocation() {
        return location;
    }

    public void setLocation(WallLocation location) {
        this.location = location;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }
}
