package com.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpRequestDto {
    @NotNull
    private String username;

    @NotNull
    private String emailId;

    @NotNull
    private String password;
}
