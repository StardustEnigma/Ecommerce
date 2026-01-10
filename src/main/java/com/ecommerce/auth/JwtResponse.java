package com.ecommerce.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String jwtToken;

    private String username;

    private Set<String> roles;

}
