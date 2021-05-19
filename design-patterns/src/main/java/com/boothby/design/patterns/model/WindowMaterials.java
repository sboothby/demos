package com.boothby.design.patterns.model;

import java.util.List;

public class WindowMaterials implements MaterialCost {

    private List<WindowImpl> windows;
    private List<WindowBorder> windowBorders;

    public WindowMaterials(List<WindowImpl> windows, List<WindowBorder> windowBorders) {
        this.windows = windows;
        this.windowBorders = windowBorders;
    }

    @Override
    public float getCost(MaterialSource materialSource) {
        //TODO do better with costing out the materials!
        return windows.size() * 5.0f +
               windowBorders.size() * 10.0f;
    }
}
