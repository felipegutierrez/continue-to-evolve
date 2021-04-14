package org.github.felipegutierrez.evolve.streams;

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
}
