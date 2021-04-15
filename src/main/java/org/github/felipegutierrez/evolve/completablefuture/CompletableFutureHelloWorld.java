package org.github.felipegutierrez.evolve.completablefuture;

import org.github.felipegutierrez.evolve.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureHelloWorld {

    private final HelloWorldService helloWorldService;

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
}
