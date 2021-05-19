package com.boothby.design.patterns.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothby.design.patterns.structural.Window;

public class WindowImpl implements Window {
    
    private static final Logger logger = LoggerFactory.getLogger(WindowImpl.class);
    
    public enum WindowType {
        DOUBLE_HUNG,
        SINGLE_HUNG,
        CASEMENT,
        SKYLIGHT
    };

    private WindowType type;
    private Dimensions dimensions;
    private String manufacturer;

    public WindowImpl(WindowType type, Dimensions dimensions, String manufacturer) {
        this.type = type;
        this.dimensions = dimensions;
        this.manufacturer = manufacturer;
    }
    
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

    @Override
    public String render() {
        logger.info("rendering base window...");
        return "rendering base window";
    }
}
