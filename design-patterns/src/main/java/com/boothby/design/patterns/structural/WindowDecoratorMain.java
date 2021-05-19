package com.boothby.design.patterns.structural;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boothby.design.patterns.model.Dimensions;
import com.boothby.design.patterns.model.WindowImpl;
import com.boothby.design.patterns.model.WindowImpl.WindowType;

public class WindowDecoratorMain {

    private static final Logger logger = LoggerFactory.getLogger(WindowDecoratorMain.class);

    public static void main(String[] args) {

        /**
         * Create several types of window with distinct features. Note in each case, the original code WindowImpl is unchanged.
         */
        
        Window frontYardWindow = new PlasticHandle(
                new LowProfileCasing(
                        new HoneycombBlinds(
                                new WindowImpl(WindowType.DOUBLE_HUNG, new Dimensions(2.0f, 3.0f, .75f), "Anderson")
                                )
                        )
                );
        String frontYardWindowResult = frontYardWindow.render();
        logger.info(frontYardWindowResult);
        
        // Create another type of window with it's features.  Note the original code is unchanged (WindowImpl).
        Window kitchenWindow = new AluminumHandle(
                    new ModernCasing (
                            new SheerCurtains (
                                    new WindowImpl(WindowType.SINGLE_HUNG, new Dimensions(3.0f, 4.0f, .75f), "Marvin")
                                    )
                            )
                    );
        String backyardWindowResult = kitchenWindow.render();
        logger.info(backyardWindowResult);
    }
}
