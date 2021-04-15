package org.github.felipegutierrez.evolve.service;

import org.github.felipegutierrez.evolve.domain.CartItem;

import static org.github.felipegutierrez.evolve.util.CommonUtil.delay;
import static org.github.felipegutierrez.evolve.util.LoggerUtil.log;

public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem) {
        int cartId = cartItem.getItemId();
        log("isCartItemInvalid : " + cartItem);
        delay(500);
        return cartId == 7 || cartId == 9 || cartId == 11;
    }
}
