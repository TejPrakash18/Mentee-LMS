package com.tej.smart_lms.controller;


import com.tej.smart_lms.dto.ForgotPasswordRequest;
import com.tej.smart_lms.dto.LoginRequest;
import com.tej.smart_lms.model.User;
import com.tej.smart_lms.services.AuthService;
import com.tej.smart_lms.services.UserService;
import com.tej.smart_lms.utils.JwtUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired private AuthService authService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User saved = authService.register(user);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            // Return JSON instead of plain text
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", e.getMessage()));  // <- this is key
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authService.login(request.getUsername(), request.getPassword());
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("message", e.getMessage())); // returns { "message": "Incorrect password" }
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String message = authService.updateForgottenPassword(request);
        if (message.equals("Password updated successfully.")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }



    @PostMapping("/logout")

    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }



}
