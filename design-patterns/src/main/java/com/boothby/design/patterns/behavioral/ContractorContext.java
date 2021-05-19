package com.boothby.design.patterns.behavioral;

import com.boothby.design.patterns.model.Contractor;

public class ContractorContext {

    private ContractorStrategy contractorStrategy; 
    
    public ContractorContext() {
    }

    public void setContractorStrategy(ContractorStrategy contractorStrategy) {
        this.contractorStrategy = contractorStrategy;
    }
    
    public Contractor findContractor() {
        return contractorStrategy.findContractor();
    }
}
