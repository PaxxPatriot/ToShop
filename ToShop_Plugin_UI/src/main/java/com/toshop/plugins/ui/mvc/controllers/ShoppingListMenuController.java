package com.toshop.plugins.ui.mvc.controllers;

import com.toshop.domain.entities.Product;
import com.toshop.domain.entities.ShoppingList;
import com.toshop.domain.entities.ShoppingListItem;
import com.toshop.plugins.ui.mvc.base.Controller;
import com.toshop.plugins.ui.mvc.views.MainMenuView;
import com.toshop.plugins.ui.mvc.views.ShoppingListMenuView;

import java.util.ArrayList;
import java.util.Collection;

public class ShoppingListMenuController extends Controller<ShoppingListMenuView> {

    public ShoppingList currentShoppingList;

    public ShoppingListMenuController(ShoppingListMenuView view) {
        super(view);
    }

    @Override
    public void init() {

    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.currentShoppingList = shoppingList;
    }

    public void goBack() {
        ui.setView(new MainMenuView());
    }

    public Collection<Product> getSuggestedProducts() {
        return application.getSuggestedProducts();
    }

    public void addItem(String text, int amount) {
        application.addProductToShoppingList(currentShoppingList, text, amount);
        application.saveShoppingList(currentShoppingList);
        view.update();
    }
}
