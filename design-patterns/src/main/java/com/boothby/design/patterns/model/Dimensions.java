package com.boothby.design.patterns.model;

public class Dimensions {

    private float widthFt;
    private float heightFt;
    private float depthFt;

    public Dimensions(float widthFt, float heightFt, float depthFt) {
        this.widthFt = widthFt;
        this.heightFt = heightFt;
        this.depthFt = depthFt;
    }
    
    public Dimensions() {
    }

    public float getWidthFt() {
        return widthFt;
    }

    public void setWidthFt(float widthFt) {
        this.widthFt = widthFt;
    }

    public float getHeightFt() {
        return heightFt;
    }

    public void setHeightFt(float heightFt) {
        this.heightFt = heightFt;
    }

    public float getDepthFt() {
        return depthFt;
    }

    public void setDepthFt(float depthFt) {
        this.depthFt = depthFt;
    }

    @Override
    public String toString() {
        return "Dimensions [widthFt=" + widthFt + ", heightFt=" + heightFt + ", depthFt=" + depthFt + "]";
    }
}
