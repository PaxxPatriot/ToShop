package com.toshop.plugins.ui.mvc.controllers;

import com.toshop.domain.entities.ShoppingList;
import com.toshop.plugins.ui.mvc.base.Controller;
import com.toshop.plugins.ui.mvc.views.MainMenuView;
import com.toshop.plugins.ui.mvc.views.ShoppingListMenuView;

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
}
