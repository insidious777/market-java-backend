package com.example.pizza.service;

import com.example.pizza.entity.UserEntity;
import com.example.pizza.exception.DefaultException;
import com.example.pizza.exception.UserAlreadyExistException;
import com.example.pizza.exception.UserNotFoundException;
import com.example.pizza.model.User;
import com.example.pizza.repository.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

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
                .setExpiration(new Date(System.currentTimeMillis() + 6000000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }
}
