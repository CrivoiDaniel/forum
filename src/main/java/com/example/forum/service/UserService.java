package com.example.forum.service;

import com.example.forum.model.User;
import com.example.forum.repository.UserRepository;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) throws ExecutionException, InterruptedException {
        Optional<User> existing = userRepository.findByEmail(user.getEmail());

        if(existing.isPresent()){
            throw new  RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return  userRepository.save(user);
    }

    public User login(String email, String password) throws  ExecutionException, InterruptedException {
        Optional<User> userExist = userRepository.findByEmail(email);

        if (userExist.isEmpty()){
            throw new RuntimeException("Invalid email or password");
        }
        User user = userExist.get();
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid email or password");
        }
        return user;
    }

    public User findByEmail(String email){

        return  userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));
    }
}
