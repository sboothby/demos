package com.boothby.design.patterns.model;

public class WindowPlacement {

    public enum WindowLocation {
        FRONT_CENTER, RIGHT_CENTER, BACK_CENTER, LEFT_CENTER
    }

    private WindowImpl window;
    private WindowLocation windowLocation;

    public WindowImpl getWindow() {
        return window;
    }

    public void setWindow(WindowImpl window) {
        this.window = window;
    }

    public WindowLocation getWindowLocation() {
        return windowLocation;
    }

    public void setWindowLocation(WindowLocation windowLocation) {
        this.windowLocation = windowLocation;
    }

    @Override
    public String toString() {
        return "WindowPlacement [window=" + window + ", windowLocation=" + windowLocation + "]";
    }
}
