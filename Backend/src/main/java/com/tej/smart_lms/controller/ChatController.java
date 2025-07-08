package com.tej.smart_lms.controller;

import com.tej.smart_lms.dto.ChatMessage;

import com.tej.smart_lms.services.OpenAiService;
import com.tej.smart_lms.utils.ChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    @Autowired
    private OpenAiService openAiService;

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {
        List<Map<String, String>> formattedMessages = new ArrayList<>();

        // Add system role prompt
        Map<String, String> systemPrompt = new HashMap<>();
        systemPrompt.put("role", "system");
        systemPrompt.put("content", "You're a helpful LMS tutor. Only provide hints and theory. Do not give full code.");
        formattedMessages.add(systemPrompt);

        // Add user and assistant messages
        for (ChatMessage m : request.getMessages()) {
            Map<String, String> msg = new HashMap<>();
            msg.put("role", m.getRole());
            msg.put("content", m.getContent());
            formattedMessages.add(msg);
        }

        return openAiService.getChatCompletion(formattedMessages);
    }
}
