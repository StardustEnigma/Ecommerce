package com.ecommerce.controller;

import com.ecommerce.auth.LoginRequest;
import com.ecommerce.auth.SignUpRequest;

import com.ecommerce.jwt.CustomUserDetails;
import com.ecommerce.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth/users")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody SignUpRequest userDetails){
        authService.registerUser(userDetails);
        return ResponseEntity.ok("User created Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest request){
       return ResponseEntity.ok(authService.loginUser(request));
    }
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails= (CustomUserDetails) authentication.getPrincipal();
        Map<String,Object> profile=new HashMap<>();
        profile.put("username",userDetails.getUsername() );
        profile.put("email",userDetails.getEmail());
        profile.put("roles",userDetails.getAuthorities().stream()
                .map(item-> item.getAuthority()).
                collect(Collectors.toSet()));
        profile.put("message","This message is from Backend");
        return ResponseEntity.ok(profile);
    }

}
