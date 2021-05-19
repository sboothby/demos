package com.boothby.design.patterns.structural;

public class AluminumHandle extends WindowDecorator {

    public AluminumHandle(Window window) {
        super(window);
    }
    
    public String render() {
        return super.render() + renderWithAluminumHandle();
    }
 
    private String renderWithAluminumHandle() {
        return " with aluminum handle";
    }
}
