package com.boothby.design.patterns.model;

public class Wall {
    
    private String paintColor;

    public String getPaintColor() {
        return paintColor;
    }

    public void setPaintColor(String paintColor) {
        this.paintColor = paintColor;
    }

    @Override
    public String toString() {
        return "Wall [paintColor=" + paintColor + "]";
    }
}
