package com.toshop.application;

import com.toshop.application.interfaces.*;
import com.toshop.domain.entities.ShoppingList;

import java.util.Collection;

public class Application {

    private DatabasePlugin database;
    private UIPlugin ui;

    public Application(DatabasePlugin databasePlugin, UIPlugin uiPlugin) {
        this.database = databasePlugin;
        this.ui = uiPlugin;

        this.ui.Initialize(this);
    }

    public Collection<ShoppingList> getAllShoppingLists() {
        return this.database.getAll();
    }

    public ShoppingList createShoppingList(String name) {
        var newShoppingList = ShoppingList.create(name);
        this.database.persist(newShoppingList);
        return newShoppingList;
    }
}
