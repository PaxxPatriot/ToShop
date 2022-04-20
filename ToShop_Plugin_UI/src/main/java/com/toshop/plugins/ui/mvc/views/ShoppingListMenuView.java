package com.toshop.plugins.ui.mvc.views;

import com.toshop.domain.entities.ShoppingList;
import com.toshop.plugins.ui.mvc.base.View;
import com.toshop.plugins.ui.mvc.controllers.ShoppingListMenuController;

import javax.swing.*;
import java.awt.*;

public class ShoppingListMenuView extends View<ShoppingListMenuController> {

    public ShoppingListMenuView(ShoppingList shoppingList) {
        super();
        this.controller.setShoppingList(shoppingList);
    }

    @Override
    public JPanel buildUI() {
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        var backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.goBack());
        main.add(backButton, BorderLayout.SOUTH);

        return main;
    }

}
