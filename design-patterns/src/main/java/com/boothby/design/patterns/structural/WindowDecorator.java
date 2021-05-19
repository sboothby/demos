package com.boothby.design.patterns.structural;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WindowDecorator implements Window {

    private static final Logger logger = LoggerFactory.getLogger(WindowDecorator.class);
    
    private Window window;
    
    public WindowDecorator(Window window) {
        this.window = window;
    }
    
    @Override
    public String render() {
        logger.info("rendering window...");
        return window.render();
    }
}

