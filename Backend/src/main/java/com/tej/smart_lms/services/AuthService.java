package com.tej.smart_lms.services;


import com.tej.smart_lms.dto.ForgotPasswordRequest;
import com.tej.smart_lms.model.User;
import com.tej.smart_lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void login(String username, String rawPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Username not found");
        }

        if (!passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
            throw new IllegalArgumentException("Incorrect password");
        }
    }


    public String updateForgottenPassword(ForgotPasswordRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                    userRepository.save(user);
                    return "Password updated successfully.";
                })
                .orElse("Email not found.");
    }
}
