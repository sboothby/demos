package com.boothby.design.patterns.model;

public class Contractor {

    private String companyName;     // contractor company name
    private int yearsInBusiness;    // how many years contractor in business
    private int workRating;         // 1 (low) - 5 (high) quality of work
    private int costRating;         // 1 (low) - 5 (high) how much they charge
    private int milesAway;        // how far away the contractor business is from your project
    private int daysUntilStart;        // how many days from now they can start the work
    
    public Contractor(String companyName, int yearsInBusiness, int workRating, int costRating, int milesAway, int daysUntilStart) {
        this.companyName = companyName;
        this.yearsInBusiness = yearsInBusiness;
        this.workRating = workRating;
        this.costRating = costRating;
        this.milesAway = milesAway;
        this.daysUntilStart = daysUntilStart;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getYearsInBusiness() {
        return yearsInBusiness;
    }

    public void setYearsInBusiness(int yearsInBusiness) {
        this.yearsInBusiness = yearsInBusiness;
    }

    public int getWorkRating() {
        return workRating;
    }

    public void setWorkRating(int workRating) {
        this.workRating = workRating;
    }

    public int getCostRating() {
        return costRating;
    }

    public void setCostRating(int costRating) {
        this.costRating = costRating;
    }

    public int getMilesAway() {
        return milesAway;
    }

    public void setMilesAway(int milesAway) {
        this.milesAway = milesAway;
    }
    
    public int getDaysUntilStart() {
        return daysUntilStart;
    }

    public void setDaysUntilStart(int daysUntilStart) {
        this.daysUntilStart = daysUntilStart;
    }

    @Override
    public String toString() {
        return "Contractor [companyName=" + companyName + ", yearsInBusiness=" + yearsInBusiness + ", workRating="
                + workRating + ", costRating=" + costRating + ", milesAway=" + milesAway + ", daysUntilStart="
                + daysUntilStart + "]";
    }
}
