package com.example.pizza.service;

import com.example.pizza.entity.CategoryEntity;
import com.example.pizza.entity.ProductEntity;
import com.example.pizza.entity.UserEntity;
import com.example.pizza.exception.DefaultException;
import com.example.pizza.exception.UserAlreadyExistException;
import com.example.pizza.exception.UserNotFoundException;
import com.example.pizza.model.Product;
import com.example.pizza.model.User;
import com.example.pizza.repository.CategoryRepo;
import com.example.pizza.repository.ProductRepo;
import com.example.pizza.repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public CategoryEntity addCategory(CategoryEntity category) throws DefaultException {
        if(categoryRepo.findByName(category.getName()) != null){
            throw new DefaultException("Category already exists");
        }
        CategoryEntity newCategory = categoryRepo.save(category);
        return newCategory;
    }


    public List<CategoryEntity> getCategories(){
        Iterable<CategoryEntity> categories = categoryRepo.findAll();
        List<CategoryEntity> categoriesModels = new ArrayList<>();

        for (CategoryEntity category: categories) {
            categoriesModels.add(category);
        }
        return categoriesModels;
    }

    public CategoryEntity getOneCategory(Long id) throws DefaultException {
        CategoryEntity category = categoryRepo.findById(id).get();
        if(category == null){
            throw new DefaultException("Category not found");
        }
        return category;
    }

    public Long deleteCategory(Long id){
        categoryRepo.deleteById(id);
        return id;
    }

}
