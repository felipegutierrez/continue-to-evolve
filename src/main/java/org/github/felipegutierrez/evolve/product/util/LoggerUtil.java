package org.github.felipegutierrez.evolve.product.util;

public class LoggerUtil {
    public static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] - " + message);
    }
}
