package org.github.felipegutierrez.evolve.completablefuture;

import org.github.felipegutierrez.evolve.domain.*;
import org.github.felipegutierrez.evolve.product.ProductInfoService;
import org.github.felipegutierrez.evolve.product.ReviewService;
import org.github.felipegutierrez.evolve.service.InventoryService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.github.felipegutierrez.evolve.util.CommonUtil.stopWatch;
import static org.github.felipegutierrez.evolve.util.LoggerUtil.log;

public class ProductServiceCompletableFuture {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;
    private final InventoryService inventoryService;

    public ProductServiceCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this(productInfoService, reviewService, new InventoryService());
    }

    public ProductServiceCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService, InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public Product retrieveProductDetailsClient(String productId) {
        stopWatch.reset();
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId)); // non-blocking
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId)); // non-blocking
        Product product = productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product(productId, productInfo, review))
                .join(); // block the thread

        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return product;
    }

    public CompletableFuture<Product> retrieveProductDetailsServer(String productId) {
        stopWatch.reset();
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId)); // non-blocking
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId)); // non-blocking
        CompletableFuture<Product> product = productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture,
                        (productInfo, review) -> new Product(productId, productInfo, review)); // non-blocking

        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return product;
    }

    public Product retrieveProductDetailsClientWithInventory(String productId) {
        stopWatch.reset();
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(productInfo -> {
                    productInfo.setProductOptions(updateInventoryNestedCall(productInfo));
                    return productInfo;
                });
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId)); // non-blocking
        Product product = productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product(productId, productInfo, review))
                .join(); // block the thread

        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return product;
    }

    private List<ProductOption> updateInventoryNestedCall(ProductInfo productInfo) {
        List<ProductOption> productOptionList = productInfo.getProductOptions()
                .stream()
                .map(productOption -> {
                    Inventory inventory = inventoryService.retrieveInventory(productOption);
                    productOption.setInventory(inventory);
                    return productOption;
                })
                .collect(Collectors.toList());
        return productOptionList;
    }

    public Product retrieveProductDetailsClientWithInventoryAsync(String productId) {
        stopWatch.reset();
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .exceptionally(e -> {
                    log("Handled exception in productInfoService.retrieveProductInfo(" + productId + ")");
                    return ProductInfo.builder()
                            .productId(productId)
                            .productOptions(List.of())
                            .build();
                })
                .thenApply(productInfo -> {
                    productInfo.setProductOptions(updateInventoryNestedCallAsync(productInfo));
                    return productInfo;
                });
        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId)) // non-blocking
                .exceptionally(e -> {
                    log("Handled exception in reviewService.retrieveReviews(" + productId + ")");
                    return Review.builder()
                            .noOfReviews(0)
                            .overallRating(0.0)
                            .build();
                });
        Product product = productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture, (productInfo, review) -> new Product(productId, productInfo, review))
                .join(); // block the thread

        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return product;
    }

    private List<ProductOption> updateInventoryNestedCallAsync(ProductInfo productInfo) {
        List<CompletableFuture<ProductOption>> completableFutureList = productInfo.getProductOptions()
                .stream()
                .map(productOption -> {
                    return CompletableFuture
                            .supplyAsync(() -> inventoryService.retrieveInventory(productOption))
                            .exceptionally(e -> {
                                log("Handled exception in inventoryService.retrieveInventory(" + productOption + ")");
                                return Inventory.builder().count(0).build();
                            })
                            .thenApply(inventory -> {
                                productOption.setInventory(inventory);
                                return productOption;
                            });
                })
                .collect(Collectors.toList());
        return completableFutureList
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
