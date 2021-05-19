package com.boothby.design.patterns.model;

import java.util.List;

public class FloorMaterials implements MaterialCost {

    private AbstractFloor floor;
    private List<Paint> paints;
    private List<FloorTrim> floorTrims;
    private Subfloor subfloor;
    
    public FloorMaterials(AbstractFloor floor, List<Paint> paints, List<FloorTrim> floorTrims, Subfloor subfloor) {
        this.floor = floor;
        this.paints = paints;
        this.floorTrims = floorTrims;
        this.subfloor = subfloor;
    }

    @Override
    public float getCost(MaterialSource materialSource) {
        //TODO do better with costing out the materials!
        return 100.0f +     // cost of the floor!
               paints.size() * 10.0f +
               floorTrims.size() * 7.0f + 
               10.0f;       // cost of the subfloor!
    }
}
