package com.boothby.design.patterns.behavioral;

import com.boothby.design.patterns.model.Contractor;

public class MostExperienceStrategy implements ContractorStrategy {

    public MostExperienceStrategy() {}

    @Override
    public Contractor findContractor() {
        return new Contractor("MyFloorIsYourFloor",       // company name 
                28,            // years in business
                4,            // work rating 
                2,            // cost rating
                12,         // distance away from your project
                3);           // days until start
    }

    @Override
    public String toString() {
        return "MostExperienceStrategy []";
    }

}
