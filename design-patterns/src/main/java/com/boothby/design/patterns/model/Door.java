package com.boothby.design.patterns.model;

public class Door {

    public enum DoorType {
        WOOD,
        METAL,
        GLASS
    };

    public enum DoorColor {
        GREY, BROWN, WHITE
    }

    private DoorType type;
    private Dimensions dimensions;
    private DoorColor color;
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

    public DoorColor getColor() {
        return color;
    }

    public void setColor(DoorColor color) {
        this.color = color;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
