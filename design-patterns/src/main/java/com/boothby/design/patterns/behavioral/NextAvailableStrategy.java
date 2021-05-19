package com.boothby.design.patterns.behavioral;

import com.boothby.design.patterns.model.Contractor;

public class NextAvailableStrategy implements ContractorStrategy {

    public NextAvailableStrategy() {}

    @Override
    public Contractor findContractor() {
        return new Contractor("AnytimeBuiders",       // company name 
                10,            // years in business
                3,            // work rating 
                3,            // cost rating
                4,         // distance away from your project
                1);           // days until start
    }

    @Override
    public String toString() {
        return "NextAvailableStrategy []";
    }
}
