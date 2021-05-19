package com.boothby.design.patterns.model;

import java.util.List;

public class WallMaterials implements MaterialCost {

    private List<Paint> paints;
    private List<Drywall> drywalls;
    
    public WallMaterials(List<Paint> paints, List<Drywall> drywalls) {
        this.paints = paints;
        this.drywalls = drywalls;
    }

    @Override
    public float getCost(MaterialSource materialSource) {
        //TODO do better with costing out the materials!
        return paints.size() * 10.0f +
                drywalls.size() * 22.0f;
    }
}
