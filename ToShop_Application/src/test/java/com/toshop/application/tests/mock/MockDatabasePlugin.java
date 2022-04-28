package com.toshop.application.tests.mock;

import com.toshop.application.interfaces.DatabasePlugin;
import com.toshop.domain.entities.Product;
import com.toshop.domain.entities.ShoppingList;

import java.util.*;

public class MockDatabasePlugin implements DatabasePlugin {

    private final HashMap<UUID, ShoppingList> shoppingListDatabase = new HashMap<>();
    private final HashMap<String, Product> productDatabase = new HashMap<>();

    public void persistShoppingList(ShoppingList list) {
        shoppingListDatabase.put(list.getId(), list);
    }

    public Optional<ShoppingList> getShoppingList(UUID shoppingListId) {
        return Optional.ofNullable(shoppingListDatabase.get(shoppingListId));
    }

    @Override
    public Collection<ShoppingList> getAllShoppingLists() {
        return shoppingListDatabase.values();
    }

    @Override
    public void deleteShoppingList(ShoppingList list) {
        shoppingListDatabase.remove(list.getId());
    }

    @Override
    public ShoppingList updateShoppingList(ShoppingList shoppingList) {
        shoppingListDatabase.put(shoppingList.getId(), shoppingList);
        return shoppingList;
    }

    @Override
    public void persistProduct(Product product) {
        productDatabase.put(product.getName(), product);
    }

    public Collection<Product> getAllProducts() {
        return productDatabase.values();
    }

    @Override
    public Optional<Product> getProduct(String name) {
        return Optional.ofNullable(productDatabase.get(name));
    }
}
