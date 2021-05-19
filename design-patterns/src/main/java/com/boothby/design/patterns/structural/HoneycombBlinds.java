package com.boothby.design.patterns.structural;

public class HoneycombBlinds extends WindowDecorator {

    public HoneycombBlinds(Window window) {
        super(window);
    }

    public String render() {
        return super.render() + renderWithHoneycombBlinds();
    }
 
    private String renderWithHoneycombBlinds() {
        return " with honeycomb blinds";
    }
}
