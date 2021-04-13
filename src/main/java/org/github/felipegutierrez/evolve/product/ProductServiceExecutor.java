package org.github.felipegutierrez.evolve.product;

import org.github.felipegutierrez.evolve.product.domain.Product;
import org.github.felipegutierrez.evolve.product.domain.ProductInfo;
import org.github.felipegutierrez.evolve.product.domain.Review;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.github.felipegutierrez.evolve.product.util.CommonUtil.stopWatch;
import static org.github.felipegutierrez.evolve.product.util.LoggerUtil.log;

public class ProductServiceExecutor {
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;

    public ProductServiceExecutor(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceExecutor productService = new ProductServiceExecutor(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }

    public Product retrieveProductDetails(String productId) throws ExecutionException, InterruptedException {
        stopWatch.start();

        Future<ProductInfo> productInfoFuture = executorService.submit(() -> productInfoService.retrieveProductInfo(productId));
        Future<Review> reviewFuture = executorService.submit(() -> reviewService.retrieveReviews(productId));

        ProductInfo productInfo = productInfoFuture.get();
        Review review = reviewFuture.get();

        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }
}
