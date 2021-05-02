package com.example.pizza.service;

import com.example.pizza.entity.CategoryEntity;
import com.example.pizza.entity.ProductEntity;
import com.example.pizza.entity.UserEntity;
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
public class ProductService {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    public Product createProduct(ProductEntity product,  String header, Long category_id) throws ParseException {
        String username = getUsernameFromJWT(header);
        UserEntity user = userRepo.findByUsername(username);

        product.setUser(user);
        System.out.println("current user:"+ user.getId());
        CategoryEntity category = categoryRepo.findById(category_id).get();
        product.setCategory(category);
        productRepo.save(product);
        return Product.toModel(product);
    }

    public Product getOne(Long id)  {
        ProductEntity product = productRepo.findById(id).get();
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

    public String getUsernameFromJWT(String header) throws ParseException {
        String token = header.split("\\s+")[1];
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        String[] tokenParts = payload.split(",");
        String[] tokenNameParts = tokenParts[1].split("\"");
        return tokenNameParts[3];
    }
}
