package com.toshop.domain.entities;

public class Recipe {
    private String id;
    private String title;
    private String image;

    public Recipe(String id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
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
}
