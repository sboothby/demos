package com.boothby.design.patterns.behavioral;

import com.boothby.design.patterns.model.Contractor;

/**
 * The context handles the invocation and response from the strategy it is given.
 */
public class ContractorContext {

    private ContractorStrategy contractorStrategy; 
    
    public ContractorContext() {}
    
    /**
     * Constructor
     * Set an initial strategy.
     * @param contractorStrategy
     */
    public ContractorContext(ContractorStrategy contractorStrategy) {
        this.contractorStrategy = contractorStrategy;
    }

    /**
     * Change the strategy at runtime.
     * @param contractorStrategy new strategy to find a contractor
     */
    public void setContractorStrategy(ContractorStrategy contractorStrategy) {
        this.contractorStrategy = contractorStrategy;
    }
    
    /**
     * Find the contractor using the current strategy.
     * @return new contractor
     */
    public Contractor findContractor() {
        Contractor foundContractor = contractorStrategy.findContractor();
        
        //TODO could handle errors, exceptions, log, or cache the results here.
        
        return foundContractor;
    }
}
