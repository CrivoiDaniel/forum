package com.example.forum.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Name are required")
    private String fullName;
    @NotBlank(message = "Email are required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password are required")
    private String password;

    @Past(message = "Birthday must be in the past")
    private LocalDate birthday;
}
