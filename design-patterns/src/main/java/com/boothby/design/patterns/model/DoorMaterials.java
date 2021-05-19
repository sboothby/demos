package com.boothby.design.patterns.model;

import java.util.List;

public class DoorMaterials implements MaterialCost {

    private List<Door> doors;
    private List<DoorKnob> doorKnobs;
    private List<Paint> paints;
    
    public DoorMaterials(List<Door> doors, List<DoorKnob> doorKnobs, List<Paint> paints) {
        this.doors = doors;
        this.doorKnobs = doorKnobs;
        this.paints = paints;
    }

    @Override
    public float getCost(MaterialSource materialSource) {
        //TODO do better with costing out the materials!
        return doors.size() * 10.0f +
               doorKnobs.size() * 5.0f +
               paints.size() * 8.0f;
    }
}
