package com.ecommerce.controller;

import com.ecommerce.model.User;
import com.ecommerce.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/admin/users")

    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/api/users/{userId}")

    public ResponseEntity<User> updateUser(@PathVariable Long userId, @Valid @RequestBody User user){
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/api/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }


}
