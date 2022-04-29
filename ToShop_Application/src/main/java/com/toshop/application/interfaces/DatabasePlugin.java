package com.toshop.application.interfaces;

import com.toshop.domain.entities.Product;
import com.toshop.domain.entities.ShoppingList;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface DatabasePlugin {
    void persistShoppingList(ShoppingList list);
    Optional<ShoppingList> getShoppingList(UUID shoppingListId);
    Collection<ShoppingList> getAllShoppingLists();
    void deleteShoppingList(ShoppingList list);
    ShoppingList updateShoppingList(ShoppingList shoppingList);

    void persistProduct(Product product);
    Collection<Product> getAllProducts();
    Optional<Product> getProduct(String name);
    Product updateProduct(Product product);
}
