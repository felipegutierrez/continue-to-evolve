package org.github.felipegutierrez.evolve.completablefuture;

import org.apache.commons.lang3.time.StopWatch;
import org.github.felipegutierrez.evolve.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    private final StopWatch stopWatch = new StopWatch();
    HelloWorldService helloWorldService = new HelloWorldService();
    CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    @Timeout(value = 1200, unit = TimeUnit.MILLISECONDS)
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
    @Timeout(value = 1200, unit = TimeUnit.MILLISECONDS)
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
    @Timeout(value = 1200, unit = TimeUnit.MILLISECONDS)
    void helloWorldCombinedUpperCase() {
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorldCombinedUpperCase();
        completableFuture
                .thenAccept(value -> assertEquals("HELLO WORLD!", value))
                .join();
        completableFuture
                .thenAccept(value -> assertNotEquals("HELLO world!", value))
                .join();
    }

    @Test
    @Timeout(value = 2200, unit = TimeUnit.MILLISECONDS)
    void helloWorldCombined4UpperCase() {
        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorldCombined4UpperCase();
        completableFuture
                .thenAccept(value -> assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE! BYE!", value))
                .join();
        completableFuture
                .thenAccept(value -> assertNotEquals("HELLO world! HI COMPLETABLEFUTURE! BYE!", value))
                .join();
    }

    @Test
    @Timeout(value = 2200, unit = TimeUnit.MILLISECONDS)
    void helloWorldUpperCaseWithCompose() {
        stopWatch.reset();
        stopWatch.start();

        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorldUpperCaseWithCompose();
        completableFuture
                .thenAccept(value -> assertEquals("HELLO WORLD!", value))
                .join();
        completableFuture
                .thenAccept(value -> assertNotEquals("HELLO world!", value))
                .join();

        stopWatch.stop();

        long timeElapsed = stopWatch.getTime();
        System.out.println("timeElapsed: " + timeElapsed);
        assertTrue(timeElapsed > 2000);
    }
}