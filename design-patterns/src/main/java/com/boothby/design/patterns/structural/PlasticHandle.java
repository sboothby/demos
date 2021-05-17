package com.boothby.design.patterns.structural;

public class PlasticHandle extends WindowDecorator {

    public PlasticHandle(Window window) {
        super(window);
    }

    public String decorate() {
        return super.decorate() + decorateWithPlasticHandle();
    }
 
    private String decorateWithPlasticHandle() {
        return " with plastic handle";
    }
}
