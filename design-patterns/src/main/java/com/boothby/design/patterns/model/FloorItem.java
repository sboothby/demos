package com.boothby.design.patterns.model;

public abstract class FloorItem {

    private Dimensions itemDimensions;

    public Dimensions getItemDimensions() {
        return itemDimensions;
    }

    public void setItemDimensions(Dimensions itemDimensions) {
        this.itemDimensions = itemDimensions;
    }
}
