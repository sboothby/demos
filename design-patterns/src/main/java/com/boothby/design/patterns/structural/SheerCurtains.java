package com.boothby.design.patterns.structural;

public class SheerCurtains extends WindowDecorator {

    public SheerCurtains(Window window) {
        super(window);
    }

    public String render() {
        return super.render() + renderWithSheerCurtain();
    }
 
    private String renderWithSheerCurtain() {
        return " with sheer curtain";
    }
}
