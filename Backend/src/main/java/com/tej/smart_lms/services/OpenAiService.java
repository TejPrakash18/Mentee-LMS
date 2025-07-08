package com.tej.smart_lms.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    private final WebClient webClient;

    public OpenAiService(@Value("${spring.ai.openai.api-key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }


    public String getChatCompletion(List<Map<String, String>> messages) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", messages);

        try {
            Map<?, ?> response = webClient.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(status -> status.isError(), clientResponse ->
                            clientResponse.bodyToMono(String.class).flatMap(error -> {
                                if (clientResponse.statusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                                    return Mono.error(new RuntimeException("Too many requests. Please try again in a few seconds."));
                                } else {
                                    return Mono.error(new RuntimeException("Error from OpenAI API: " + error));
                                }
                            }))
                    .bodyToMono(Map.class)
                    .block();


            List<?> choices = (List<?>) response.get("choices");
            Map<?, ?> message = (Map<?, ?>) ((Map<?, ?>) choices.get(0)).get("message");
            return (String) message.get("content");

        } catch (WebClientResponseException e) {
            return "OpenAI Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "Unexpected Error: " + e.getMessage();
        }
    }
}
