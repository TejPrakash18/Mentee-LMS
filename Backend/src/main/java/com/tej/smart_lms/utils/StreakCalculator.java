package com.tej.smart_lms.utils;

import java.time.LocalDate;
import java.util.*;

public class StreakCalculator {

    public static int currentStreak(Map<LocalDate, Integer> map) {
        LocalDate today = LocalDate.now();
        int streak = 0;
        while (map.containsKey(today.minusDays(streak))) streak++;
        return streak;
    }

    public static int longestStreak(Map<LocalDate, Integer> map) {
        Set<LocalDate> sorted = new TreeSet<>(map.keySet());
        int max = 0, count = 0;
        LocalDate prev = null;
        for (LocalDate date : sorted) {
            if (prev == null || prev.plusDays(1).equals(date)) count++;
            else count = 1;
            max = Math.max(max, count);
            prev = date;
        }
        return max;
    }

    public static long totalActiveDaysThisYear(Map<LocalDate, Integer> map) {
        int year = LocalDate.now().getYear();
        return map.keySet().stream().filter(d -> d.getYear() == year).count();
    }
}
