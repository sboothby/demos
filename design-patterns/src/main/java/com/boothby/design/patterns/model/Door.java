package com.boothby.design.patterns.model;

public class Door {

    public enum DoorType {
        WOOD,
        METAL,
        GLASS
    };

    private DoorType type;
    private Dimensions dimensions;
    private String color;
    private String manufacturer;
    
    public DoorType getType() {
        return type;
    }

    public void setType(DoorType type) {
        this.type = type;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Door [type=" + type + ", dimensions=" + dimensions + ", color=" + color + ", manufacturer="
                + manufacturer + "]";
    }
}
