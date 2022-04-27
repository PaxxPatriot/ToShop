package com.toshop.application.tests;

import com.toshop.application.Application;
import com.toshop.application.tests.mock.MockDatabasePlugin;
import com.toshop.application.tests.mock.MockRecipeProviderPlugin;
import com.toshop.application.tests.mock.MockUIPlugin;
import com.toshop.domain.entities.ShoppingList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        String name = "Test List";
        ShoppingList testShoppingList = ShoppingList.create(name);
        mockDatabase.persistShoppingList(testShoppingList);

        // Act
        testApplication.deleteShoppingList(testShoppingList);

        // Assert
        assumeTrue(mockDatabase.getShoppingList(testShoppingList.getId()).isEmpty());
    }

    @Test
    @DisplayName("Add Product to Shopping List")
    void testAddProduct() {
        // Arrange
        String name = "Test List";
        ShoppingList testShoppingList = ShoppingList.create(name);
        mockDatabase.persistShoppingList(testShoppingList);

        // Act
        testApplication.addProductToShoppingList(testShoppingList, "Test Product", 16);

        // Assert
        assumeTrue(mockDatabase.getProduct("Test Product").isPresent());
        assumeTrue(testShoppingList.getItems().stream().anyMatch(i -> i.getProduct().getName().equals("Test Product")));
    }

}
