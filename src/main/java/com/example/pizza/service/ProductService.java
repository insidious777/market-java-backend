package com.example.pizza.service;

import com.example.pizza.entity.ProductEntity;
import com.example.pizza.entity.UserEntity;
import com.example.pizza.model.Product;
import com.example.pizza.model.User;
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

    public Product createProduct(ProductEntity product,  String header) throws ParseException {
        decodeJWT(header);
        //UserEntity user = userRepo.findById(userId).get();
        long id =0;
        UserEntity user = userRepo.findById(id).get();
        product.setUser(user);
        productRepo.save(product);
        //ProductEntity newProduct = productRepo.save(product);
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

    public String decodeJWT(String header) throws ParseException {
        String token = header.split("\\s+")[1];
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        System.out.println(payload);
        Object obj = new JSONParser().parse(payload);
        System.out.println(obj);
//        JSONObject jo = (JSONObject) obj;
//        System.out.println(jo);
//        String username = (String) jo.get("sub");
//        System.out.println(username);
        return "gfsd";
    }
}
