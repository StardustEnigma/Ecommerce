package com.ecommerce.service;

import com.ecommerce.model.User;
import com.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user=userRepository.findById(userId).
                orElseThrow(()->new RuntimeException("User Not Found"));

        userRepository.delete(user);
    }

    @Override
    public User updateUser(Long userId, User user) {
        User savedUser=userRepository.findById(userId).
                orElseThrow(()->new RuntimeException("User not found"));

        savedUser.setUserName(user.getUserName());
        savedUser.setEmail(user.getEmail());
        return userRepository.save(savedUser);
    }
}
