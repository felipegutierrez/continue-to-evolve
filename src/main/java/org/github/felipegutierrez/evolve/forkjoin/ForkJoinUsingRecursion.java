package org.github.felipegutierrez.evolve.forkjoin;

import org.github.felipegutierrez.evolve.util.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static org.github.felipegutierrez.evolve.util.CommonUtil.delay;
import static org.github.felipegutierrez.evolve.util.CommonUtil.stopWatch;
import static org.github.felipegutierrez.evolve.util.LoggerUtil.log;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {

    private List<String> inputList;

    public ForkJoinUsingRecursion(List<String> inputList) {
        this.inputList = inputList;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinUsingRecursion forkJoinExampleUsingRecursion = new ForkJoinUsingRecursion(DataSet.namesList());
        stopWatch.start();

        // Start things running and get the result back, This is blocked until the results are calculated.
        List<String> resultList = forkJoinPool.invoke(forkJoinExampleUsingRecursion); // invoke -> Add the task to the shared queue from which all the other qu

        log("resultList : " + resultList);

        stopWatch.stop();
        log("Total time taken : " + stopWatch.getTime());
    }

    @Override
    protected List<String> compute() {

        if (this.inputList.size() <= 1) {
            List<String> resultList = new ArrayList<>();
            inputList.forEach(name -> resultList.add(transform(name)));
            return resultList;
        }

        int middleOfTheInputList = inputList.size() / 2;
        ForkJoinTask<List<String>> leftInputList = new ForkJoinUsingRecursion(inputList.subList(0, middleOfTheInputList))
                .fork(); // push this task to the queue asynchronously
        // only the right side of the list is remaining
        inputList = inputList.subList(middleOfTheInputList, inputList.size());
        List<String> rightResult = compute();
        List<String> leftResult = leftInputList.join();
        log("leftResult: " + leftResult);
        leftResult.addAll(rightResult);
        return leftResult;
    }

    private String transform(String s) {
        // simulating a long computation
        delay(500);
        return s.length() + " - " + s;
    }
}
