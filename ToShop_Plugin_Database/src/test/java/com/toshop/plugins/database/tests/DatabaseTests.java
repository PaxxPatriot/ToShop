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
        testShoppingList.addItem(new ShoppingListItem(testShoppingList, Product.create("Cheese"), 2));
        testShoppingList.addItem(new ShoppingListItem(testShoppingList, Product.create("Bread"), 1));

        databasePlugin.persistShoppingList(testShoppingList);

        var readList = databasePlugin.getShoppingList(testShoppingList.getId());

        assumeTrue(readList.isPresent());

        var list = readList.get();

        assumeTrue(list.getId() == testShoppingList.getId());
        assumeTrue(list.getItems().size() == testShoppingList.getItems().size());

        databasePlugin.deleteShoppingList(list);

        var readListAfterDelete = databasePlugin.getShoppingList(testShoppingList.getId());

        assumeTrue(readListAfterDelete.isEmpty());
    }

    @Test
    void testMerge() {
        // Arrange
        ShoppingList testShoppingList = ShoppingList.create("test_list_update");
        testShoppingList.addItem(new ShoppingListItem(testShoppingList, Product.create("Cheese"), 2));
        testShoppingList.addItem(new ShoppingListItem(testShoppingList, Product.create("Bread"), 1));
        databasePlugin.persistShoppingList(testShoppingList);

        // Act
        testShoppingList.addItem(new ShoppingListItem(testShoppingList, Product.create("Tomato"), 2));
        databasePlugin.updateShoppingList(testShoppingList);

        var readListAfterUpdate = databasePlugin.getShoppingList(testShoppingList.getId());

        // Assert
        assumeTrue(readListAfterUpdate.isPresent());
        assumeTrue(readListAfterUpdate.get().getItems().size() == 3);
    }
}
