package com.toshop.domain.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ShoppingList {

    private UUID id;
    private List<ShoppingListItem> items;

    private String name;

    public static ShoppingList create(String name) {
        var newList = new ShoppingList();
        newList.items = new ArrayList<>();
        newList.id = UUID.randomUUID();
        newList.name = name;
        return newList;
    }

    public UUID getId() {
        return id;
    }
    private void setId(UUID id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
