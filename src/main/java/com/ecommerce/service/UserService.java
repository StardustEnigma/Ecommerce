package com.ecommerce.service;

import com.ecommerce.model.User;

import java.util.List;


public interface UserService {
    List<User> getAllUsers();

    void createUser(User user);

    void deleteUser(Long userId);

    User updateUser(Long userId,User user);
}
