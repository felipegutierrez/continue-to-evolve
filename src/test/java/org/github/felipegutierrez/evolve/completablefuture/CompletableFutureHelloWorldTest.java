package org.github.felipegutierrez.evolve.completablefuture;

import org.apache.commons.lang3.time.StopWatch;
import org.github.felipegutierrez.evolve.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldTest {

    private final StopWatch stopWatch = new StopWatch();
    @Mock
    HelloWorldService helloWorldService = mock(HelloWorldService.class);
    @InjectMocks
    CompletableFutureHelloWorld completableFutureHelloWorld;

    @Test
    @Timeout(value = 1200, unit = TimeUnit.MILLISECONDS)
    void helloWorld() {
        when(helloWorldService.helloWorld()).thenCallRealMethod();

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
        when(helloWorldService.helloWorld()).thenCallRealMethod();

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
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorldCombinedUpperCase();
        completableFuture
                .thenAccept(value -> assertEquals("HELLO WORLD!", value))
                .join();
        completableFuture
                .thenAccept(value -> assertNotEquals("HELLO world!", value))
                .join();
    }

    @Test
    @Timeout(value = 1200, unit = TimeUnit.MILLISECONDS)
    void helloWorldCombinedUpperCaseWithException() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("this is a generated exception!"));
        when(helloWorldService.world()).thenCallRealMethod();

        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorldCombinedUpperCase();
        completableFuture
                .thenAccept(value -> assertEquals(" WORLD!", value))
                .join();
        completableFuture
                .thenAccept(value -> assertNotEquals("HELLO WORLD!", value))
                .join();
    }

    @Test
    @Timeout(value = 2200, unit = TimeUnit.MILLISECONDS)
    void helloWorldCombined4UpperCase() {
        when(helloWorldService.hello()).thenReturn("hello");
        when(helloWorldService.world()).thenCallRealMethod();

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
    void helloWorldCombined4UpperCaseWithException() {

        when(helloWorldService.hello()).thenThrow(new RuntimeException("this is a generated exception!"));
        when(helloWorldService.world()).thenCallRealMethod();

        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorldCombined4UpperCase();
        completableFuture
                .thenAccept(value -> assertEquals(" WORLD! HI COMPLETABLEFUTURE! BYE!", value))
                .join();
        completableFuture
                .thenAccept(value -> assertNotEquals("HELLO WORLD! HI COMPLETABLEFUTURE! BYE!", value))
                .join();
    }

    @Test
    @Timeout(value = 2200, unit = TimeUnit.MILLISECONDS)
    void helloWorldCombined4UpperCaseWithTwoException() {

        when(helloWorldService.hello()).thenThrow(new RuntimeException("this is a generated exception!"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("this is a generated exception!"));

        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorldCombined4UpperCase();
        completableFuture
                .thenAccept(value -> assertEquals(" HI COMPLETABLEFUTURE! BYE!", value))
                .join();
        completableFuture
                .thenAccept(value -> assertNotEquals("HELLO WORLD! HI COMPLETABLEFUTURE! BYE!", value))
                .join();
    }

    @Test
    @Timeout(value = 2200, unit = TimeUnit.MILLISECONDS)
    void helloWorldUpperCaseWithCompose() {
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.worldFuture("hello")).thenCallRealMethod();

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

    @Test
    @Timeout(value = 2200, unit = TimeUnit.MILLISECONDS)
    void helloWorldCombined4UpperCaseWithCustomThreadPool() {
        when(helloWorldService.hello()).thenReturn("hello");
        when(helloWorldService.world()).thenCallRealMethod();

        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorldCombined4UpperCaseWithCustomThreadPool();
        completableFuture
                .thenAccept(value -> assertEquals("HELLO WORLD! HI COMPLETABLEFUTURE! BYE!", value))
                .join();
        completableFuture
                .thenAccept(value -> assertNotEquals("HELLO world! HI COMPLETABLEFUTURE! BYE!", value))
                .join();
    }

}