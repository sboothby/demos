package com.boothby.design.patterns.behavioral;

public class ContractorSearchParams {

    private int minYearsInBusiness;
    private int maxMilesAway;
    private int minWorkRating;
    private int maxCostRating;
    private boolean startASAP;

    public ContractorSearchParams(int minYearsInBusiness, 
            int maxMilesAway, int minWorkRating, 
            int maxCostRating, boolean startASAP) {
        this.minYearsInBusiness = minYearsInBusiness;
        this.maxMilesAway = maxMilesAway;
        this.minWorkRating = minWorkRating;
        this.maxCostRating = maxCostRating;
        this.startASAP = startASAP;
    }

    public int getMinYearsInBusiness() {
        return minYearsInBusiness;
    }

    public void setMinYearsInBusiness(int minYearsInBusiness) {
        this.minYearsInBusiness = minYearsInBusiness;
    }

    public int getMaxMilesAway() {
        return maxMilesAway;
    }

    public void setMaxMilesAway(int maxMilesAway) {
        this.maxMilesAway = maxMilesAway;
    }

    public int getMinWorkRating() {
        return minWorkRating;
    }

    public void setMinWorkRating(int minWorkRating) {
        this.minWorkRating = minWorkRating;
    }

    public int getMaxCostRating() {
        return maxCostRating;
    }

    public void setMaxCostRating(int maxCostRating) {
        this.maxCostRating = maxCostRating;
    }

    public boolean isStartASAP() {
        return startASAP;
    }

    public void setStartASAP(boolean startASAP) {
        this.startASAP = startASAP;
    }
}
