package com.tej.smart_lms.services;

import com.tej.smart_lms.model.Notes;
import com.tej.smart_lms.model.User;
import com.tej.smart_lms.model.DSA;
import com.tej.smart_lms.repository.DsaQuestionRepository;
import com.tej.smart_lms.repository.NoteRepository;
import com.tej.smart_lms.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private final NoteRepository noteRepo;
    private final UserRepository userRepo;
    private final DsaQuestionRepository questionRepo;

    public NoteService(NoteRepository noteRepo, UserRepository userRepo, DsaQuestionRepository questionRepo) {
        this.noteRepo = noteRepo;
        this.userRepo = userRepo;
        this.questionRepo = questionRepo;
    }

    public Notes getNote(String username, Long questionId) {
        return noteRepo.findByUserUsernameAndDsaQuestionId(username, questionId)
                .orElse(null);
    }

    public Notes saveOrUpdateNote(String username, Long questionId, String content) {
        if (content == null) {
            throw new IllegalArgumentException("Note content cannot be null");
        }

        Notes note = noteRepo.findByUserUsernameAndDsaQuestionId(username, questionId)
                .orElseGet(() -> {
                    User user = userRepo.findByUsername(username)
                            .orElseThrow(() -> new RuntimeException("User not found: " + username));
                    DSA question = questionRepo.findById(questionId)
                            .orElseThrow(() -> new RuntimeException("DSA question not found: " + questionId));
                    Notes newNote = new Notes();
                    newNote.setUser(user);
                    newNote.setDsaQuestion(question);
                    return newNote;
                });

        note.setContent(content);
        return noteRepo.save(note);
    }
}
