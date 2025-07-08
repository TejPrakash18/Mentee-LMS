package com.tej.smart_lms.controller;

import com.tej.smart_lms.dto.DSADto;
import com.tej.smart_lms.exceptions.DSANotFoundException;
import com.tej.smart_lms.services.DsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dsa")
public class DsaController {

    @Autowired private DsaService dsaService;

    //  Get all DSA questions
    @GetMapping
    public ResponseEntity<List<DSADto>> getAllQuestions() {
        return ResponseEntity.ok(dsaService.getAllDsaQuestion());
    }

    @PostMapping
    public ResponseEntity<DSADto> createDsa(@RequestBody DSADto dto) {
        return ResponseEntity.ok(dsaService.createDsa(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<DSADto> updateDsa(@PathVariable Long id, @RequestBody DSADto dto) {
        try {
            return ResponseEntity.ok(dsaService.updateDsa(id, dto));
        } catch (ObjectOptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null); // or return a custom error DTO if needed
        }
    }



    //  Get DSA by ID
    @GetMapping("/question/{id}")
    public ResponseEntity<DSADto> getById(@PathVariable String id) {
        return ResponseEntity.ok(dsaService.getById(id));
    }

    //  Get by category
    @GetMapping("/category")
    public ResponseEntity<List<DSADto>> getByCategory(@RequestParam String category) {
        List<DSADto> dsas = dsaService.getByCategory(category);
        if (dsas.isEmpty()) {
            throw new DSANotFoundException("No DSA found in category: " + category);
        }
        return ResponseEntity.ok(dsas);
    }

    //  Mark DSA completed
    @PostMapping("/complete")
    public ResponseEntity<Void> completeDsa(@RequestParam String username, @RequestParam String title) {
        dsaService.markDsaCompleted(username, title);
        return ResponseEntity.ok().build();
    }

    //  Count of completed DSAs
    @GetMapping("/completed/count")
    public ResponseEntity<Integer> getCompletedDsaCounts(@RequestParam String username) {
        return ResponseEntity.ok(dsaService.getCompletedDsaCount(username));
    }

    //  Completed DSA titles
    @GetMapping("/completed")
    public ResponseEntity<List<String>> getCompletedDsaQuestions(@RequestParam String username) {
        return ResponseEntity.ok(dsaService.getCompletedDsaQuestions(username));
    }

    //  Completed DSA count by difficulty
    @GetMapping("/completed/count/difficulty")
    public ResponseEntity<Map<String, Long>> getCompletedDsaCountByDifficulty(@RequestParam String username) {
        return ResponseEntity.ok(dsaService.countCompletedDsaByDifficulty(username));
    }

    //  Total DSA questions count
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCount() {
        return ResponseEntity.ok(dsaService.getTotalQuestionCount());
    }

    //  DSA count grouped by difficulty
    @GetMapping("/count/difficulty")
    public ResponseEntity<Map<String, Long>> getCountByDifficulty() {
        return ResponseEntity.ok(dsaService.getDifficultyWiseCount());
    }
}
