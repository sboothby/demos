package com.boothby.design.patterns.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFloor {

    protected List<FloorItem> floorItems;
    protected String manufacturer;

    public AbstractFloor() {
        floorItems = new ArrayList<>();
    }
    
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<FloorItem> getFloorItems() {
        return floorItems;
    }

    public void setFloorItems(List<FloorItem> floorItems) {
        this.floorItems = floorItems;
    }

    public float getFloorTotalSquareFeet() {
        float floorSquareFt = 0;
        for(FloorItem floorItem : floorItems) {
            floorSquareFt += (floorItem.getItemDimensions().getWidthFt() * floorItem.getItemDimensions().getDepthFt());
        }
        return floorSquareFt;
    }

    @Override
    public String toString() {
        return "AbstractFloor [floorItems=" + floorItems + ", manufacturer=" + manufacturer + "]";
    }
}
