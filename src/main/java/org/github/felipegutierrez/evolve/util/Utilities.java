package org.github.felipegutierrez.evolve.util;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utilities {
    public static final Logger logger = LoggerFactory.getLogger("AnyUniqueStringHere");

    public static void printLoggerState() {
        // print internal state
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
    }
}
