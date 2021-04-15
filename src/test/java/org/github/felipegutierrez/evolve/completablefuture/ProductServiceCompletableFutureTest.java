package org.github.felipegutierrez.evolve.completablefuture;

import org.github.felipegutierrez.evolve.domain.Product;
import org.github.felipegutierrez.evolve.product.ProductInfoService;
import org.github.felipegutierrez.evolve.product.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceCompletableFutureTest {

    private final ProductInfoService productInfoService = new ProductInfoService();
    private final ReviewService reviewService = new ReviewService();
    ProductServiceCompletableFuture productServiceCompletableFuture = new ProductServiceCompletableFuture(productInfoService, reviewService);

    @Test
    @Timeout(value = 1200, unit = TimeUnit.MILLISECONDS)
    void retrieveProductDetails() {

        String productId = "ABC123";
        Product product = productServiceCompletableFuture.retrieveProductDetails(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }
}