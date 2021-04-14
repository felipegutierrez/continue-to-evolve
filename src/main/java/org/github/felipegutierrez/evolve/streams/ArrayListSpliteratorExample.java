package org.github.felipegutierrez.evolve.streams;

import java.util.List;
import java.util.stream.Collectors;

import static org.github.felipegutierrez.evolve.util.CommonUtil.*;

public class ArrayListSpliteratorExample {

    public List<Integer> multiplyEachValue(List<Integer> inputList, Integer multiplyFactor, boolean parallel) {
        resetTimer();
        startTimer();
        List<Integer> result = null;
        if (parallel) {
            result = inputList
                    .parallelStream()
                    .map(value -> value * multiplyFactor)
                    .collect(Collectors.toList());
        } else {
            result = inputList
                    .stream()
                    .map(value -> value * multiplyFactor)
                    .collect(Collectors.toList());
        }
        timeTaken();
        return result;
    }
}
