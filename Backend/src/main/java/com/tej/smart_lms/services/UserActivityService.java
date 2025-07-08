    package com.tej.smart_lms.services;


    import com.tej.smart_lms.dto.UserActivityStatsDTO;
    import com.tej.smart_lms.dto.UserActivityStatsResponse;
    import com.tej.smart_lms.model.UserActivity;
    import com.tej.smart_lms.repository.UserActivityRepository;
    import com.tej.smart_lms.utils.StreakCalculator;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.util.*;
    import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class UserActivityService {

        private final UserActivityRepository repository;

        public UserActivityStatsDTO getUserStats(String username) {
            List<UserActivity> logs = repository.findAllByUsername(username);

            // ✅ Filter out null dates
            logs = logs.stream()
                    .filter(log -> log.getDate() != null)
                    .collect(Collectors.toList());

            Map<String, Integer> dailyData = logs.stream()
                    .collect(Collectors.groupingBy(
                            log -> log.getDate().toString(),
                            Collectors.summingInt(UserActivity::getCount)
                    ));

            Set<LocalDate> uniqueDays = logs.stream()
                    .map(UserActivity::getDate)
                    .collect(Collectors.toSet());

            int currentStreak = calculateCurrentStreak(uniqueDays);
            int longestStreak = calculateLongestStreak(uniqueDays);

            return new UserActivityStatsDTO(dailyData, currentStreak, longestStreak);
        }




        public void logActivity(String username, LocalDate date) {
            Optional<UserActivity> existing = repository.findByUsernameAndDate(username, date);

            if (existing.isPresent()) {
                UserActivity activity = existing.get();
                activity.setCount(activity.getCount() + 1);  // ✅ Increment count
                repository.save(activity);
            } else {
                UserActivity activity = new UserActivity();
                activity.setUsername(username);
                activity.setDate(date);
                activity.setCount(1); // ✅ Important!
                repository.save(activity);
            }
        }





        private int calculateCurrentStreak(Set<LocalDate> days) {
            int streak = 0;
            LocalDate today = LocalDate.now();
            while (days.contains(today.minusDays(streak))) streak++;
            return streak;
        }

        private int calculateLongestStreak(Set<LocalDate> days) {
            List<LocalDate> sorted = new ArrayList<>(days);
            Collections.sort(sorted);
            int longest = 0, current = 1;
            for (int i = 1; i < sorted.size(); i++) {
                if (sorted.get(i).minusDays(1).equals(sorted.get(i - 1))) {
                    current++;
                } else {
                    longest = Math.max(longest, current);
                    current = 1;
                }
            }
            return Math.max(longest, current);
        }
    }
