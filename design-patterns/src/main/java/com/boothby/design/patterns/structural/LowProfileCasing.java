package com.boothby.design.patterns.structural;

public class LowProfileCasing extends WindowDecorator {

    public LowProfileCasing(Window window) {
        super(window);
    }

    public String render() {
        return super.render() + renderWithLowProfileCasing();
    }
 
    private String renderWithLowProfileCasing() {
        return " with low profile casing";
    }
}
