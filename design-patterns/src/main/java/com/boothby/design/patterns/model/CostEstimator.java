package com.boothby.design.patterns.model;

public class CostEstimator {

    private float shippingCosts;
    private float localTaxRate;

    public CostEstimator(float shippingCosts, float localTaxRate) {
        this.shippingCosts = shippingCosts;
        this.localTaxRate = localTaxRate;
    }

    public float getRetailCostEstimate(ConstructionMaterials constructionMaterials) {
        return getEstimate(constructionMaterials, MaterialSource.RETAIL);
    }

    public float getWholesaleCostEstimate(ConstructionMaterials constructionMaterials) {
        return getEstimate(constructionMaterials, MaterialSource.WHOLESALE);
    }

    private float getEstimate(ConstructionMaterials constructionMaterials, MaterialSource materialSource) {
        float estimate = constructionMaterials.getDoorMaterials().getCost(materialSource)
                + constructionMaterials.getWindowMaterials().getCost(materialSource)
                + constructionMaterials.getFloorMaterials().getCost(materialSource)
                + constructionMaterials.getCeilingMaterials().getCost(materialSource)
                + constructionMaterials.getWallMaterials().getCost(materialSource);

        estimate += shippingCosts;

        estimate *= localTaxRate;

        return estimate;
    }
}
