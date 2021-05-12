package com.boothby.design.patterns.creational;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapServerSingletonMain {

    private static final Logger logger = LoggerFactory.getLogger(MapServerSingletonMain.class);
    
    public static void main(String[] args) {
        // Create first time.
        MapServerSingleton mapServerSingleton1 = MapServerSingleton.getInstance();
        
        // Try to create again - same object returned.
        MapServerSingleton mapServerSingleton2 = MapServerSingleton.getInstance();
        
        assert(mapServerSingleton1.equals(mapServerSingleton2));
        logger.info("{} == {}", mapServerSingleton1, mapServerSingleton2);
    }
}
