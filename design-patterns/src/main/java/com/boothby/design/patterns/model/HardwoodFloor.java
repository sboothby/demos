package com.boothby.design.patterns.model;

public class HardwoodFloor extends AbstractFloor {

    public enum WoodType {
        OAK,
        MAPLE,
        MAHAGONY,
        CHERRY
    };
    
    private WoodType type;
    private boolean preFinished;
    
    public HardwoodFloor() {}
    
    public HardwoodFloor(WoodType woodType, boolean preFinished) {
        super();
        this.type = woodType;
        this.preFinished = preFinished;
    }
    
    public boolean isPreFinished() {
        return preFinished;
    }

    public void setPreFinished(boolean preFinished) {
        this.preFinished = preFinished;
    }

    public WoodType getType() {
        return type;
    }

    public void setType(WoodType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "HardwoodFloor [type=" + type + ", preFinished=" + preFinished + "]";
    }

    @Override
    public float getCost() {
        return 2000.0f; //TODO refine this based on flooring
    }
}
