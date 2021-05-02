package com.example.pizza.repository;

import com.example.pizza.entity.CategoryEntity;
import com.example.pizza.entity.ProductEntity;
import com.example.pizza.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepo extends CrudRepository<CategoryEntity, Long> {
    CategoryEntity findByName(String name);
}
