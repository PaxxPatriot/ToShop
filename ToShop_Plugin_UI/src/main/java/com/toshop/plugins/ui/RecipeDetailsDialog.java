package com.toshop.plugins.ui;

import com.toshop.application.Application;
import com.toshop.domain.entities.*;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class RecipeDetailsDialog extends javax.swing.JDialog {

    private final SwingUIPlugin ui;
    private final Application application;
    private final Recipe recipe;
    private final ShoppingList currentShoppingList;

    public RecipeDetailsDialog(SwingUIPlugin ui, Application app, Recipe recipe, ShoppingList currentShoppingList) {
        super();
        this.ui = ui;
        this.application = app;
        this.recipe = recipe;
        this.currentShoppingList = currentShoppingList;
        this.setTitle(recipe.getTitle());
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setModal(true);

        buildUI();
    }

    private void buildUI() {
        JPanel outer = new JPanel();
        outer.setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(1, 2));
        content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel left = new JPanel();
        left.setLayout(new GridLayout(2, 1));

        JPanel imageAndTitle = new JPanel();
        imageAndTitle.setLayout(new BorderLayout());
        JLabel title = new JLabel(recipe.getTitle());
        title.setFont(ui.getDefaultFont().deriveFont(24f));
        imageAndTitle.add(title, BorderLayout.NORTH);

        JLabel image = null;
        try {
            var imageIcon = new ImageIcon(new URL(recipe.getImage()));
            var scaledImage = new ImageIcon(imageIcon.getImage().getScaledInstance(-1, this.getHeight() / 2, Image.SCALE_SMOOTH));
            image = new JLabel(scaledImage);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        image.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageAndTitle.add(image, BorderLayout.CENTER);

        left.add(imageAndTitle);

        JPanel description = new JPanel();
        description.setLayout(new BorderLayout());
        JLabel summary = new JLabel("<html>" + recipe.getSummary() + "</html>");
        summary.setFont(ui.getDefaultFont().deriveFont(16f));
        summary.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        description.add(summary, BorderLayout.PAGE_START);

        left.add(description);

        content.add(left);

        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());
        right.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        var ingredientsTitle = new JLabel("Ingredients:");
        ingredientsTitle.setFont(ui.getDefaultFont().deriveFont(24f));
        right.add(ingredientsTitle, BorderLayout.NORTH);

        // Add JList that displays ingredients
        JList<Ingredient> ingredients = new JList<>(recipe.getExtendedIngredients());
        ingredients.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        right.add(new JScrollPane(ingredients), BorderLayout.CENTER);

        content.add(right);

        outer.add(content, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton close = new JButton("Close");
        close.addActionListener(e -> this.dispose());
        buttons.add(close);

        JButton addIngredients = new JButton("Add Ingredients to Shopping List");
        addIngredients.addActionListener(e -> addIngredientsToShoppingList());
        buttons.add(addIngredients);

        outer.add(buttons, BorderLayout.SOUTH);

        add(outer);
    }

    private void addIngredientsToShoppingList() {
        for (Ingredient ingredient : recipe.getExtendedIngredients()) {
            application.addProductToShoppingList(currentShoppingList, ingredient.getFormattedName(), ingredient.getAmount());
        }

        application.saveShoppingList(currentShoppingList);

        // Refresh UI
        this.ui.setView(this.ui.getCurrentView());
    }
}
