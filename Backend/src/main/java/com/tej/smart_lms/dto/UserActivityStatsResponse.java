package com.tej.smart_lms.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserActivityStatsResponse {
    private long totalActiveDays;
    private int currentStreak;
    private int longestStreak;
    private Map<LocalDate, Integer> dailyData;
}
