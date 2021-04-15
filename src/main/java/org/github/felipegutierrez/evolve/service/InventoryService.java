package org.github.felipegutierrez.evolve.service;

import org.github.felipegutierrez.evolve.domain.Inventory;
import org.github.felipegutierrez.evolve.domain.ProductOption;

import static org.github.felipegutierrez.evolve.util.CommonUtil.delay;

public class InventoryService {
    public Inventory retrieveInventory(ProductOption productOption) {
        delay(500);
        return Inventory.builder()
                .count(2).build();
    }
}
