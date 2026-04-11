package com.example.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name are required")
    private String fullName;

    @NotBlank(message = "Email are required")
    @Email(message = "Invalid email format")
    private String email;

    @JsonIgnore
    @NotBlank(message = "Password are required")
    private String password;

    @JsonIgnore
    @Past(message = "Birthday must be in the past")
    private LocalDate birthday;

    @JsonIgnore
    @CreationTimestamp
    private LocalDate created_at;

    @JsonIgnore
    @UpdateTimestamp
    private LocalDate updated_at;
}
