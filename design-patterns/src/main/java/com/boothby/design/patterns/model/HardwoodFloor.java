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
}