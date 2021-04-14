package com.example.pizza.service;

import com.example.pizza.entity.UserEntity;
import com.example.pizza.exception.UserAlreadyExistException;
import com.example.pizza.exception.UserNotFoundException;
import com.example.pizza.model.User;
import com.example.pizza.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
}
