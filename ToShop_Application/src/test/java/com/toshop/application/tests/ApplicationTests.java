package com.toshop.application.tests;

import com.toshop.application.Application;
import com.toshop.application.tests.mock.MockDatabasePlugin;
import com.toshop.application.tests.mock.MockRecipeProviderPlugin;
import com.toshop.application.tests.mock.MockUIPlugin;
import com.toshop.domain.entities.ShoppingList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class ApplicationTests {

    private static Application testApplication;
    private static MockDatabasePlugin mockDatabase;

    @BeforeAll
    static void initialize() {
        mockDatabase = new MockDatabasePlugin();
        var mockUI = new MockUIPlugin();
        var mockRecipeProvider = new MockRecipeProviderPlugin();
        testApplication = new Application(mockDatabase, mockUI, mockRecipeProvider);
    }

    @Test
    @DisplayName("Create Shopping List")
    void testCreateShoppingList() {
        // Arrange
        String name = "Test List";

        // Act
        ShoppingList createdShoppingList = testApplication.createShoppingList(name);

        // Assert
        assumeTrue(createdShoppingList != null);
        assumeTrue(mockDatabase.getShoppingList(createdShoppingList.getId()).isPresent());
    }

    @Test
    @DisplayName("Delete Shopping List")
    void testDeleteShoppingList() {
        // Arrange
        var testShoppingList = createTestShoppingList("Test List Delete");

        // Act
        testApplication.deleteShoppingList(testShoppingList);

        // Assert
        assumeTrue(mockDatabase.getShoppingList(testShoppingList.getId()).isEmpty());
    }

    @Test
    @DisplayName("Add Product to Shopping List")
    void testAddProduct() {
        // Arrange
        var testShoppingList = createTestShoppingList("Test List Add Product");

        // Act
        testApplication.addProductToShoppingList(testShoppingList, "Test Product", 16);

        // Assert
        assumeTrue(mockDatabase.getProduct("Test Product").isPresent());
        assumeTrue(testShoppingList.getItems().stream().anyMatch(i -> i.getProduct().getName().equals("Test Product")));
    }

    @Test
    @DisplayName("Update Product last used date")
    void testUpdateProductDate() throws InterruptedException {
        // Arrange
        var testShoppingList = createTestShoppingList("Test List Update");

        // Act
        testApplication.addProductToShoppingList(testShoppingList, "Test Product", 16);
        Date firstDate = mockDatabase.getProduct("Test Product").get().getLastAddedDate();
        Thread.sleep(1);
        testApplication.addProductToShoppingList(testShoppingList, "Test Product", 5);
        Date secondDate = mockDatabase.getProduct("Test Product").get().getLastAddedDate();
        // Assert
        assumeTrue(secondDate.after(firstDate));
    }

    private ShoppingList createTestShoppingList(String name) {
        ShoppingList testShoppingList = ShoppingList.create(name);
        mockDatabase.persistShoppingList(testShoppingList);
        return testShoppingList;
    }
}
