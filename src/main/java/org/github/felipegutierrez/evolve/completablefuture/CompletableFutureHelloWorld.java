package org.github.felipegutierrez.evolve.completablefuture;

import org.apache.commons.lang3.time.StopWatch;
import org.github.felipegutierrez.evolve.service.HelloWorldService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public String anyOf() {
        // DB
        CompletableFuture<String> db = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            log("response from DB");
            return "response from DB";
        });

        // REST api
        CompletableFuture<String> restCall = CompletableFuture.supplyAsync(() -> {
            delay(2000);
            log("response from REST api");
            return "response from REST";
        });

        // SOAP call
        CompletableFuture<String> soapCall = CompletableFuture.supplyAsync(() -> {
            delay(3000);
            log("response from SOAP call");
            return "response from SOAP";
        });

        List<CompletableFuture<String>> completableFutureList = List.of(db, restCall, soapCall);
        CompletableFuture<Object> completableFutureAnyOf = CompletableFuture
                .anyOf(completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]));

        String result = (String) completableFutureAnyOf
                .thenApply(v -> {
                    if (v instanceof String) return v;
                    else return null;
                })
                .join();
        return result;
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
                .exceptionally(exception -> {
                    log("Hello Exception is: " + exception.getMessage());
                    return "";
                })
                .thenCombine(completableFutureWorld, (hello, world) -> hello + world)
                .exceptionally(exception -> {
                    log("World Exception is: " + exception.getMessage());
                    return "";
                });

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
                    log("Hello is: " + value);
                    if (exception != null) {
                        log("Hello Exception is: " + exception.getMessage());
                        return "";
                    } else {
                        return value;
                    }
                })
                .thenCombine(completableFutureWorld, (hello, world) -> hello + world)
                .exceptionally(exception -> {
                    log("World Exception is: " + exception.getMessage());
                    return "";
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

    public CompletableFuture<String> helloWorldCombined4UpperCaseWithCustomThreadPool() {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        stopWatch.reset();
        stopWatch.start();

        CompletableFuture<String> completableFutureHello = CompletableFuture
                .supplyAsync(helloWorldService::hello, executorService)
                .thenApply(value -> {
                    log("thenApply toUpperCase");
                    return value.toUpperCase();
                });
        CompletableFuture<String> completableFutureWorld = CompletableFuture
                .supplyAsync(helloWorldService::world, executorService)
                .thenApply(value -> {
                    log("thenApply toUpperCase");
                    return value.toUpperCase();
                });
        CompletableFuture<String> completableFutureHi = CompletableFuture
                .supplyAsync(() -> {
                    delay(1000);
                    return " hi CompletableFuture!";
                }, executorService)
                .thenApply(value -> {
                    log("thenApply toUpperCase");
                    return value.toUpperCase();
                });
        CompletableFuture<String> completableFutureBye = CompletableFuture
                .supplyAsync(() -> {
                    delay(1000);
                    return " bye!";
                }, executorService)
                .thenApply(value -> {
                    log("thenApply toUpperCase");
                    return value.toUpperCase();
                });
        CompletableFuture<String> completableFutureCombined = completableFutureHello
                .handle((value, exception) -> {
                    log("Hello is: " + value);
                    if (exception != null) {
                        log("Hello Exception is: " + exception.getMessage());
                        return "";
                    } else {
                        return value;
                    }
                })
                .thenCombine(completableFutureWorld, (hello, world) -> hello + world)
                .exceptionally(exception -> {
                    log("World Exception is: " + exception.getMessage());
                    return "";
                })
                .thenCombine(completableFutureHi, (previous, current) -> previous + current)
                .thenCombine(completableFutureBye, (previous, current) -> previous + current);

        stopWatch.stop();

        return completableFutureCombined;
    }

    public CompletableFuture<String> helloWorldCombined4UpperCaseWithCustomThreadPoolAndAsync() {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        stopWatch.reset();
        stopWatch.start();

        CompletableFuture<String> completableFutureHello = CompletableFuture
                .supplyAsync(helloWorldService::hello, executorService)
                .thenApplyAsync(value -> {
                    log("thenApply toUpperCase");
                    return value.toUpperCase();
                }, executorService);
        CompletableFuture<String> completableFutureWorld = CompletableFuture
                .supplyAsync(helloWorldService::world, executorService)
                .thenApplyAsync(value -> {
                    log("thenApply toUpperCase");
                    return value.toUpperCase();
                }, executorService);
        CompletableFuture<String> completableFutureHi = CompletableFuture
                .supplyAsync(() -> {
                    delay(1000);
                    return " hi CompletableFuture!";
                }, executorService)
                .thenApplyAsync(value -> {
                    log("thenApply toUpperCase");
                    return value.toUpperCase();
                }, executorService);
        CompletableFuture<String> completableFutureBye = CompletableFuture
                .supplyAsync(() -> {
                    delay(1000);
                    return " bye!";
                }, executorService)
                .thenApplyAsync(value -> {
                    log("thenApply toUpperCase");
                    return value.toUpperCase();
                }, executorService);
        CompletableFuture<String> completableFutureCombined = completableFutureHello
                .handleAsync((value, exception) -> {
                    log("Hello is: " + value);
                    if (exception != null) {
                        log("Hello Exception is: " + exception.getMessage());
                        return "";
                    } else {
                        return value;
                    }
                }, executorService)
                .thenCombineAsync(completableFutureWorld, (hello, world) -> {
                    log("thenCombineAsync World");
                    return hello + world;
                }, executorService)
                .exceptionally(exception -> {
                    log("World Exception is: " + exception.getMessage());
                    return "";
                })
                .thenCombineAsync(completableFutureHi, (previous, current) -> {
                    log("thenCombineAsync Hi (previous, current)");
                    return previous + current;
                }, executorService)
                .thenCombineAsync(completableFutureBye, (previous, current) -> {
                    log("thenCombineAsync Bye (previous, current)");
                    return previous + current;
                }, executorService);

        stopWatch.stop();

        return completableFutureCombined;
    }
}
