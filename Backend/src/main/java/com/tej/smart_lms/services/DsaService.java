package com.tej.smart_lms.services;

import com.tej.smart_lms.dto.DSADto;
import com.tej.smart_lms.dto.DsaMapper;
import com.tej.smart_lms.exceptions.DSANotFoundException;
import com.tej.smart_lms.model.DSA;
import com.tej.smart_lms.model.DsaExample;
import com.tej.smart_lms.model.User;
import com.tej.smart_lms.repository.DsaQuestionRepository;
import com.tej.smart_lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DsaService {

    @Autowired private DsaQuestionRepository dsaRepo;
    @Autowired private UserRepository userRepository;

    // ✅ Get all DSA questions
    public List<DSADto> getAllDsaQuestion() {
        return dsaRepo.findAll().stream()
                .map(DsaMapper::toDto)
                .toList();
    }
    public DSADto createDsa(DSADto dto) {
        DSA saved = dsaRepo.save(DsaMapper.toEntity(dto));
        return DsaMapper.toDto(saved);
    }

    public DSADto updateDsa(Long id, DSADto dto) {
        DSA existing = dsaRepo.findById(id)
                .orElseThrow(() -> new DSANotFoundException("DSA not found with ID: " + id));

        // modifying the managed entity directly
        existing.setTitle(dto.getTitle());
        existing.setDifficulty(dto.getDifficulty());
        existing.setCategory(dto.getCategory());
        existing.setExplanation(dto.getExplanation());
        existing.setExpectedOutput(dto.getExpectedOutput());

        existing.getExamples().clear();
        existing.getExamples().addAll(
                dto.getExamples().stream()
                        .map(e -> new DsaExample(null, e.getInput(), e.getOutput()))
                        .collect(Collectors.toList())
        );

        // this will trigger the version check
        DSA updated = dsaRepo.save(existing);
        return DsaMapper.toDto(updated);
    }


    // ✅ Get by ID
    public DSADto getById(String id) {
        DSA question = dsaRepo.findById(Long.parseLong(id))
                .orElseThrow(() -> new DSANotFoundException("DSA not found with ID: " + id));
        return DsaMapper.toDto(question);
    }

    // ✅ Get by category (return DTOs)
    public List<DSADto> getByCategory(String category) {
        return dsaRepo.findByCategoryIgnoreCase(category).stream()
                .map(DsaMapper::toDto)
                .toList();
    }

    // ✅ Mark a DSA question completed by user
    public void markDsaCompleted(String username, String title) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        Set<String> completed = Optional.ofNullable(user.getCompletedDsaQuestion())
                .orElse(new HashSet<>());
        completed.add(title);
        user.setCompletedDsaQuestion(completed);
        userRepository.save(user);
    }

    // ✅ Get completed count
    public int getCompletedDsaCount(String username) {
        return Optional.ofNullable(
                userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                        .getCompletedDsaQuestion()
        ).map(Set::size).orElse(0);
    }

    // ✅ Get list of completed titles
    public List<String> getCompletedDsaQuestions(String username) {
        return new ArrayList<>(
                Optional.ofNullable(
                        userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                                .getCompletedDsaQuestion()
                ).orElse(Collections.emptySet())
        );
    }

    // ✅ Count completed by difficulty
    public Map<String, Long> countCompletedDsaByDifficulty(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<String> completedTitles = user.getCompletedDsaQuestion();
        if (completedTitles == null || completedTitles.isEmpty()) return Collections.emptyMap();

        return dsaRepo.findAll().stream()
                .filter(q -> completedTitles.contains(q.getTitle()))
                .collect(Collectors.groupingBy(
                        q -> q.getDifficulty().toLowerCase(),
                        Collectors.counting()
                ));
    }

    // ✅ Get total question count
    public long getTotalQuestionCount() {
        return dsaRepo.count();
    }

    // ✅ Get count grouped by difficulty
    public Map<String, Long> getDifficultyWiseCount() {
        return dsaRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        q -> q.getDifficulty().toLowerCase(),
                        Collectors.counting()
                ));
    }
}
