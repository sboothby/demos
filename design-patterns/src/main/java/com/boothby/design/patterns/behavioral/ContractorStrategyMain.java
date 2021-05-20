package com.boothby.design.patterns.behavioral;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothby.design.patterns.model.Contractor;

public class ContractorStrategyMain {

    private static final Logger logger = LoggerFactory.getLogger(ContractorStrategyMain.class);

    public static void main(String[] args) {
        // Create strategy finder, used to pick the contractor search stradgey at runtime.
        ContractorClientSearch contractorClientSearch = new ContractorClientSearch();
        
        // Create context for searching contractors with the chosen strategy.
        ContractorContext context = new ContractorContext();

        /*********************************************************************
         * Use case: Find a contractor later, but cost is very important!
        /*********************************************************************/
        ContractorSearchParams searchParams1 = new ContractorSearchParams(
                1,      // min years in business
                10,     // max miles away
                2,      // min work rating
                1,      // max cost rating
                false   // start ASAP
                );
        ContractorStrategy contractorStrategy1 = contractorClientSearch.findStrategyForSearch(searchParams1);
        context.setContractorStrategy(contractorStrategy1);
        Contractor contractor1 = context.findContractor();
        logger.info("Using {}, found contractor 1: {}", contractorStrategy1, contractor1.toString());
        
        /*********************************************************************
         * Use case: Find whomever can work as soon as possible, my house is on fire!
        /*********************************************************************/
        ContractorSearchParams searchParams2 = new ContractorSearchParams(
                2,      // min years in business
                8,      // max miles away
                3,      // min work rating
                3,      // max cost rating
                true    // start ASAP
                );
        ContractorStrategy contractorStrategy2 = contractorClientSearch.findStrategyForSearch(searchParams2);
        context.setContractorStrategy(contractorStrategy2);
        Contractor contractor2 = context.findContractor();
        logger.info("Using {}, found contractor 2: {}", contractorStrategy2, contractor2.toString());

        /*********************************************************************
         * Use case: Find contractor nearest to me so I don't sit around wasting my time!
         *********************************************************************/
        ContractorSearchParams searchParams3 = new ContractorSearchParams(
                5,      // min years in business
                2,      // max miles away
                3,      // min work rating
                3,      // max cost rating
                false   // start ASAP
                );
        ContractorStrategy contractorStrategy3 = contractorClientSearch.findStrategyForSearch(searchParams3);
        context.setContractorStrategy(contractorStrategy3);
        Contractor contractor3 = context.findContractor();
        logger.info("Using {}, found contractor 3: {}", contractorStrategy3, contractor3.toString());

        /*********************************************************************
         * Use case: Get the best person for the job!
         *********************************************************************/
        ContractorSearchParams searchParams4 = new ContractorSearchParams(
                5,      // min years in business
                25,     // max miles away
                5,      // min work rating
                3,      // max cost rating
                false   // start ASAP
        );
        ContractorStrategy contractorStrategy4 = contractorClientSearch.findStrategyForSearch(searchParams4);
        context.setContractorStrategy(contractorStrategy4);
        Contractor contractor4 = context.findContractor();
        logger.info("Using {}, found contractor 4: {}", contractorStrategy4, contractor4.toString());
    }
}
