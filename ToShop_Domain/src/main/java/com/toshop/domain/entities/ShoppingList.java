package com.toshop.domain.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ShoppingList {

    private UUID id;
    private List<ShoppingListItem> items;

    public static ShoppingList create() {
        var newList = new ShoppingList();
        newList.items = new ArrayList<>();
        newList.id = UUID.randomUUID();
        return newList;
    }

    public UUID getId() {
        return id;
    }
    private void setId(UUID id) { this.id = id; }

    public void addItem(ShoppingListItem item) {
        items.add(item);
    }

    public void removeItem(ShoppingListItem item) {
        items.remove(item);
    }

    public List<ShoppingListItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    private void setItems(List<ShoppingListItem> items) {
        this.items = items;
    }
}
