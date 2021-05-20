package com.boothby.design.patterns.structural;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothby.design.patterns.model.MaterialSource;
import com.boothby.design.patterns.structural.ConstructionFacade.FloorType;

public class ConstructionFacadeMain {

    private static final Logger logger = LoggerFactory.getLogger(ConstructionFacadeMain.class);
    
    public static void main(String[] args) {
        /**
         * Let's build something - but how much will it cost?!  
         * It's too complicated - I need a Facade!
         */
        ConstructionFacade constructionFacade = new ConstructionFacade();
        
        logger.info("Build a room with a hardwood floor...");
        float hardwoodRoomTotalMaterialCosts =
                constructionFacade.estimateRoomMaterialConstructionCosts(
                    MaterialSource.WHOLESALE, 
                    2,              // # doors  
                    4,              // # windows 
                    12,             // roomWidth 
                    16,             // roomLength 
                    FloorType.HARDWOOD);
        logger.info("It will cost ${} to build your hardwood floor room.", hardwoodRoomTotalMaterialCosts);

        logger.info("*****");

        logger.info("Build a room with a tile floor...");
        float tileRoomTotalMaterialCosts = 
                constructionFacade.estimateRoomMaterialConstructionCosts(
                        MaterialSource.WHOLESALE,
                        2,          // # doors  
                        4,          // # windows 
                        12,         // roomWidth 
                        16,         // roomLength
                        FloorType.TILE);
        logger.info("It will cost ${} to build your tile floor room.", tileRoomTotalMaterialCosts);
    }
}
