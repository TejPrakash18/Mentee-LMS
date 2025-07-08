package com.tej.smart_lms.repository;

import com.tej.smart_lms.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Notes, Long> {
    Optional<Notes> findByUserUsernameAndDsaQuestionId(String username, Long dsaQuestionId);
}