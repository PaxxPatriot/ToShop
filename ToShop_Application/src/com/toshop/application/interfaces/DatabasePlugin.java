package com.toshop.application.interfaces;

import com.toshop.domain.entities.ShoppingList;

public interface DatabasePlugin {
    void Persist(ShoppingList list);
    ShoppingList Get(int shoppingListId);
}
