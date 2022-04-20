package com.toshop.plugins.database.tests;

import com.toshop.domain.entities.Product;
import com.toshop.domain.entities.ShoppingList;
import com.toshop.domain.entities.ShoppingListItem;
import com.toshop.plugins.database.SQLiteDatabasePlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class DatabaseTests {

    private static SQLiteDatabasePlugin databasePlugin;

    @BeforeAll
    static void initialize() {
        databasePlugin = new SQLiteDatabasePlugin("test.db");
    }

    @Test
    void testPersistAndGet() {
        ShoppingList testShoppingList = ShoppingList.create("test_list");
        testShoppingList.addItem(new ShoppingListItem(testShoppingList, new Product(4.99, "Cheese"), 2));
        testShoppingList.addItem(new ShoppingListItem(testShoppingList, new Product(2.99, "Bread"), 1));

        databasePlugin.persist(testShoppingList);

        var readList = databasePlugin.get(testShoppingList.getId());

        assumeTrue(readList.isPresent());

        var list = readList.get();

        assumeTrue(list.getId() == testShoppingList.getId());
        assumeTrue(list.getItems().size() == testShoppingList.getItems().size());
    }
}
