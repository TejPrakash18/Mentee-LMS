package com.tej.smart_lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class UserActivityStatsDTO {
    private Map<String, Integer> dailyData;
    private int currentStreak;
    private int longestStreak;
}
