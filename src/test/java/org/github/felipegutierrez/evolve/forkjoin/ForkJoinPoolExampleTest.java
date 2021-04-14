package org.github.felipegutierrez.evolve.forkjoin;

import org.github.felipegutierrez.evolve.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.github.felipegutierrez.evolve.util.CommonUtil.stopWatch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ForkJoinPoolExampleTest {

    @Test
    public void computeForkJoin() {
        List<String> actualList = DataSet.namesList();
        List<String> expectedList = List.of("3 - Bob", "5 - Jamie", "4 - Jill", "4 - Rick");
        ForkJoinPoolExample forkJoinPoolExample = new ForkJoinPoolExample();

        stopWatch.start();
        List<String> resultList = forkJoinPoolExample.compute(actualList);
        stopWatch.stop();

        System.out.println(resultList);
        assertEquals(expectedList, resultList);
        long duration = stopWatch.getTime();
        System.out.println("Total time taken : " + duration);
        assertTrue(duration < 1500);
    }
}
