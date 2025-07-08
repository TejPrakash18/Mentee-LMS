package com.tej.smart_lms.controller;

import com.tej.smart_lms.dto.NoteDto;
import com.tej.smart_lms.model.Notes;
import com.tej.smart_lms.services.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<?> getNote(@PathVariable String questionId,
                                     @RequestParam String username) {
        try {
            Long qId = Long.parseLong(questionId);
            Notes note = noteService.getNote(username, qId);
            if (note == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(NoteDto.from(note));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid questionId: must be a number");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error retrieving note: " + e.getMessage());
        }
    }

    @PostMapping("/{questionId}")
    public ResponseEntity<?> saveNote(@PathVariable String questionId,
                                      @RequestParam String username,
                                      @RequestBody String content) {
        Long qId;
        try {
            qId = Long.parseLong(questionId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid questionId: must be a numeric value");
        }

        try {
            Notes saved = noteService.saveOrUpdateNote(username, qId, content);
            return ResponseEntity.ok(NoteDto.from(saved));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Bad Request: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error saving note: " + e.getMessage());
        }
    }
}
