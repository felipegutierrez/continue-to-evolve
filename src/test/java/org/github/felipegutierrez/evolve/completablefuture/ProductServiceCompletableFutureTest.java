package org.github.felipegutierrez.evolve.completablefuture;

import org.github.felipegutierrez.evolve.domain.Product;
import org.github.felipegutierrez.evolve.product.ProductInfoService;
import org.github.felipegutierrez.evolve.product.ReviewService;
import org.github.felipegutierrez.evolve.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceCompletableFutureTest {

    private final ProductInfoService productInfoService = new ProductInfoService();
    private final ReviewService reviewService = new ReviewService();
    private final InventoryService inventoryService = new InventoryService();
    ProductServiceCompletableFuture productServiceCompletableFuture = new ProductServiceCompletableFuture(productInfoService, reviewService, inventoryService);

    @Test
    @Timeout(value = 1200, unit = TimeUnit.MILLISECONDS)
    void retrieveProductDetailsClient() {

        String productId = "ABC123";
        Product product = productServiceCompletableFuture.retrieveProductDetailsClient(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    @Timeout(value = 1200, unit = TimeUnit.MILLISECONDS)
    void retrieveProductDetailsServer() {

        String productId = "ABC123";
        Product product = productServiceCompletableFuture.retrieveProductDetailsServer(productId)
                .join();

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    @Timeout(value = 2200, unit = TimeUnit.MILLISECONDS)
    void retrieveProductDetailsClientWithInventory() {
        String productId = "ABC123";
        Product product = productServiceCompletableFuture.retrieveProductDetailsClientWithInventory(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> {
                    assertNotNull(productOption.getInventory());
                });
        assertNotNull(product.getReview());
    }

    @Test
    @Timeout(value = 1600, unit = TimeUnit.MILLISECONDS)
    void retrieveProductDetailsClientWithInventoryAsync() {
        String productId = "ABC123";
        Product product = productServiceCompletableFuture.retrieveProductDetailsClientWithInventoryAsync(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> {
                    assertNotNull(productOption.getInventory());
                });
        assertNotNull(product.getReview());
    }
}