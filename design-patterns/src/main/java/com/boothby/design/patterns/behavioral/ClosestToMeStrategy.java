package com.boothby.design.patterns.behavioral;

import com.boothby.design.patterns.model.Contractor;

public class ClosestToMeStrategy implements ContractorStrategy {

    public ClosestToMeStrategy() {}

    @Override
    public Contractor findContractor() {
        return new Contractor("WeGotYouCarpted",       // company name 
                3,            // years in business
                3,            // work rating 
                3,            // cost rating
                1,         // distance away from your project
                5);           // days until start
    }

    @Override
    public String toString() {
        return "ClosestToMeStrategy []";
    }
}
