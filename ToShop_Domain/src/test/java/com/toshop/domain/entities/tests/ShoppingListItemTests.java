package com.toshop.domain.entities.tests;

import com.toshop.domain.entities.Product;
import com.toshop.domain.entities.ShoppingList;
import com.toshop.domain.entities.ShoppingListItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingListItemTests {
    @Test
    void getTotalPriceTest() {
        // Arrange
        ShoppingList shoppingList = ShoppingList.create();
        Product product = new Product(10.0, "Produkt");
        ShoppingListItem item = new ShoppingListItem(shoppingList, product, 2);

        // Act
        double totalPrice = item.getTotalPrice();

        // Assert
        assertEquals(totalPrice, 20.0);
    }
}
