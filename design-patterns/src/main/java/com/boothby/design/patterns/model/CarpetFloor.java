package com.boothby.design.patterns.model;

public class CarpetFloor extends AbstractFloor {
    
    public enum CarpetType {
        BERBER,
        POLYESTER,
        SISAL
    }
    
    private CarpetType type;

    public CarpetType getType() {
        return type;
    }

    public void setType(CarpetType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CarpetFloor [type=" + type + "]";
    }
}
