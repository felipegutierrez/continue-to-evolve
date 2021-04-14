package org.github.felipegutierrez.evolve.streams;

import org.github.felipegutierrez.evolve.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

import static org.github.felipegutierrez.evolve.util.CommonUtil.stopWatch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayListSpliteratorExampleTest {

    ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();
    int expectedSize = 1000000;
    List<Integer> actualList = DataSet.generateArrayList(expectedSize);
    int availableCores = Runtime.getRuntime().availableProcessors();

    @RepeatedTest(5)
    public void multiplyEachValueSequentially() {
        boolean parallel = false;
        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(actualList, 2, parallel);

        assertEquals(expectedSize, resultList.size());
        long duration = stopWatch.getTime();
        System.out.println("Parallel: [" + parallel + "] - total time: " + duration);
        assertTrue(duration < 1000);
    }

    @RepeatedTest(5)
    public void multiplyEachValueParallel() {
        boolean parallel = true;
        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(actualList, 2, parallel);

        assertEquals(expectedSize, resultList.size());
        long duration = stopWatch.getTime();
        System.out.println("Parallel: [" + parallel + "] - total time: " + duration);
        assertTrue(duration < 1000);
    }
}