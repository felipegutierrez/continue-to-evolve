package org.github.felipegutierrez.evolve.service;

import org.apache.commons.lang3.time.StopWatch;
import org.github.felipegutierrez.evolve.domain.Cart;
import org.github.felipegutierrez.evolve.domain.CartItem;
import org.github.felipegutierrez.evolve.domain.CheckoutResponse;
import org.github.felipegutierrez.evolve.domain.CheckoutStatus;

import java.util.List;

import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.toList;
import static org.github.felipegutierrez.evolve.util.LoggerUtil.log;

public class CheckoutService {

    private final PriceValidatorService priceValidatorService;

    private final StopWatch stopWatch = new StopWatch();

    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart) {

        stopWatch.start();
        List<CartItem> priceValidationList = cart.getCartItemList()
                //.stream()
                .parallelStream()
                .map(cartItem -> {
                    boolean isPriceValid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isPriceValid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(toList());
        stopWatch.stop();
        stopWatch.reset();

        if (priceValidationList.size() > 0) {
            log("Checkout Error");
            return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
        }

        //double finalRate = calculateFinalPrice(cart);
        double finalRate = calculateFinalPrice_reduce(cart);
        log("Checkout Complete and the final rate is " + finalRate);

        return new CheckoutResponse(CheckoutStatus.SUCCESS, finalRate);
    }

    private double calculateFinalPrice(Cart cart) {
        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .collect(summingDouble(Double::doubleValue));
        //.mapToDouble(Double::doubleValue)
        //.sum();
    }

    private double calculateFinalPrice_reduce(Cart cart) {
        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                //.reduce(0.0, (x,y)->x+y);
                .reduce(0.0, Double::sum);
        //Identity for multiplication is 1
        //Identity for addition  is 0
    }
}

