package com.boothby.design.patterns.behavioral;

import com.boothby.design.patterns.model.Contractor;

public class RandomContractorStrategy implements ContractorStrategy {

    public RandomContractorStrategy() {}

    @Override
    public Contractor findContractor() {
        return new Contractor("PayNowOrPayLater",       // company name 
                5,            // years in business
                4,            // work rating 
                4,            // cost rating
                12,         // distance away from your project
                5);           // days until start
    }

    @Override
    public String toString() {
        return "RandomContractorStrategy []";
    }
}
