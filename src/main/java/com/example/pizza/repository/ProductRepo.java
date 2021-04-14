package com.example.pizza.repository;

import com.example.pizza.entity.ProductEntity;
import com.example.pizza.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<ProductEntity, Long> {
    UserEntity findByTitle(String username);
}
