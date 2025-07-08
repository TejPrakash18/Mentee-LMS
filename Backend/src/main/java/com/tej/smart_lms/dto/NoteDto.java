package com.tej.smart_lms.dto;


import com.tej.smart_lms.model.Notes;
import java.time.LocalDateTime;

public record NoteDto(Long id, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static NoteDto from(Notes note) {
        return new NoteDto(note.getId(), note.getContent(), note.getCreatedAt(), note.getUpdatedAt());
    }
}
