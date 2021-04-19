package com.example.pizza.model;

import com.example.pizza.entity.ProductEntity;
import com.example.pizza.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private Long id;
    private String username;
    private List<Product> products;

    public static User toModel(UserEntity entity){
        User model = new User();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());


        if(entity.getProducts()!= null) {
            List<ProductEntity> list = entity.getProducts();
            List<Product> models = new ArrayList<>();
            for (ProductEntity productEntity : list) {
                models.add(Product.toModel(productEntity));
            }
            model.setProducts(models);
        }
        return model;
    }

    public User() {
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
