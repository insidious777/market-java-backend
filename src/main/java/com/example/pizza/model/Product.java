package com.example.pizza.model;

import com.example.pizza.entity.ProductEntity;
import com.example.pizza.entity.UserEntity;

public class Product {
    private Long id;
    private String title;
    private String description;
    private Long ownerId;

    public static Product toModel(ProductEntity entity) {
        Product model = new Product();
        model.setId(entity.getId());
        model.setDescription(entity.getDescription());
        model.setTitle(entity.getTitle());
        model.setOwnerId(entity.getId());
        return model;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}