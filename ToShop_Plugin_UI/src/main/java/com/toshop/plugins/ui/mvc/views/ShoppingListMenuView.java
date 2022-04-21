package com.toshop.plugins.ui.mvc.views;

import com.toshop.domain.entities.ShoppingList;
import com.toshop.plugins.ui.mvc.base.View;
import com.toshop.plugins.ui.mvc.controllers.ShoppingListMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ShoppingListMenuView extends View<ShoppingListMenuController> {

    private JList<Object> shoppingListItemList;

    public ShoppingListMenuView(ShoppingList shoppingList) {
        super();
        this.controller.setShoppingList(shoppingList);
    }

    @Override
    public JPanel buildUI() {
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        var topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        var titleLabel = new JLabel(controller.currentShoppingList.getName());
        titleLabel.setFont(ui.getDefaultFont().deriveFont(24f));
        topPanel.add(titleLabel);
        main.add(topPanel, BorderLayout.NORTH);

        var bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        var backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.goBack());
        bottomPanel.add(backButton);
        main.add(bottomPanel, BorderLayout.SOUTH);

        // Split main panel into two parts
        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, main, new JPanel());
        splitPane.setDividerLocation(0.75);
        splitPane.setResizeWeight(0.75);

        // List of all items in the ShoppingList using a JList
        var listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        shoppingListItemList = new JList<>(controller.currentShoppingList.getItems().toArray());
        shoppingListItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        shoppingListItemList.setLayoutOrientation(JList.VERTICAL);
        shoppingListItemList.setVisibleRowCount(-1);
        shoppingListItemList.setFont(ui.getDefaultFont().deriveFont(16f));
        listPanel.add(shoppingListItemList, BorderLayout.CENTER);

        // Add list to left side of split pane
        splitPane.setLeftComponent(listPanel);

        // Panel that contains a input field for entering new products at the top and below it a JList with suggested products
        var inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        var inputField = new JTextField();
        inputField.setFont(ui.getDefaultFont().deriveFont(16f));
        // Add placeholder text to input field
        inputField.setText("Enter product name...");
        // Make placeholder text light gray
        inputField.setForeground(Color.LIGHT_GRAY);
        inputField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputField.getText().equals("Enter product name...")) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                inputField.setText("Enter product name...");
                inputField.setForeground(Color.LIGHT_GRAY);
            }
        });
        inputPanel.add(inputField, BorderLayout.NORTH);
        var suggestedList = new JList<>(controller.getSuggestedProducts().toArray());
        suggestedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suggestedList.setLayoutOrientation(JList.VERTICAL);
        suggestedList.setVisibleRowCount(-1);
        suggestedList.setFont(ui.getDefaultFont().deriveFont(16f));
        // Add double click action to list that adds the selected element to the shopping list
        suggestedList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    controller.addItem(suggestedList.getSelectedValue().toString());
                }
            }
        });
        inputPanel.add(suggestedList, BorderLayout.CENTER);
        // Add button that adds the input field to the shopping list
        var addButton = new JButton("Add");
        addButton.addActionListener(e -> controller.addItem(inputField.getText()));
        inputPanel.add(addButton, BorderLayout.SOUTH);
        splitPane.setRightComponent(inputPanel);

        // Add splitpane to main panel
        main.add(splitPane, BorderLayout.CENTER);

        return main;
    }

    public void update() {
        shoppingListItemList.setListData(controller.currentShoppingList.getItems().toArray());
    }

}
