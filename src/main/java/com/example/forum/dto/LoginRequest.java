package com.example.forum.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Email are required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password are required")
    private String password;
}
