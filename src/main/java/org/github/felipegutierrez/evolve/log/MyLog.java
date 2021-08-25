package org.github.felipegutierrez.evolve.log;

import org.slf4j.Logger;

public class MyLog {
    private final Logger logger;

    /*public MyLog() {
        this(SomeStatic.logger);
    }*/

    MyLog(Logger logger) {
        this.logger = logger;
    };

    public void someOperation() {
        logger.warn("warning message");
        // ...
    };
}
