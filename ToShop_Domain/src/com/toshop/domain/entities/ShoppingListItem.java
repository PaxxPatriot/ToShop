package com.toshop.domain.entities;

public class ShoppingListItem {
    private Product product;
    private int amount;

    public double getTotalPrice() {
        return product.getPrice() * amount;
    }
}
