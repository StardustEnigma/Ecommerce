package com.ecommerce.controller;

import com.ecommerce.model.User;
import com.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Tag(name = "User Management", description = "APIs for user management operations")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/admin/users")
    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users (Admin only)")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/api/public/users")
    @Operation(summary = "Create a new user", description = "Register a new user in the system")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user){
        userService.createUser(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/api/public/users/{userId}")
    @Operation(summary = "Update user details", description = "Update an existing user's information")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @Valid @RequestBody User user){
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/api/public/users/{userId}")
    @Operation(summary = "Delete a user", description = "Remove a user from the system")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}
