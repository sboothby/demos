package com.boothby.design.patterns.model;

public class ConstructionMaterials {

    private DoorMaterials doorMaterials;
    private WindowMaterials windowMaterials;
    private FloorMaterials floorMaterials;
    private CeilingMaterials ceilingMaterials;
    private WallMaterials wallMaterials;

    public DoorMaterials getDoorMaterials() {
        return doorMaterials;
    }

    public void setDoorMaterials(DoorMaterials doorMaterials) {
        this.doorMaterials = doorMaterials;
    }

    public WindowMaterials getWindowMaterials() {
        return windowMaterials;
    }

    public void setWindowMaterials(WindowMaterials windowMaterials) {
        this.windowMaterials = windowMaterials;
    }

    public FloorMaterials getFloorMaterials() {
        return floorMaterials;
    }

    public void setFloorMaterials(FloorMaterials floorMaterials) {
        this.floorMaterials = floorMaterials;
    }

    public CeilingMaterials getCeilingMaterials() {
        return ceilingMaterials;
    }

    public void setCeilingMaterials(CeilingMaterials ceilingMaterials) {
        this.ceilingMaterials = ceilingMaterials;
    }

    public WallMaterials getWallMaterials() {
        return wallMaterials;
    }

    public void setWallMaterials(WallMaterials wallMaterials) {
        this.wallMaterials = wallMaterials;
    }
}
