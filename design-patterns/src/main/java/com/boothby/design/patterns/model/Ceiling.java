package com.boothby.design.patterns.model;

public class Ceiling {

    public enum CeilingType {
        POPCORN, DRYWALL
    };

    private CeilingType type;
    private String color;
    
    public CeilingType getType() {
        return type;
    }

    public void setType(CeilingType type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
