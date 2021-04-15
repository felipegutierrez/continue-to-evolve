package org.github.felipegutierrez.evolve.completablefuture;

import org.github.felipegutierrez.evolve.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CompletableFutureHelloWorldTest {

    HelloWorldService helloWorldService = new HelloWorldService();
    CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    void helloWorld() {
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorldUpperCase();
        completableFuture
                .thenAccept(value -> assertEquals("HELLO WORLD", value))
                .join();
        completableFuture
                .thenAccept(value -> assertNotEquals("HELLO world", value))
                .join();
    }

    @Test
    void helloWorldUpperCaseWithLength() {
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorldUpperCaseWithLength();
        completableFuture
                .thenAccept(value -> assertEquals("11 - HELLO WORLD", value))
                .join();
        completableFuture
                .thenAccept(value -> assertNotEquals("11 - HELLO world", value))
                .join();
        completableFuture
                .thenAccept(value -> assertNotEquals("10 - HELLO WORLD", value))
                .join();
    }

    @Test
    void helloWorldCombinedUpperCase() {
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorldCombinedUpperCase();
        completableFuture
                .thenAccept(value -> assertEquals("HELLO WORLD!", value))
                .join();
        completableFuture
                .thenAccept(value -> assertNotEquals("HELLO world!", value))
                .join();
    }
}