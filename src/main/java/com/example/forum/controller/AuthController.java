package com.example.forum.controller;

import com.example.forum.dto.AuthResponse;
import com.example.forum.dto.LoginRequest;
import com.example.forum.dto.RegisterRequest;
import com.example.forum.model.User;
import com.example.forum.service.JwtService;
import com.example.forum.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) throws ExecutionException, InterruptedException {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setBirthday(request.getBirthday());

        User savedUser = userService.register(user);
        String token = jwtService.generateToken(savedUser.getEmail());

        return ResponseEntity.ok(
                new AuthResponse(
                        token,
                        savedUser.getFullName(),
                        savedUser.getEmail(),
                        savedUser.getBirthday()
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) throws ExecutionException, InterruptedException {
        User user = userService.login(request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(
                new AuthResponse(
                        token,
                        user.getFullName(),
                        user.getEmail(),
                        user.getBirthday()
                )
        );
    }
}
