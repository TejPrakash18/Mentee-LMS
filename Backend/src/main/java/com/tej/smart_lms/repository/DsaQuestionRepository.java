package com.tej.smart_lms.repository;

import com.tej.smart_lms.model.DSA;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DsaQuestionRepository extends JpaRepository<DSA, Long> {
    List<DSA> findByCategoryIgnoreCase(String category);
    List<DSA> findByDifficultyIgnoreCase(String difficulty);
}

