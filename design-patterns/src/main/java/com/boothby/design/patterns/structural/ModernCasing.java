package com.boothby.design.patterns.structural;

public class ModernCasing extends WindowDecorator {

    public ModernCasing(Window window) {
        super(window);
    }

    public String render() {
        return super.render() + renderWithModernCasing();
    }
 
    private String renderWithModernCasing() {
        return " with modern casing";
    }
}
