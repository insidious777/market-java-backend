package com.example.pizza.service;

import com.example.pizza.entity.CategoryEntity;
import com.example.pizza.entity.ProductEntity;
import com.example.pizza.entity.UserEntity;
import com.example.pizza.exception.DefaultException;
import com.example.pizza.exception.UserAlreadyExistException;
import com.example.pizza.exception.UserNotFoundException;
import com.example.pizza.model.Product;
import com.example.pizza.model.User;
import com.example.pizza.repository.ProductRepo;
import com.example.pizza.repository.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;

    public User registration(UserEntity user) throws UserAlreadyExistException {
        if(userRepo.findByUsername(user.getUsername()) != null){
            throw new UserAlreadyExistException("User already exists");
        }
        UserEntity newUser =  userRepo.save(user);
        return User.toModel(newUser);
    }

    public User getOne(Long id) throws UserNotFoundException {
        UserEntity user = userRepo.findById(id).get();
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        return User.toModel(user);
    }

    public String login(Map<String, String> params) throws DefaultException {
        String password = params.get("password");
        String username = params.get("username");
        UserEntity user = userRepo.findByUsername(username);
        if(user == null){
            throw new DefaultException("User does not exists");
        }
        if(!user.getPassword().equals(password)){
            throw new DefaultException("Password mismatch");
        }
        return getJWTToken(username);
    }

    public List<User> getAll(){
        Iterable<UserEntity> users = userRepo.findAll();
        List<User> userModels = new ArrayList<>();

        for (UserEntity user: users) {
            System.out.println(user.getUsername());
            userModels.add(User.toModel(user));
        }
        return userModels;
    }

    public Long delete(Long id){
        userRepo.deleteById(id);
        return id;
    }

//    public Product addToCart(Long product_id, String header) throws ParseException {
//        String username = getUsernameFromJWT(header);
//        UserEntity user = userRepo.findByUsername(username);
//        ProductEntity product = productRepo.findById(product_id).get();
//        CartEntity cart = new CartEntity();
//        List<ProductEntity> list = new ArrayList<ProductEntity>();
//        list.add(product);
//        cart.setProducts(list);
//        user.setCart(cart);
//
//        return Product.toModel(product);
//    }

    public String getUsernameFromJWT(String header) throws ParseException {
        String token = header.split("\\s+")[1];
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        String[] tokenParts = payload.split(",");
        String[] tokenNameParts = tokenParts[1].split("\"");
        return tokenNameParts[3];
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }
}
