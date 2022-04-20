package com.toshop.plugins.ui.mvc.views;

import com.toshop.domain.entities.ShoppingList;
import com.toshop.plugins.ui.mvc.base.View;
import com.toshop.plugins.ui.mvc.controllers.MainMenuController;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends View<MainMenuController> {

    @Override
    public JPanel buildUI() {
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        var topLabel = new JLabel("Select a shopping list or create a new one", SwingConstants.CENTER);
        topLabel.setFont(ui.getDefaultFont().deriveFont(24f));
        main.add(topLabel, BorderLayout.NORTH);

        // Main center panel that contains all shopping lists and the create button
        var buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Shopping list buttons
        for (ShoppingList shoppingList : application.getAllShoppingLists()) {
            var thisList = shoppingList;
            var selectListButton = new JButton();
            selectListButton.setText(shoppingList.getName());
            selectListButton.setPreferredSize(new Dimension(128, 128));
            selectListButton.addActionListener(e -> controller.selectShoppingList(thisList));
            selectListButton.setBackground(new Color(132, 210, 245));
            buttonPanel.add(selectListButton);
        }

        // Create button
        var addListButton = new JButton();
        addListButton.setText("Create...");
        addListButton.setPreferredSize(new Dimension(128, 128));
        addListButton.addActionListener(e -> controller.createNewShoppingList());
        addListButton.setBackground(new Color(95, 217, 116));
        buttonPanel.add(addListButton);

        main.add(buttonPanel, BorderLayout.CENTER);

        return main;
    }

}
