package com.boothby.design.patterns.model;

public class Ceiling {

    public enum CeilingType {
        POPCORN, DRYWALL
    };

    public enum CeilingColor {
        WHITE, BROWN
    }

    private float height;
    private CeilingType type;
    private CeilingColor color;
    private Dimensions ceilingDimensions;

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public CeilingType getType() {
        return type;
    }

    public void setType(CeilingType type) {
        this.type = type;
    }

    public CeilingColor getColor() {
        return color;
    }

    public void setColor(CeilingColor color) {
        this.color = color;
    }

    public Dimensions getCeilingDimensions() {
        return ceilingDimensions;
    }

    public void setCeilingDimensions(Dimensions ceilingDimensions) {
        this.ceilingDimensions = ceilingDimensions;
    }
}
