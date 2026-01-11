package com.ecommerce.service;

import com.ecommerce.auth.JwtResponse;
import com.ecommerce.auth.LoginRequest;
import com.ecommerce.auth.SignUpRequest;
import com.ecommerce.exception.UserAlreadyExistsException;
import com.ecommerce.jwt.JwtUtils;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService{


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public User registerUser(SignUpRequest request) {
        if (userRepository.findByUserName(request.getUsername()).isPresent()){
            throw new UserAlreadyExistsException("Username Already Exists");
        }
        User user=new User();
        user.setUserName(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(Role.USER));
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    public JwtResponse loginUser(LoginRequest request) {
        Authentication authentication= authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails= (UserDetails) authentication.getPrincipal();

        String jwtToken=jwtUtils.generateToken(userDetails);

        Set<String> roles=userDetails.getAuthorities().
                stream().map(
                        auth ->auth.getAuthority()).
                collect(Collectors.toSet());

        return new JwtResponse(
                jwtToken,
                userDetails.getUsername(),
                roles
        );
    }
}
