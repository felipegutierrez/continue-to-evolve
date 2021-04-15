package org.github.felipegutierrez.evolve.completablefuture;

import org.github.felipegutierrez.evolve.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static org.github.felipegutierrez.evolve.util.LoggerUtil.log;

public class CompletableFutureHelloWorld {

    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();

        CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase)
                .thenAccept(result -> log("result is: " + result))
                .join();

        log("Done!");
        // delay(2000);
    }
}
