package com.boothby.design.patterns.structural;

public class LowProfileCasing extends WindowDecorator {

    public LowProfileCasing(Window window) {
        super(window);
    }

    public String decorate() {
        return super.decorate() + decorateWithLowProfileCasing();
    }
 
    private String decorateWithLowProfileCasing() {
        return " with low profile casing";
    }
}
