package com.tej.smart_lms.repository;

import com.tej.smart_lms.model.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {

    // ✅ All activity for a user (already exists)
    List<UserActivity> findAllByUsername(String username);

    // ✅ Activity for a specific day
    Optional<UserActivity> findByUsernameAndDate(String username, LocalDate date);
}
