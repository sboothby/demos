package com.boothby.design.patterns.structural;

public class SheerCurtains extends WindowDecorator {

    public SheerCurtains(Window window) {
        super(window);
    }

    public String decorate() {
        return super.decorate() + decorateWithSheerCurtain();
    }
 
    private String decorateWithSheerCurtain() {
        return " with sheer curtain";
    }
}
