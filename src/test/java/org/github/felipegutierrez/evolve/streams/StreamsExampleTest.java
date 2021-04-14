package org.github.felipegutierrez.evolve.streams;

import org.github.felipegutierrez.evolve.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.github.felipegutierrez.evolve.util.CommonUtil.*;
import static org.github.felipegutierrez.evolve.util.LoggerUtil.log;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamsExampleTest {

    List<String> actualList = DataSet.namesList();
    List<String> expectedList = List.of("3 - Bob", "5 - Jamie", "4 - Jill", "4 - Rick");

    @Test
    public void computeStream() {

        log("namesList : " + actualList);
        resetTimer();
        startTimer();
        StreamsExample parallelismExample = new StreamsExample();
        List<String> resultList = parallelismExample.stringTransform(actualList);
        timeTaken();
        log("resultList : " + resultList);

        System.out.println(resultList);
        assertEquals(expectedList, resultList);
        long duration = stopWatch.getTime();
        System.out.println("Total time taken : " + duration);
        assertTrue(duration < 2500);
    }

    @Test
    public void computeStreamParallel01() {
        log("namesList : " + actualList);
        resetTimer();
        startTimer();
        StreamsExample parallelismExample = new StreamsExample();
        List<String> resultList = parallelismExample.stringTransformParallel01(actualList);
        timeTaken();
        log("resultList : " + resultList);

        System.out.println(resultList);
        assertEquals(expectedList, resultList);
        long duration = stopWatch.getTime();
        System.out.println("Total time taken : " + duration);
        assertTrue(duration < 600);
    }
}
