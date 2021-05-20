package com.boothby.design.patterns.behavioral;

import java.util.HashMap;
import java.util.Map;

/**
 * Client code that chooses the right strategy for the contractor search.
 */
public class ContractorClientSearch {

    //TODO ideally these are dynamically updatable with new strategies, but at least
    // this is the only class that needs to bring in new strategies.
    private static final String STRATEGY_NEXT_AVAILABLE = "strategy-next-available";
    private static final String STRATEGY_MOST_EXPERIENCE = "strategy-most-experience";
    private static final String STRATEGY_NEAREST = "strategy-nearest";
    private static final String STRATEGY_HIGHEST_RATED = "strategy-highest-rated";
    private static final String STRATEGY_LOWEST_COST = "strategy-lowest-cost";
    private static final String STRAGEGY_RANDOM = "strategy-random";

    private Map<String, ContractorStrategy> mapStrategies;
    
    public ContractorClientSearch() {
        init();
    }
    
    private void init() {
        // Pre-create strategies into a reusable pool, for runtime selection. This can achieve runtime performance 
        // increase, e.g. if certain strategies have some dependent initialization, or associated resources.
        mapStrategies = new HashMap<>();
        mapStrategies.put(STRATEGY_NEXT_AVAILABLE, new NextAvailableStrategy());
        mapStrategies.put(STRATEGY_MOST_EXPERIENCE, new MostExperienceStrategy());
        mapStrategies.put(STRATEGY_NEAREST, new ClosestToMeStrategy());
        mapStrategies.put(STRATEGY_HIGHEST_RATED, new HighestRatedStrategy());
        mapStrategies.put(STRATEGY_LOWEST_COST, new LowestCostStrategy());
        mapStrategies.put(STRAGEGY_RANDOM, new RandomContractorStrategy());
    }
    
    /**
     * Find the ideal strategy to search for client contractor.
     * @param searchParams client-provided search params
     * @return initialized strategy for contractor search
     */
    public ContractorStrategy findStrategyForSearch(ContractorSearchParams searchParams) {
        ContractorStrategy contractorStrategy = null;
        
        //TODO this would be a more sophisticated algorithm for choosing the right contractor strategy
        // cross-referencing all search params.
        if (searchParams.isStartASAP()) {
            contractorStrategy = mapStrategies.get(STRATEGY_NEXT_AVAILABLE);

        } else if (searchParams.getMinYearsInBusiness() > 10) {
            contractorStrategy = mapStrategies.get(STRATEGY_MOST_EXPERIENCE);
        
        } else if (searchParams.getMaxMilesAway() < 5) {
            contractorStrategy = mapStrategies.get(STRATEGY_NEAREST);
        
        } else if (searchParams.getMinWorkRating() > 4) {
            contractorStrategy = mapStrategies.get(STRATEGY_HIGHEST_RATED);
        
        } else if (searchParams.getMaxCostRating() < 2) {
            contractorStrategy = mapStrategies.get(STRATEGY_LOWEST_COST);
        
        } else {
            contractorStrategy = mapStrategies.get(STRAGEGY_RANDOM);
        }

        return contractorStrategy;
    }
}
