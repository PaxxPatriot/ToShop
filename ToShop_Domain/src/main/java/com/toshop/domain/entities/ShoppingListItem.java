package com.toshop.domain.entities;

import java.util.UUID;

public class ShoppingListItem {
    private UUID id;
    private Product product;
    private int amount;
    private ShoppingList shoppingList;

    private ShoppingListItem() {

    }

    public ShoppingListItem(ShoppingList shoppingList, Product product, int amount) {
        this.id = UUID.randomUUID();
        this.shoppingList = shoppingList;
        this.product = product;
        this.amount = amount;
    }

    public UUID getId() { return id; }
    private void setId(UUID id) { this.id = id; }

    public Product getProduct() {
        return product;
    }

    private void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    private void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    @Override
    public String toString() {
        return amount + "x " + product.getName();
    }
}
