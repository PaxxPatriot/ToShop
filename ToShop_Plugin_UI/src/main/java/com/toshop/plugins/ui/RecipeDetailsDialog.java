package com.toshop.plugins.ui;

import com.toshop.application.Application;
import com.toshop.domain.entities.Recipe;

import javax.swing.*;

public class RecipeDetailsDialog extends javax.swing.JDialog {

    private final SwingUIPlugin ui;
    private final Application application;
    private final Recipe recipe;

    public RecipeDetailsDialog(SwingUIPlugin ui, Application app, Recipe recipe) {
        super();
        this.ui = ui;
        this.application = app;
        this.recipe = recipe;
        this.setTitle(recipe.getTitle());
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setModal(true);

        buildUI();
    }

    private void buildUI() {
        JPanel content = new JPanel();

        add(content);
    }
}
