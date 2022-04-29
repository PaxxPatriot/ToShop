package com.toshop.domain.entities;

import java.util.Date;

public class Product {
    private String name;
    private Date firstAddedDate;
    private Date lastAddedDate;

    public Product() {

    }

    public static Product create(String name){
        Product product = new Product();
        product.name = name;
        product.firstAddedDate = new Date();
        product.lastAddedDate = new Date();
        return product;
    }

    public String getName() {
        return name;
    }

    public Date getFirstAddedDate() {
        return firstAddedDate;
    }

    public Date getLastAddedDate() {
        return lastAddedDate;
    }

    @Override
    public String toString() {
        return name;
    }

    public void updateLastUsed() {
        this.lastAddedDate = new Date();
    }
}
