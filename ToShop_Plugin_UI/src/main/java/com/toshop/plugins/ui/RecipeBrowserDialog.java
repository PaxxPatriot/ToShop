package com.toshop.plugins.ui;

import com.toshop.application.Application;
import com.toshop.domain.entities.Recipe;
import com.toshop.domain.entities.ShoppingList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class RecipeBrowserDialog extends JDialog {


    private final SwingUIPlugin ui;
    private final Application application;
    private final ShoppingList currentShoppingList;
    private JPanel searchResultPanel;
    private JTextField searchField;
    private JLabel resultLabel;

    public RecipeBrowserDialog(SwingUIPlugin ui, Application app, ShoppingList currentShoppingList) {
        super();
        this.ui = ui;
        this.application = app;
        this.currentShoppingList = currentShoppingList;
        this.setTitle("Recipe Browser");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setModal(true);

        buildUI();
    }

    private void buildUI() {
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        JPanel search = new JPanel();
        search.setLayout(new GridBagLayout());

        // Add search field
        searchField = new JTextField();
        var searchFieldConstraints = new GridBagConstraints();
        searchFieldConstraints.gridx = 0;
        searchFieldConstraints.gridy = 0;
        searchFieldConstraints.weightx = 1;
        searchFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        searchFieldConstraints.insets = new Insets(5, 5, 5, 5);

        // Add placeholder text
        searchField.setText("Enter search keywords...");
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Enter search keywords...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Enter search keywords...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        search.add(searchField, searchFieldConstraints);

        // Add search button
        JButton searchButton = new JButton("Search");
        var searchButtonConstraints = new GridBagConstraints();
        searchButtonConstraints.gridx = 1;
        searchButtonConstraints.gridy = 0;
        searchButtonConstraints.weightx = 0;
        searchButtonConstraints.fill = GridBagConstraints.NONE;
        searchButtonConstraints.insets = new Insets(5, 5, 5, 5);
        search.add(searchButton, searchButtonConstraints);

        // Perform search when search button is clicked
        searchButton.addActionListener(e -> {
            search();
        });


        // Make search field have the same height as the search button
        searchField.setPreferredSize(searchButton.getPreferredSize());

        // Press search button when enter is pressed
        searchField.addActionListener(e -> searchButton.doClick());

        content.add(search, BorderLayout.NORTH);

        var centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        resultLabel = new JLabel("No results");
        resultLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.add(resultLabel, BorderLayout.NORTH);

        searchResultPanel = new JPanel();
        searchResultPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        centerPanel.add(searchResultPanel, BorderLayout.CENTER);

        content.add(centerPanel, BorderLayout.CENTER);

        add(content);
    }

    private void search() {
        var query = searchField.getText();
        var searchResultFuture = application.searchRecipes(query);
        // Display results when future is done
        searchResultFuture.thenAccept(recipes -> {
            searchResultPanel.removeAll();
            resultLabel.setText(String.format("Found %d results", recipes.size()));
            for (var recipe : recipes) {
                searchResultPanel.add(getResultPanel(recipe));
                System.out.println(recipe.getTitle());
            }
            searchResultPanel.revalidate();
            searchResultPanel.repaint();
        });
    }

    private JPanel getResultPanel(Recipe recipe) {
        JPanel result = new JPanel();
        result.setPreferredSize(new Dimension(200, 200));
        result.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        result.setLayout(new BoxLayout(result, BoxLayout.Y_AXIS));
        // Set background color to white
        result.setBackground(Color.WHITE);

        JLabel title = new JLabel(recipe.getTitle());
        title.setFont(new Font("Arial", Font.BOLD, 20));
        result.add(title);

        // Download image for recipe and display it
        var image = new JLabel();
        var imageUrl = recipe.getImage();
        if (imageUrl != null) {
            try {
                var imageIcon = new ImageIcon(new URL(imageUrl));
                var scaledImageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(200, -1, Image.SCALE_SMOOTH));
                image.setIcon(scaledImageIcon);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        result.add(image);

        // Highlight result on mouse hover
        result.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                result.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                result.setBackground(Color.WHITE);
            }
        });

        // Open recipe when clicked
        result.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openRecipe(recipe);
            }
        });

        result.setToolTipText("Click to open recipe");

        return result;
    }

    private void openRecipe(Recipe recipe) {
        var detailedRecipeFuture = application.getDetailedRecipe(recipe.getId());
        // Set cursor to wait cursor
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        detailedRecipeFuture.thenAccept(detailedRecipe -> {
            // Set cursor to default
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            var recipeDetailsDialog = new RecipeDetailsDialog(ui, application, detailedRecipe, currentShoppingList);
            recipeDetailsDialog.setVisible(true);
        });
    }
}
