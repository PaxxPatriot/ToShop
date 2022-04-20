package com.toshop.application.interfaces;

import com.toshop.domain.entities.ShoppingList;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface DatabasePlugin {
    void persist(ShoppingList list);
    Optional<ShoppingList> get(UUID shoppingListId);
    Collection<ShoppingList> getAll();
}
