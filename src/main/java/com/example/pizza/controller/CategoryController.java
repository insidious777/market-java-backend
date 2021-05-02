package com.example.pizza.controller;

import com.example.pizza.entity.CategoryEntity;
import com.example.pizza.entity.UserEntity;
import com.example.pizza.exception.UserAlreadyExistException;
import com.example.pizza.exception.UserNotFoundException;
import com.example.pizza.repository.UserRepo;
import com.example.pizza.service.CategoryService;
import com.example.pizza.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity addCategory(@RequestBody CategoryEntity category){
        System.out.println(category.getName());
        try {
            return ResponseEntity.ok(categoryService.addCategory(category));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity getCategories(){
        try {
            return ResponseEntity.ok(categoryService.getCategories());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Something wrong!");
        }
    }

    @GetMapping
    public ResponseEntity getOneCategory(@RequestParam Long id){
        try {
            return ResponseEntity.ok(categoryService.getOneCategory(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Something wrong!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id){
        try {
            return ResponseEntity.ok(categoryService.deleteCategory(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Something wrong!");
        }
    }

}
