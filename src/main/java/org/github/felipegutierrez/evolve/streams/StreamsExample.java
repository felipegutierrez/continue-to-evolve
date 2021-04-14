package org.github.felipegutierrez.evolve.streams;

import org.github.felipegutierrez.evolve.util.Operation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.github.felipegutierrez.evolve.util.CommonUtil.*;
import static org.github.felipegutierrez.evolve.util.LoggerUtil.log;
import static org.github.felipegutierrez.evolve.util.Operation.MULTI;
import static org.github.felipegutierrez.evolve.util.Operation.SUM;

public class StreamsExample {

    public static void main(String[] args) {

        List<String> namesList = List.of("Bob", "Jamie", "Jill", "Rick");
        log("namesList : " + namesList);
        startTimer();
        StreamsExample parallelismExample = new StreamsExample();
        List<String> resultList = parallelismExample.stringTransform(namesList);
        timeTaken();
        log("resultList : " + resultList);
    }

    public List<String> stringTransform(List<String> namesList) {
        return namesList
                .stream() // sequential
                .map(this::transform)
                .collect(Collectors.toList());
    }

    public List<String> stringTransform(List<String> namesList, boolean parallel) {
        Stream<String> namesStream = namesList.stream(); // sequential
        if (parallel) {
            // turn the pipeline chain to execute in parallel
            namesStream.parallel();
        }
        return namesStream
                .map(this::transform)
                .collect(Collectors.toList());
    }

    public List<String> stringTransformParallel02(List<String> namesList) {
        return namesList
                .parallelStream() // turn the pipeline chain to execute in parallel
                .map(this::transform)
                .collect(Collectors.toList());
    }

    public Integer reduceInParallel(List<Integer> inputList, int identity, Operation operation) {
        return inputList
                .parallelStream() // turn the pipeline chain to execute in parallel
                .reduce(identity, (x, y) -> {
                    if (operation == SUM) {
                        return x + y;
                    } else if (operation == MULTI) {
                        return x * y;
                    } else {
                        return x + y;
                    }
                });
    }

    private String transform(String name) {
        delay(500);
        //log("Transforming : " + name);
        return name.length() + " - " + name;
    }
}
