package com.ecommerce.service;

import com.ecommerce.jwt.CustomUserDetails;
import com.ecommerce.model.User;
import com.ecommerce.repositories.UserRepository;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomerDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUserName(username).
                orElseThrow(()-> new UsernameNotFoundException("username Not Found"));
        return new CustomUserDetails(user);
    }
}

