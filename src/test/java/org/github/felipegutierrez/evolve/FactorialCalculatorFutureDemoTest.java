package org.github.felipegutierrez.evolve;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FactorialCalculatorFutureDemoTest {

    @Test
    public void computeFactorial() throws Exception {
        if (Runtime.getRuntime().availableProcessors() >= 4) {
            FactorialCalculatorFutureDemo factorialDemo = new FactorialCalculatorFutureDemo(20, 500);
            assertTrue(factorialDemo.compute());
        }
    }
}
