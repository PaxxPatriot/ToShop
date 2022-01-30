package com.toshop.plugins.database;

import com.toshop.application.interfaces.DatabasePlugin;
import com.toshop.domain.entities.ShoppingList;

public class SQLiteDatabasePlugin implements DatabasePlugin {

    private String filePath;

    public SQLiteDatabasePlugin(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void Persist(ShoppingList list) {

    }

    @Override
    public ShoppingList Get(int shoppingListId) {
        System.out.println("SQLite is not yet implemented :(");
        return null;
    }
}
