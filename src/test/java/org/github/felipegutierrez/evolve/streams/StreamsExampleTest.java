package org.github.felipegutierrez.evolve.streams;

import org.github.felipegutierrez.evolve.util.Operation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.github.felipegutierrez.evolve.util.CommonUtil.*;
import static org.github.felipegutierrez.evolve.util.LoggerUtil.log;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamsExampleTest {

    StreamsExample parallelismExample = new StreamsExample();
    List<String> actualList = List.of("Bob", "Jamie", "Jill", "Rick");
    List<String> expectedList = List.of("3 - Bob", "5 - Jamie", "4 - Jill", "4 - Rick");
    int availableCores = Runtime.getRuntime().availableProcessors();

    @Test
    public void computeStream() {

        log("namesList : " + actualList);
        resetTimer();
        startTimer();

        List<String> resultList = parallelismExample.stringTransform(actualList);
        timeTaken();
        log("resultList : " + resultList);

        assertEquals(4, resultList.size());

        System.out.println(resultList);
        assertEquals(expectedList, resultList);
        long duration = stopWatch.getTime();
        System.out.println("Total time taken : " + duration);
        assertTrue(duration < 2500);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    public void computeStream(boolean parallel) {
        log("namesList : " + actualList);
        resetTimer();
        startTimer();

        List<String> resultList = parallelismExample.stringTransform(actualList, parallel);
        timeTaken();
        log("resultList : " + resultList);

        assertEquals(4, resultList.size());

        System.out.println(resultList);
        assertEquals(expectedList, resultList);
        long duration = stopWatch.getTime();
        System.out.println("Total time taken : " + duration);
        if (parallel) {
            if (availableCores >= 4) {
                assertTrue(duration < 600);
            }
        } else {
            assertTrue(duration < 2500);
        }
    }

    @Test
    public void computeStreamParallel02() {

        log("namesList : " + actualList);
        resetTimer();
        startTimer();

        List<String> resultList = parallelismExample.stringTransformParallel02(actualList);
        timeTaken();
        log("resultList : " + resultList);

        assertEquals(4, resultList.size());

        System.out.println(resultList);
        assertEquals(expectedList, resultList);
        long duration = stopWatch.getTime();
        System.out.println("Total time taken : " + duration);
        if (availableCores >= 4) {
            assertTrue(duration < 600);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void reduceSum(int identity) {
        Integer result = parallelismExample.reduceInParallel(List.of(1, 2, 3, 4, 5, 6, 7, 8), identity, Operation.SUM);
        if (identity == 0) {
            int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8; // 36
            assertEquals(expected, result);
        } else if (identity == 1) {
            int expected = 1 + 1 + 2 + 1 + 3 + 1 + 4 + 1 + 5 + 1 + 6 + 1 + 7 + 1 + 8 + 1; // 44
            assertEquals(expected, result);
        } else if (identity == 2) {
            int expected = 1 + 2 + 2 + 2 + 3 + 2 + 4 + 2 + 5 + 2 + 6 + 2 + 7 + 2 + 8 + 2; // 52
            assertEquals(expected, result);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void reduceMulti(int identity) {
        Integer result = parallelismExample.reduceInParallel(List.of(1, 2, 3, 4), identity, Operation.MULTI);
        if (identity == 0) {
            int expected = 1 * 0 * 2 * 0 * 3 * 0 * 4; // 0
            assertEquals(expected, result);
        } else if (identity == 1) {
            int expected = 1 * 1 * 2 * 1 * 3 * 1 * 4 * 1; // 24
            assertEquals(expected, result);
        } else if (identity == 2) {
            int expected = 1 * 2 * 2 * 2 * 3 * 2 * 4 * 2; // 384
            assertEquals(expected, result);
        }
    }
}
