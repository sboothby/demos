package com.boothby.design.patterns.behavioral;

import com.boothby.design.patterns.model.Contractor;

public class LowestCostStrategy implements ContractorStrategy {

    public LowestCostStrategy() {}

    @Override
    public Contractor findContractor() {
        return new Contractor("Here4UBuilders",       // company name 
                2,            // years in business
                3,            // work rating 
                1,            // cost rating
                15,        // distance away from your project
                7);           // days until start
    }

    @Override
    public String toString() {
        return "LowestCostStrategy []";
    }

}
