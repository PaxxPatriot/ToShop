package com.toshop.application;

import com.toshop.application.interfaces.*;
import com.toshop.domain.entities.Product;
import com.toshop.domain.entities.ShoppingList;
import com.toshop.domain.entities.ShoppingListItem;

import java.util.Collection;
import java.util.Optional;

public class Application {

    private DatabasePlugin database;
    private UIPlugin ui;

    public Application(DatabasePlugin databasePlugin, UIPlugin uiPlugin) {
        this.database = databasePlugin;
        this.ui = uiPlugin;

        System.out.println("Starting ToShop application...");
        System.out.println("Database Plugin: " + databasePlugin.getClass().getName());
        System.out.println("UI Plugin: " + uiPlugin.getClass().getName());

        this.ui.Initialize(this);
    }

    public Collection<ShoppingList> getAllShoppingLists() {
        return this.database.getAllShoppingLists();
    }

    public ShoppingList createShoppingList(String name) {
        var newShoppingList = ShoppingList.create(name);
        this.database.persistShoppingList(newShoppingList);
        return newShoppingList;
    }

    public void deleteShoppingList(ShoppingList shoppingList) {
        this.database.deleteShoppingList(shoppingList);
    }

    public void saveShoppingList(ShoppingList shoppingList) {
        this.database.updateShoppingList(shoppingList);
    }

    public void addProductToShoppingList(ShoppingList shoppingList, String productName, int quantity) {
        var product = database.getProduct(productName);
        if (product.isEmpty()) {
            product = Optional.ofNullable(new Product(productName));
            database.persistProduct(product.get());
        }
        shoppingList.addItem(new ShoppingListItem(shoppingList, product.get(), quantity));
    }

    public Collection<Product> getSuggestedProducts() {
        return this.database.getAllProducts();
    }
}
