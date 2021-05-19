package com.boothby.design.patterns.structural;

public class PlasticHandle extends WindowDecorator {

    public PlasticHandle(Window window) {
        super(window);
    }

    public String render() {
        return super.render() + renderWithPlasticHandle();
    }
 
    private String renderWithPlasticHandle() {
        return " with plastic handle";
    }
}
