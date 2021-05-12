package com.boothby.design.patterns.creational;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapServerSingleton {

    private static final Logger logger = LoggerFactory.getLogger(MapServerSingleton.class);

    private static volatile MapServerSingleton instance = null;
    
    /**
     * Constructor (private)
     * Only this class can create instance of this object, no other classes can.
     */
    private MapServerSingleton() {
    }

    public static MapServerSingleton getInstance() {
        if (instance == null) {
            synchronized (MapServerSingleton.class) {
                // Double check ensures a second thread can't create the instance if it already got created by another thread.
                if (instance == null) {
                    instance = new MapServerSingleton();
                    logger.info("** Created new instance of MapServerSingleton **");
                }
            }
        }
        return instance;
    }
}
