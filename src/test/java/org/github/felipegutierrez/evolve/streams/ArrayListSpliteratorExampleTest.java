package org.github.felipegutierrez.evolve.streams;

import org.github.felipegutierrez.evolve.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.github.felipegutierrez.evolve.util.CommonUtil.stopWatch;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void multiplyEachValueSet() {
        Set<Integer> actualSet = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Set<Integer> result = arrayListSpliteratorExample.multiplyEachValue(actualSet);
        Integer[] array = result.toArray(new Integer[0]);
        assertNotEquals(20, array[9]);
    }

    @Test
    void multiplyEachValueList() {
        List<Integer> actualList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> result = arrayListSpliteratorExample.multiplyEachValue(actualList);
        Integer[] array = result.toArray(new Integer[0]);
        assertEquals(20, array[9]);
    }
}