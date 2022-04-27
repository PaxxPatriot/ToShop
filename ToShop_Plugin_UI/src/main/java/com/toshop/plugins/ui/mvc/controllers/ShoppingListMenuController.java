package com.toshop.plugins.ui.mvc.controllers;

import com.toshop.domain.entities.Product;
import com.toshop.domain.entities.ShoppingList;
import com.toshop.domain.entities.ShoppingListItem;
import com.toshop.plugins.ui.RecipeBrowserDialog;
import com.toshop.plugins.ui.mvc.base.Controller;
import com.toshop.plugins.ui.mvc.views.MainMenuView;
import com.toshop.plugins.ui.mvc.views.ShoppingListMenuView;

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
        currentShoppingList = application.saveShoppingList(currentShoppingList);
        view.update();
    }

    public void removeShoppingListItem(ShoppingListItem item) {
        application.removeShoppingListItem(currentShoppingList, item);
        currentShoppingList = application.saveShoppingList(currentShoppingList);
        view.update();
    }

    public void moveShoppingListItemUp(ShoppingListItem selectedItem) {
        int currentIndex = currentShoppingList.getItems().indexOf(selectedItem);
        if (currentIndex > 0) {
            ShoppingListItem item = currentShoppingList.getItems().get(currentIndex - 1);
            currentShoppingList.getItems().set(currentIndex - 1, selectedItem);
            currentShoppingList.getItems().set(currentIndex, item);
            currentShoppingList = application.saveShoppingList(currentShoppingList);
            view.update();
        }
    }

    public void moveShoppingListItemDown(ShoppingListItem selectedItem) {
        int currentIndex = currentShoppingList.getItems().indexOf(selectedItem);
        if (currentIndex < currentShoppingList.getItems().size() - 1) {
            ShoppingListItem item = currentShoppingList.getItems().get(currentIndex + 1);
            currentShoppingList.getItems().set(currentIndex + 1, selectedItem);
            currentShoppingList.getItems().set(currentIndex, item);
            currentShoppingList = application.saveShoppingList(currentShoppingList);
            view.update();
        }
    }

    public void moveShoppingListItem(ShoppingListItem item, int dropLocation) {
        int currentIndex = currentShoppingList.getItems().indexOf(item);
        if (currentIndex != dropLocation) {
            currentShoppingList.getItems().remove(item);
            currentShoppingList.getItems().add(dropLocation, item);
            currentShoppingList = application.saveShoppingList(currentShoppingList);
            view.update();
        }
    }

    public void browseRecipes() {
        RecipeBrowserDialog browser = new RecipeBrowserDialog(ui, application);
        browser.setVisible(true);
    }
}
