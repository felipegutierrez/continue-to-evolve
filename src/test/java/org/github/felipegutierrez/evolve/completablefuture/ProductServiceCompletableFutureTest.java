package org.github.felipegutierrez.evolve.completablefuture;

import org.github.felipegutierrez.evolve.domain.Product;
import org.github.felipegutierrez.evolve.product.ProductInfoService;
import org.github.felipegutierrez.evolve.product.ReviewService;
import org.github.felipegutierrez.evolve.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceCompletableFutureTest {

    @Mock
    private final ProductInfoService productInfoService = mock(ProductInfoService.class);
    @Mock
    private final ReviewService reviewService = mock(ReviewService.class);
    @Mock
    private final InventoryService inventoryService = mock(InventoryService.class);
    @InjectMocks
    ProductServiceCompletableFuture productServiceCompletableFuture;

    @Test
    @Timeout(value = 1200, unit = TimeUnit.MILLISECONDS)
    void retrieveProductDetailsClient() {

        String productId = "ABC123";
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(reviewService.retrieveReviews(any())).thenCallRealMethod();

        Product product = productServiceCompletableFuture.retrieveProductDetailsClient(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    @Timeout(value = 1200, unit = TimeUnit.MILLISECONDS)
    void retrieveProductDetailsServer() {

        String productId = "ABC123";
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(reviewService.retrieveReviews(any())).thenCallRealMethod();

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
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(reviewService.retrieveReviews(any())).thenCallRealMethod();
        when(inventoryService.retrieveInventory(any())).thenCallRealMethod();

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
    @Timeout(value = 1700, unit = TimeUnit.MILLISECONDS)
    void retrieveProductDetailsClientWithInventoryAsync() {
        String productId = "ABC123";
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(reviewService.retrieveReviews(any())).thenCallRealMethod();
        when(inventoryService.retrieveInventory(any())).thenCallRealMethod();

        Product product = productServiceCompletableFuture.retrieveProductDetailsClientWithInventoryAsync(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> {
                    assertNotNull(productOption.getInventory());
                });
        assertNotNull(product.getReview());
    }

    @Test
    @Timeout(value = 1700, unit = TimeUnit.MILLISECONDS)
    void retrieveProductDetailsClientWithInventoryAsyncWithExceptionOnReview() {
        String productId = "ABC123";
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(reviewService.retrieveReviews(any())).thenThrow(new RuntimeException("this is an exception!"));
        when(inventoryService.retrieveInventory(any())).thenCallRealMethod();

        Product product = productServiceCompletableFuture.retrieveProductDetailsClientWithInventoryAsync(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> {
                    assertNotNull(productOption.getInventory());
                });
        assertNotNull(product.getReview());
        assertEquals(0, product.getReview().getNoOfReviews());
        assertEquals(0.0, product.getReview().getOverallRating());
    }

    @Test
    @Timeout(value = 1700, unit = TimeUnit.MILLISECONDS)
    void retrieveProductDetailsClientWithInventoryAsyncWithExceptionOnProductInfo() {
        String productId = "ABC123";
        when(productInfoService.retrieveProductInfo(any())).thenThrow(new RuntimeException("this is an exception!"));
        when(reviewService.retrieveReviews(any())).thenCallRealMethod();

        Product product = productServiceCompletableFuture.retrieveProductDetailsClientWithInventoryAsync(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() == 0);
        assertNotNull(product.getReview());
    }

    @Test
    @Timeout(value = 1700, unit = TimeUnit.MILLISECONDS)
    void retrieveProductDetailsClientWithInventoryAsyncWithExceptionOnInventory() {
        String productId = "ABC123";
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(reviewService.retrieveReviews(any())).thenCallRealMethod();
        when(inventoryService.retrieveInventory(any())).thenThrow(new RuntimeException("this is an exception!"));

        Product product = productServiceCompletableFuture.retrieveProductDetailsClientWithInventoryAsync(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions()
                .forEach(productOption -> {
                    assertNotNull(productOption.getInventory());
                    assertEquals(0, productOption.getInventory().getCount());
                });
        assertNotNull(product.getReview());
    }
}