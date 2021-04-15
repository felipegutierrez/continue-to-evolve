package org.github.felipegutierrez.evolve.product;

import org.github.felipegutierrez.evolve.domain.ProductInfo;
import org.github.felipegutierrez.evolve.domain.ProductOption;

import java.util.Arrays;
import java.util.List;

import static org.github.felipegutierrez.evolve.util.CommonUtil.delay;

public class ProductInfoService {

    public ProductInfo retrieveProductInfo(String productId) {
        delay(1000);
        List<ProductOption> productOptions =
                Arrays.asList(new ProductOption(1, "64GB", "Black", 699.99),
                        new ProductOption(2, "128GB", "Black", 749.99));
        return new ProductInfo(productId, productOptions);
    }
}
