package com.toshop.plugins.ui.mvc.controllers;

import com.toshop.domain.entities.ShoppingList;
import com.toshop.plugins.ui.mvc.base.Controller;
import com.toshop.plugins.ui.mvc.views.MainMenuView;
import com.toshop.plugins.ui.mvc.views.ShoppingListMenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainMenuController extends Controller<MainMenuView> {

    public MainMenuController(MainMenuView view) {
        super(view);
    }

    @Override
    public void init() {

    }

    public void createNewShoppingList() {
        var name = JOptionPane.showInputDialog("Enter a name for the new shopping list:");

        if (name == null || name.isEmpty()) return;

        var newShoppingList = application.createShoppingList(name);
        ui.setView(new ShoppingListMenuView(newShoppingList));
    }

    public void selectShoppingList(ShoppingList shoppingList) {
        ui.setView(new ShoppingListMenuView(shoppingList));
    }

    public void deleteShoppingList(ShoppingList shoppingList) {
        application.deleteShoppingList(shoppingList);
        ui.setView(new MainMenuView());
    }
}
