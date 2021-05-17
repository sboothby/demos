package com.boothby.design.patterns.structural;

public class ModernCasing extends WindowDecorator {

    public ModernCasing(Window window) {
        super(window);
    }

    public String decorate() {
        return super.decorate() + decorateWithModernCasing();
    }
 
    private String decorateWithModernCasing() {
        return " with modern casing";
    }
}
