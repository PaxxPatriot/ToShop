package com.toshop.domain.entities;

public class Ingredient {
    private int amount;
    private String name;

    public Ingredient() {

    }

    public Ingredient(int amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    @Override
    public String toString() {
        return getAmount() + "x " + getFormattedName();
    }

    public String getFormattedName() {
        if (name == null) {
            return null;
        }

        // Upper case first letter of each word
        String[] words = name.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    public int getAmount() {
        if (amount > 0) {
            return amount;
        }
        return 1;
    }

    public String getName() {
        return name;
    }

}
