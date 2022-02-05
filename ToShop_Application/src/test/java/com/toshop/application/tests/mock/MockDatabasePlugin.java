package com.toshop.application.tests.mock;

import com.toshop.application.interfaces.DatabasePlugin;
import com.toshop.domain.entities.ShoppingList;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class MockDatabasePlugin implements DatabasePlugin {

    private final HashMap<UUID, ShoppingList> database = new HashMap<UUID, ShoppingList>();

    public void persist(ShoppingList list) {
        database.put(list.getId(), list);
    }

    public Optional<ShoppingList> get(UUID shoppingListId) {
        return Optional.ofNullable(database.get(shoppingListId));
    }
}
