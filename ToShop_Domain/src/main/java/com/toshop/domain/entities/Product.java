package com.toshop.domain.entities;

public class Product {
    private String name;

    public Product() {

    }

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
