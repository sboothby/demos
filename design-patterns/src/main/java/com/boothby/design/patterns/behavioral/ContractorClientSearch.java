package com.boothby.design.patterns.behavioral;

public class ContractorClientSearch {

    public static ContractorStrategy getSearchStrategy(ContractorSearchParams searchParams) {
        ContractorStrategy contractorStrategy = null;
        
        /**
         * Client chooses the contractor strategy in this pattern. This is the client implementation,
         * the application receiving a set of search params. Ideally, these thresholds that
         * determine the strategy are configurable, and in a real-world example, the choosing
         * of the right strategy might be more complex than this. Or, it might be the end user
         * choose the strategy which is a much simpler example.
         */
        if (searchParams.isStartASAP()) {
            contractorStrategy = new NextAvailableStrategy();

        } else if (searchParams.getMinYearsInBusiness() > 10) {
            contractorStrategy = new MostExperienceStrategy();
        
        } else if (searchParams.getMaxMilesAway() < 5) {
            contractorStrategy = new ClosestToMeStrategy();
        
        } else if (searchParams.getMinWorkRating() > 4) {
            contractorStrategy = new HighestRatedStrategy();
        
        } else if (searchParams.getMaxCostRating() < 2) {
            contractorStrategy = new LowestCostStrategy();
        
        } else {
            contractorStrategy = new RandomContractorStrategy();
        }

        return contractorStrategy;
    }
}
