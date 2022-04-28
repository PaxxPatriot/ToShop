package com.toshop.domain.entities;

public class Recipe {
    private String id;
    private String title;
    private String image;
    private int servings;
    private int readyInMinutes;
    private Ingredient[] extendedIngredients;
    private String summary;

    public Recipe(String id, String title, String image, int servings, int readyInMinutes, Ingredient[] extendedIngredients, String summary) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.servings = servings;
        this.readyInMinutes = readyInMinutes;
        this.extendedIngredients = extendedIngredients;
        this.summary = summary;
    }

    public Recipe() {

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getServings() {
        return servings;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public Ingredient[] getExtendedIngredients() {
        return extendedIngredients;
    }

    public String getSummary() {
        return summary;
    }

}
