package org.github.felipegutierrez.evolve.product;

import org.github.felipegutierrez.evolve.domain.Product;
import org.github.felipegutierrez.evolve.domain.ProductInfo;
import org.github.felipegutierrez.evolve.domain.Review;

import java.util.concurrent.*;

import static org.github.felipegutierrez.evolve.util.CommonUtil.stopWatch;
import static org.github.felipegutierrez.evolve.util.LoggerUtil.log;

public class ProductServiceExecutor {
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public ProductServiceExecutor(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceExecutor productService = new ProductServiceExecutor(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

        executorService.shutdown();
    }

    public Product retrieveProductDetails(String productId) throws ExecutionException, InterruptedException, TimeoutException {
        stopWatch.start();

        Future<ProductInfo> productInfoFuture = executorService.submit(() -> productInfoService.retrieveProductInfo(productId));
        Future<Review> reviewFuture = executorService.submit(() -> reviewService.retrieveReviews(productId));

        // ProductInfo productInfo = productInfoFuture.get();
        ProductInfo productInfo = productInfoFuture.get(2, TimeUnit.SECONDS);
        Review review = reviewFuture.get(2, TimeUnit.SECONDS);

        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }
}
