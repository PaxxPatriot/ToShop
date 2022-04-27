package com.toshop.application;

import com.toshop.application.interfaces.*;
import com.toshop.domain.entities.Product;
import com.toshop.domain.entities.Recipe;
import com.toshop.domain.entities.ShoppingList;
import com.toshop.domain.entities.ShoppingListItem;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class Application {

    private final RecipeProviderPlugin recipeProvider;
    private final DatabasePlugin database;
    private final UIPlugin ui;

    public Application(DatabasePlugin databasePlugin, UIPlugin uiPlugin, RecipeProviderPlugin recipeProviderPlugin) {
        this.database = databasePlugin;
        this.ui = uiPlugin;
        this.recipeProvider = recipeProviderPlugin;
    }

    public void run() {
        System.out.println("Starting ToShop application...");
        System.out.println("Database Plugin: " + database.getClass().getName());
        System.out.println("UI Plugin: " + ui.getClass().getName());
        System.out.println("Recipe Provider Plugin: " + recipeProvider.getClass().getName());
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

    public ShoppingList saveShoppingList(ShoppingList shoppingList) {
        return this.database.updateShoppingList(shoppingList);
    }

    public void addProductToShoppingList(ShoppingList shoppingList, String productName, int amount) {
        var product = database.getProduct(productName);
        if (product.isEmpty()) {
            product = Optional.ofNullable(new Product(productName));
            database.persistProduct(product.get());
        }
        shoppingList.addItem(new ShoppingListItem(shoppingList, product.get(), amount));
    }

    public Collection<Product> getSuggestedProducts() {
        return this.database.getAllProducts();
    }

    public void removeShoppingListItem(ShoppingList currentShoppingList, ShoppingListItem item) {
        currentShoppingList.removeItem(item);
    }

    public CompletableFuture<Collection<Recipe>> searchRecipes(String query) {
        return this.recipeProvider.searchRecipes(query);
    }

    public CompletableFuture<Recipe> getDetailedRecipe(String recipeId) {
        return this.recipeProvider.getDetailedRecipe(recipeId);
    }
}

