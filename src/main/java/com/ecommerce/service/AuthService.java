package com.ecommerce.service;

import com.ecommerce.auth.JwtResponse;
import com.ecommerce.auth.LoginRequest;
import com.ecommerce.auth.SignUpRequest;
import com.ecommerce.model.User;

import java.util.List;
import java.util.Map;

public interface AuthService {
    User registerUser(SignUpRequest request);

    JwtResponse loginUser(LoginRequest request);
}
