package com.example.pizza.controller;

import com.example.pizza.entity.ProductEntity;
import com.example.pizza.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity createProduct(@RequestBody ProductEntity product, @RequestParam Long userId){
        try {
            return ResponseEntity.ok(productService.createProduct(product, userId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Something wrong");
        }
    }

    @GetMapping
    public ResponseEntity getProduct(@RequestParam Long id){
        try {
            return ResponseEntity.ok(productService.getOne(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Something wrong");
        }
    }

    @GetMapping("/")
    public ResponseEntity getAll(){
        try {
            return ResponseEntity.ok(productService.getAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Something wrong");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(productService.delete(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Something wrong!");
        }
    }

}
