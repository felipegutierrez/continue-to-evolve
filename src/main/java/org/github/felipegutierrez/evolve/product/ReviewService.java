package org.github.felipegutierrez.evolve.product;

import org.github.felipegutierrez.evolve.product.domain.Review;

import static org.github.felipegutierrez.evolve.util.CommonUtil.delay;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        delay(1000);
        return new Review(200, 4.5);
    }
}
