package com.boothby.design.patterns.structural;

public class AluminumHandle extends WindowDecorator {

    public AluminumHandle(Window window) {
        super(window);
    }
    
    public String decorate() {
        return super.decorate() + decorateWithAluminumHandle();
    }
 
    private String decorateWithAluminumHandle() {
        return " with aluminum handle";
    }
}
