package com.boothby.design.patterns.model;

public class Window {
    
    public enum WindowType {
        DOUBLE_HUNG,
        SINGLE_HUNG,
        CASEMENT,
        SKYLIGHT
    };

    private WindowType type;
    private Dimensions dimensions;
    private String manufacturer;

    public WindowType getType() {
        return type;
    }

    public void setType(WindowType type) {
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
}
