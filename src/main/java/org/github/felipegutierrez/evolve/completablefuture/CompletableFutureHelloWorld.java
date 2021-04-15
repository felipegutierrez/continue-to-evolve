package org.github.felipegutierrez.evolve.completablefuture;

import org.apache.commons.lang3.time.StopWatch;
import org.github.felipegutierrez.evolve.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static org.github.felipegutierrez.evolve.util.CommonUtil.delay;
import static org.github.felipegutierrez.evolve.util.LoggerUtil.log;

public class CompletableFutureHelloWorld {

    private final HelloWorldService helloWorldService;
    private final StopWatch stopWatch = new StopWatch();

    public CompletableFutureHelloWorld(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public CompletableFuture<String> helloWorldUpperCase() {
        return CompletableFuture
                .supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<String> helloWorldUpperCaseWithLength() {
        return CompletableFuture
                .supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase)
                .thenApply(value -> value.length() + " - " + value);
    }

    public CompletableFuture<String> helloWorldCombinedUpperCase() {
        stopWatch.reset();
        stopWatch.start();

        CompletableFuture<String> completableFutureHello = CompletableFuture
                .supplyAsync(helloWorldService::hello)
                .thenApply(String::toUpperCase);
        CompletableFuture<String> completableFutureWorld = CompletableFuture
                .supplyAsync(helloWorldService::world)
                .thenApply(String::toUpperCase);
        CompletableFuture<String> completableFutureCombined = completableFutureHello
                .thenCombine(completableFutureWorld, (hello, world) -> hello + world);

        stopWatch.stop();

        return completableFutureCombined;
    }

    public CompletableFuture<String> helloWorldCombined4UpperCase() {
        stopWatch.reset();
        stopWatch.start();

        CompletableFuture<String> completableFutureHello = CompletableFuture
                .supplyAsync(helloWorldService::hello)
                .thenApply(String::toUpperCase);
        CompletableFuture<String> completableFutureWorld = CompletableFuture
                .supplyAsync(helloWorldService::world)
                .thenApply(String::toUpperCase);
        CompletableFuture<String> completableFutureHi = CompletableFuture
                .supplyAsync(() -> {
                    delay(1000);
                    return " hi CompletableFuture!";
                })
                .thenApply(String::toUpperCase);
        CompletableFuture<String> completableFutureBye = CompletableFuture
                .supplyAsync(() -> {
                    delay(1000);
                    return " bye!";
                })
                .thenApply(String::toUpperCase);
        CompletableFuture<String> completableFutureCombined = completableFutureHello
                .handle((value, exception) -> {
                    if (exception != null) log("Hello Exception is: " + exception.getMessage());
                    if (value == null || value.isBlank() || value.isEmpty() || "null".equalsIgnoreCase(value)) return "";
                    else return value;
                })
                .thenCombine(completableFutureWorld, (hello, world) -> hello + world)
                .handle((value, exception) -> {
                    if (exception != null) log("World Exception is: " + exception.getMessage());
                    if (value == null || value.isBlank() || value.isEmpty() || "null".equalsIgnoreCase(value)) return "";
                    else return value;
                })
                .thenCombine(completableFutureHi, (previous, current) -> previous + current)
                .thenCombine(completableFutureBye, (previous, current) -> previous + current);

        stopWatch.stop();

        return completableFutureCombined;
    }

    /**
     * "thenCompose" is a dependent task on the pipeline and it adds to the total time execution of the pipeline
     *
     * @return
     */
    public CompletableFuture<String> helloWorldUpperCaseWithCompose() {
        return CompletableFuture
                .supplyAsync(helloWorldService::hello)
                .thenCompose(previous -> helloWorldService.worldFuture(previous))
                .thenApply(String::toUpperCase);
    }
}
