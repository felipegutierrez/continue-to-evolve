package org.github.felipegutierrez.evolve.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest {

    public static Logger logger = LoggerFactory.getLogger(UtilitiesTest.class);

    @Test
    public void myTest(){
        Utilities.printLoggerState(); // to console
        logger.info("Hello World");
    }
}