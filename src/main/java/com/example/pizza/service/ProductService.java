package com.example.pizza.service;

import com.example.pizza.entity.ProductEntity;
import com.example.pizza.entity.UserEntity;
import com.example.pizza.model.Product;
import com.example.pizza.model.User;
import com.example.pizza.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public Product createProduct(ProductEntity product)  {
        ProductEntity newProduct = productRepo.save(product);
        return Product.toModel(newProduct);
    }

    public Product getOne(Long id)  {
        System.out.println(id);
        ProductEntity product = productRepo.findById(id).get();
        System.out.println(product);
        return Product.toModel(product);
    }

    public List<Product> getAll(){
        Iterable<ProductEntity> products = productRepo.findAll();
        List<Product> productModels = new ArrayList<>();

        for (ProductEntity product: products) {
            productModels.add(Product.toModel(product));
        }
        return productModels;
    }

    public Long delete(Long id){
        productRepo.deleteById(id);
        return id;
    }

}
