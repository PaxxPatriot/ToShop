package com.toshop.plugins.ui.mvc.views;

import com.toshop.domain.entities.Product;
import com.toshop.domain.entities.ShoppingList;
import com.toshop.domain.entities.ShoppingListItem;
import com.toshop.plugins.ui.mvc.base.View;
import com.toshop.plugins.ui.mvc.controllers.ShoppingListMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ShoppingListMenuView extends View<ShoppingListMenuController> {

    private JList<ShoppingListItem> shoppingListItemList;
    private JList<Product> suggestedList;

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
        splitPane.setDividerLocation(0.85);
        splitPane.setResizeWeight(1);

        // Shopping List on the left
        JPanel shoppingListPanel = buildShoppingListPanel();
        splitPane.setLeftComponent(shoppingListPanel);

        // Add Product on the right
        JPanel addProductPanel = buildAddProductPanel();
        splitPane.setRightComponent(addProductPanel);

        main.add(splitPane, BorderLayout.CENTER);

        return main;
    }

    private JPanel buildAddProductPanel() {
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
        suggestedList = new JList<>(controller.getSuggestedProducts().toArray(new Product[0]));
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
        return inputPanel;
    }

    private JPanel buildShoppingListPanel() {
        var listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        shoppingListItemList = new JList<>(controller.currentShoppingList.getItems().toArray(new ShoppingListItem[0]));
        shoppingListItemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        shoppingListItemList.setLayoutOrientation(JList.VERTICAL);
        shoppingListItemList.setVisibleRowCount(-1);
        shoppingListItemList.setFont(ui.getDefaultFont().deriveFont(16f));
        // Add right click action that opens popup menu that allows user to remove items
        shoppingListItemList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getButton() == 3 && shoppingListItemList.getSelectedIndex() != -1) {
                    var popup = new JPopupMenu();
                    var removeItem = new JMenuItem("Remove Item");
                    removeItem.addActionListener(e -> {
                        var selectedItem = shoppingListItemList.getSelectedValue();
                        controller.removeShoppingListItem((ShoppingListItem) selectedItem);
                    });
                    popup.add(removeItem);
                    // Add menu items to move items up and down
                    var moveUp = new JMenuItem("Move Up");
                    moveUp.addActionListener(e -> {
                        var selectedItem = shoppingListItemList.getSelectedValue();
                        controller.moveShoppingListItemUp((ShoppingListItem) selectedItem);
                    });
                    popup.add(moveUp);
                    var moveDown = new JMenuItem("Move Down");
                    moveDown.addActionListener(e -> {
                        var selectedItem = shoppingListItemList.getSelectedValue();
                        controller.moveShoppingListItemDown((ShoppingListItem) selectedItem);
                    });
                    popup.add(moveDown);
                    popup.show(shoppingListItemList, evt.getX(), evt.getY());
                }
            }
        });
        // Allow reordering of items by dragging them
        shoppingListItemList.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport info) {
                return info.isDataFlavorSupported(DataFlavor.stringFlavor);
            }

            @Override
            public boolean importData(TransferSupport info) {
                if (!info.isDrop()) {
                    return false;
                }
                var index = shoppingListItemList.getSelectedIndex();
                var item = (ShoppingListItem) shoppingListItemList.getSelectedValue();
                var dropLocation = shoppingListItemList.locationToIndex(info.getDropLocation().getDropPoint());
                //if (dropLocation > index) {
                //    dropLocation++;
                //}
                controller.moveShoppingListItem(item, dropLocation);
                return true;
            }

            public int getSourceActions(JComponent c) {
                return TransferHandler.COPY_OR_MOVE;
            }

            @Override
            protected Transferable createTransferable(JComponent c) {
                return new StringSelection(shoppingListItemList.getSelectedValue().toString());
            }

            @Override
            public void exportDone(JComponent c, Transferable t, int action) {
                if (action == TransferHandler.MOVE) {
                    var item = (ShoppingListItem) shoppingListItemList.getSelectedValue();
                    controller.removeShoppingListItem(item);
                }
            }
        });
        shoppingListItemList.setDropMode(DropMode.ON);
        shoppingListItemList.setDragEnabled(true);

        listPanel.add(shoppingListItemList, BorderLayout.CENTER);
        return listPanel;
    }

    public void update() {
        shoppingListItemList.setListData(controller.currentShoppingList.getItems().toArray(new ShoppingListItem[0]));
        suggestedList.setListData(controller.getSuggestedProducts().toArray(new Product[0]));
    }

}
