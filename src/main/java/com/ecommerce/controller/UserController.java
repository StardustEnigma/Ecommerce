package com.ecommerce.controller;

import com.ecommerce.model.User;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/admin/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users=userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/api/public/users")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.createUser(user);
        return new ResponseEntity<>("User created Successfully",HttpStatus.CREATED);
    }

    @PutMapping("/api/public/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId,@RequestBody User user){
        User updateUser=userService.updateUser(userId,user);
        return ResponseEntity.ok(updateUser);
    }
    @DeleteMapping("/api/public/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
       userService.deleteUser(userId);
       return ResponseEntity.ok("User Deleted Successfully");
    }

}
