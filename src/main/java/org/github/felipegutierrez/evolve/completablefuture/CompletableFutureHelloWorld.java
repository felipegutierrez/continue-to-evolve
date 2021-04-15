package org.github.felipegutierrez.evolve.completablefuture;

import org.apache.commons.lang3.time.StopWatch;
import org.github.felipegutierrez.evolve.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

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
}
