package com.ecommerce.service;

import com.ecommerce.exception.UserNotFoundException;
import com.ecommerce.model.User;
import com.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user=userRepository.findById(userId).
                orElseThrow(()->new UserNotFoundException("User Not Found with id "+userId));

        userRepository.delete(user);
    }

    @Override
    public User updateUser(Long userId, User user) {
        User savedUser=userRepository.findById(userId).
                orElseThrow(()->new UserNotFoundException("User not Found with id "+userId));

        savedUser.setUserName(user.getUserName());
        savedUser.setEmail(user.getEmail());
        return userRepository.save(savedUser);
    }
}
