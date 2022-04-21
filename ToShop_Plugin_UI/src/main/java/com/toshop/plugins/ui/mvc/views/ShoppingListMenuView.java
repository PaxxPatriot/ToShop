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
        var topInputPanel = new JPanel();
        topInputPanel.setLayout(new BorderLayout());
        var productNameInputField = new JTextField();
        productNameInputField.setFont(ui.getDefaultFont().deriveFont(16f));
        productNameInputField.setText("Enter product name...");
        productNameInputField.setForeground(Color.LIGHT_GRAY);
        productNameInputField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (productNameInputField.getText().equals("Enter product name...")) {
                    productNameInputField.setText("");
                    productNameInputField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (productNameInputField.getText().equals("")) {
                    productNameInputField.setText("Enter product name...");
                    productNameInputField.setForeground(Color.LIGHT_GRAY);
                }
            }
        });
        topInputPanel.add(productNameInputField, BorderLayout.CENTER);
        // Add JSpinner next to the input field
        var productAmountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
        // Set spinner width to fit 2 digits
        productAmountSpinner.setPreferredSize(new Dimension(50, 20));
        productAmountSpinner.setFont(ui.getDefaultFont().deriveFont(16f));
        topInputPanel.add(productAmountSpinner, BorderLayout.EAST);
        inputPanel.add(topInputPanel, BorderLayout.NORTH);
        var suggestedList = new JList<>(controller.getSuggestedProducts().toArray());
        suggestedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suggestedList.setLayoutOrientation(JList.VERTICAL);
        suggestedList.setVisibleRowCount(-1);
        suggestedList.setFont(ui.getDefaultFont().deriveFont(16f));
        // Add double click action to list that adds the selected element to the shopping list
        suggestedList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() >= 2) {
                    controller.addItem(suggestedList.getSelectedValue().toString(), (int) productAmountSpinner.getValue());
                    evt.consume();
                }
            }
        });
        inputPanel.add(suggestedList, BorderLayout.CENTER);
        // Add button that adds the input field to the shopping list
        var addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            controller.addItem(productNameInputField.getText(), (int) productAmountSpinner.getValue());
            productNameInputField.setText("");
        });
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
