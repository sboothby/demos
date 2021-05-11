package com.boothby.design.patterns.model;

public class WallPlacement {

    public enum WallLocation {
        FRONT, BACK, RIGHT, LEFT
    };

    private WallLocation wallLocation;
    private Wall wall;

    public WallLocation getWallLocation() {
        return wallLocation;
    }
    
    public void setWallLocation(WallLocation wallLocation) {
        this.wallLocation = wallLocation;
    }
    
    public Wall getWall() {
        return wall;
    }
    
    public void setWall(Wall wall) {
        this.wall = wall;
    }

    @Override
    public String toString() {
        return "WallPlacement [wallLocation=" + wallLocation + ", wall=" + wall + "]";
    }
}
