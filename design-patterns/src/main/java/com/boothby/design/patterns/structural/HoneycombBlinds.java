package com.boothby.design.patterns.structural;

public class HoneycombBlinds extends WindowDecorator {

    public HoneycombBlinds(Window window) {
        super(window);
    }

    public String decorate() {
        return super.decorate() + decorateWithHoneycombBlinds();
    }
 
    private String decorateWithHoneycombBlinds() {
        return " with honeycomb blinds";
    }
}
