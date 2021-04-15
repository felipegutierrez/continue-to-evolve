package org.github.felipegutierrez.evolve.forkjoin;

import org.apache.commons.lang3.time.StopWatch;
import org.github.felipegutierrez.evolve.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ForkJoinUsingRecursionTest {

    int availableCores = Runtime.getRuntime().availableProcessors();

    @Test
    public void computeForkJoin() {
        StopWatch stopWatch = new StopWatch();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<String> actualList = DataSet.namesList();
        List<String> expectedList = List.of("3 - Bob", "5 - Jamie", "4 - Jill", "4 - Rick");
        ForkJoinUsingRecursion forkJoinExampleUsingRecursion = new ForkJoinUsingRecursion(actualList);

        stopWatch.start();
        List<String> resultList = forkJoinPool.invoke(forkJoinExampleUsingRecursion); // invoke -> Add the task to the shared queue from which all the other qu
        stopWatch.stop();

        System.out.println(resultList);
        assertEquals(expectedList, resultList);
        long duration = stopWatch.getTime();
        System.out.println("Total time taken : " + duration);
        if (availableCores >= 4) {
            assertTrue(duration < 700);
        }
    }
}
