package com.toshop.plugins.recipes.spoonacular;

public class SearchResult {
    public int offset;
    public int number;
    public int totalResults;
    public Recipe[] results;

    public static class Recipe {
        public int id;
        public String title;
        public String image;
        public String imageType;
    }
}
