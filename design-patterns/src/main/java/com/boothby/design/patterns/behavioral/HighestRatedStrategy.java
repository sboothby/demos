package com.boothby.design.patterns.behavioral;

import com.boothby.design.patterns.model.Contractor;

public class HighestRatedStrategy implements ContractorStrategy {

    public HighestRatedStrategy() {}

    @Override
    public Contractor findContractor() {
        return new Contractor("Acme",       // company name 
                              8,            // years in business
                              5,            // work rating 
                              3,            // cost rating
                              10,        // distance away from your project
                              5);           // days until start
    }

    @Override
    public String toString() {
        return "HighestRatedStrategy []";
    }
}
