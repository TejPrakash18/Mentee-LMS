package com.tej.smart_lms.utils;

import com.tej.smart_lms.dto.ChatMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ChatRequest {
    private List<ChatMessage> messages;

}