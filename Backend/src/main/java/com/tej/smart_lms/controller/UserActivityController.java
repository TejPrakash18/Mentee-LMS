package com.tej.smart_lms.controller;

import com.tej.smart_lms.dto.ActivityLogRequest;
import com.tej.smart_lms.dto.UserActivityStatsDTO;
import com.tej.smart_lms.dto.UserActivityStatsResponse;
import com.tej.smart_lms.services.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class UserActivityController {

    private final UserActivityService userActivityService;

    @GetMapping("/{username}/stats")
    public ResponseEntity<UserActivityStatsDTO> getUserStats(@PathVariable String username) {
        return ResponseEntity.ok(userActivityService.getUserStats(username));
    }

    @PostMapping("/log")
    public ResponseEntity<Void> logActivity(@RequestBody ActivityLogRequest request) {
        userActivityService.logActivity(request.getUsername(), LocalDate.now());
        return ResponseEntity.ok().build();
    }
}
