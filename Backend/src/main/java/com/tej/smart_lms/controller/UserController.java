package com.tej.smart_lms.controller;
import com.tej.smart_lms.dto.UpdateProfileRequest;
import com.tej.smart_lms.model.User;
import com.tej.smart_lms.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
@Tag(name = "User API", description = "Operations for users")
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestParam(name = "username", required = false) String usernameParam) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized"));
        }

        String loggedInUsername = authentication.getName();

        if (usernameParam != null && !usernameParam.equals(loggedInUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Access denied to another user's profile"));
        }

        String targetUsername = (usernameParam != null && !usernameParam.isBlank()) ? usernameParam : loggedInUsername;

        User user = userService.assignAvatarIfAbsent(targetUsername);

        user.setPassword(null); // never expose password
        return ResponseEntity.ok(user);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Unauthorized");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        String username = authentication.getName();

        Optional<User> updated = userService.updateProfile(
                username,
                updateProfileRequest.getUserName(),
                updateProfileRequest.getEmail(),
                updateProfileRequest.getLocation(),
                updateProfileRequest.getCollege(),
                updateProfileRequest.getEducation(),
                updateProfileRequest.getGithub(),
                updateProfileRequest.getLinkedin(),
                updateProfileRequest.getSkills()
        );

        if (updated.isPresent()) {
            User user = updated.get();
            user.setPassword(null); // never return password
            return ResponseEntity.ok(user);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/avatar")
    public ResponseEntity<Map<String, String>> getAvatar(@RequestParam String username) {
        String avatarUrl = userService.getAvatarUrl(username);
        return ResponseEntity.ok(Map.of("avatarUrl", avatarUrl));
    }


}