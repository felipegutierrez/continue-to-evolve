package org.github.felipegutierrez.evolve.forkjoin;

import org.github.felipegutierrez.evolve.util.DataSet;
import org.github.felipegutierrez.evolve.util.LoggerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static org.github.felipegutierrez.evolve.util.CommonUtil.delay;
import static org.github.felipegutierrez.evolve.util.CommonUtil.stopWatch;

public class ForkJoinPoolExample {
    ForkJoinPool forkJoinPool = new ForkJoinPool();

    public static void main(String[] args) {

        ForkJoinPoolExample forkJoinPoolExample = new ForkJoinPoolExample();
        stopWatch.start();

        List<String> resultList = forkJoinPoolExample.compute(DataSet.namesList());
        LoggerUtil.log("resultList : " + resultList);

        stopWatch.stop();
        LoggerUtil.log("Total time taken : " + stopWatch.getTime());
    }

    public List<String> compute(List<String> inputList) {

        return forkJoinPool.invoke(new RecursiveTask<>() {
            @Override
            protected List<String> compute() {
                List<ForkJoinTask<String>> forkJoinTasks = new ArrayList<>();
                List<String> responseList = new ArrayList<>();

                inputList.forEach((item) -> {
                    forkJoinTasks.add(new RecursiveTask<String>() {
                        @Override
                        protected String compute() {
                            // call the long computation here
                            return transform(item);
                        }
                    }.fork()); // This adds the task to the forkjoin queue
                });
                //Joining RecursiveTask
                forkJoinTasks.forEach(task -> {
                    responseList.add(task.join()); //This retrieves the result
                });

                return responseList;
            }
        });
    }

    private String transform(String s) {
        // simulating a long computation
        delay(500);
        return s.length() + " - " + s;
    }
}
