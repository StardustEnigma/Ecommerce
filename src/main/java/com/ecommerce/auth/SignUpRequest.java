package com.ecommerce.auth;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String emailId;
    private String password;
}
