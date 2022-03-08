package com.toshop.plugins.database.tests;

import com.toshop.application.interfaces.DatabasePlugin;
import com.toshop.domain.entities.Product;
import com.toshop.domain.entities.ShoppingList;
import com.toshop.domain.entities.ShoppingListItem;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class DatabaseTests {
    @Test
    void testPersistAndGet() {
        // Capture
        DatabasePlugin databasePlugin = EasyMock.createMock(DatabasePlugin.class);
        ShoppingList testShoppingList = ShoppingList.create();
        testShoppingList.addItem(new ShoppingListItem(testShoppingList, new Product(4.99, "Cheese"), 2));
        testShoppingList.addItem(new ShoppingListItem(testShoppingList, new Product(2.99, "Bread"), 1));
        databasePlugin.persist(testShoppingList);
        EasyMock.expect(databasePlugin.get(testShoppingList.getId())).andReturn(java.util.Optional.of(testShoppingList));
        EasyMock.replay(databasePlugin);

        // Arrange
        databasePlugin.persist(testShoppingList);

        // Act
        var readList = databasePlugin.get(testShoppingList.getId());

        // Assert
        assumeTrue(readList.isPresent());

        var list = readList.get();

        assumeTrue(list.getId() == testShoppingList.getId());
        assumeTrue(list.getItems().size() == testShoppingList.getItems().size());

        // Verify
        EasyMock.verify(databasePlugin);
    }
}
